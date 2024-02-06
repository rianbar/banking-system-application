package com.bank.transfersystem.service;

import com.bank.transfersystem.dto.UpdateWalletDTO;
import com.bank.transfersystem.error.BusinessRuleException;
import com.bank.transfersystem.error.ConnectionFailureException;
import com.bank.transfersystem.proxy.AuthorizingServiceProxy;
import com.bank.transfersystem.proxy.RegisterProxyConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessRulesService {

    private final RegisterProxyConnection proxyConnection;
    private final AuthorizingServiceProxy authorizingProxy;

    @Autowired
    public BusinessRulesService(RegisterProxyConnection proxyConnection, AuthorizingServiceProxy proxy) {
        this.proxyConnection = proxyConnection;
        this.authorizingProxy = proxy;
    }

    public boolean checkUsersExistence(Long payeeId, Long payerId) {
        var payee = proxyConnection.getUserById(payeeId);
        var payer = proxyConnection.getUserById(payerId);

        if (payer.getUserType().name().equalsIgnoreCase("SHOPKEEPER"))
            throw new BusinessRuleException("impossible to proceed with the transaction " +
                    "because the payer is a shopkeeper");

        return payee.getId() == null || payer.getId() == null;
    }

    public boolean checkBalance(Long payerID, double value) {
        var payer = proxyConnection.getUserById(payerID);

        return payer.getWallet().getBalance() < value;
    }

    public void transferValue(Long payeeId, Long payerId, double value) {
        var payee = proxyConnection.getUserById(payeeId);
        var payer = proxyConnection.getUserById(payerId);

        var wallet = proxyConnection.getWalletById(payee.getWallet().getId());
        var payerWallet = proxyConnection.getWalletById(payer.getWallet().getId());

        double amount = wallet.getBalance();
        double updatedBalance = amount + value;

        if (authorizingProxy.getAuthorization().getMessage().equalsIgnoreCase("Autorizado")) {
            proxyConnection.updateWalletBalance(payerWallet.getId(),
                    UpdateWalletDTO.builder().balance(payerWallet.getBalance() - value).build());
            proxyConnection.updateWalletBalance(wallet.getId(),
                    UpdateWalletDTO.builder().balance(updatedBalance).build());
        } else {
            throw new ConnectionFailureException("authorization service denied!");
        }
    }

    public String sendMailService() {
        String sent = "sent mail";
        String denied = "mail service is actually inaccessible";
        return authorizingProxy.getMailServiceStatus().getMessage().equalsIgnoreCase("true") ? sent : denied;
    }
}
