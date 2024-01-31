package com.bank.transfersystem.service;

import com.bank.transfersystem.proxy.RegisterProxyConnection;
import org.springframework.stereotype.Service;

@Service
public class BusinessRulesService {

    private final RegisterProxyConnection proxyConnection;

    public BusinessRulesService(RegisterProxyConnection proxyConnection) {
        this.proxyConnection = proxyConnection;
    }

    public boolean checkUsersExistence(Long payeeId, Long payerId) {
        var payee = proxyConnection.getUserById(payeeId);
        var payer = proxyConnection.getUserById(payerId);

        return !(payee.getId() == null || payer.getId() == null);
    }
}
