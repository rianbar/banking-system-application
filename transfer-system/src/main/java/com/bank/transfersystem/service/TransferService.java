package com.bank.transfersystem.service;

import com.bank.transfersystem.dto.UserTransactionDTO;
import com.bank.transfersystem.error.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TransferService {
    private final BusinessRulesService businessRules;

    public TransferService(BusinessRulesService businessRules) {
        this.businessRules = businessRules;
    }

    public String transactionService(UserTransactionDTO dto) {

        if (businessRules.checkUsersExistence(dto.getPayee(), dto.getPayer()))
            throw new UserNotFoundException("payee or payer id not found!");
        if (!businessRules.checkBalance(dto.getPayer())) {

        }
        return "";
    }


}
