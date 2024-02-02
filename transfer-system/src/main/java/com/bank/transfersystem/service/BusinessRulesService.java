package com.bank.transfersystem.service;

import com.bank.transfersystem.dto.UpdateWalletDTO;
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

        return !(payee.getId() == null || payer.getId() == null);
    }

    public boolean checkBalance(Long payerID) {
        var payer = proxyConnection.getUserById(payerID);
        return payer.getWallet().getBalance() <= 0;
    }

    public void transferValue(Long payeeId, double value) {
        var payee = proxyConnection.getUserById(payeeId);
        var wallet = proxyConnection.getWalletById(payee.getWallet().getId());

        double amount = wallet.getBalance();
        double updatedBalance = amount + value;

        if (authorizingProxy.getAuthorization().equalsIgnoreCase("Autorizado")) {
            proxyConnection.updateWalletBalance(wallet.getId(),
                    UpdateWalletDTO.builder().balance(updatedBalance).build());
        } else {
            throw new ConnectionFailureException("authorization service denied!");
        }
    }
}
