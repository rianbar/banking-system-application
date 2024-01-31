package com.bank.transfersystem.service;

import com.bank.transfersystem.dto.UserTransactionDTO;
import com.bank.transfersystem.proxy.AuthorizingServiceProxy;
import org.springframework.stereotype.Service;

@Service
public class TransferService {

    private final AuthorizingServiceProxy proxy;
    private final BusinessRulesService businessRules;

    public TransferService(BusinessRulesService businessRules, AuthorizingServiceProxy proxy) {
        this.businessRules = businessRules;
        this.proxy = proxy;
    }

    public String transactionService(UserTransactionDTO dto) {
        /*
        if (businessRules.checkUsersExistence(dto.getPayee(), dto.getPayer())) {
        }

         */
        return "";
    }


}
