package com.bank.transfersystem.service;

import com.bank.transfersystem.dto.UserTransactionDTO;
import com.bank.transfersystem.error.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferService {
    private final BusinessRulesService businessRules;

    @Autowired
    public TransferService(BusinessRulesService businessRules) {
        this.businessRules = businessRules;
    }

    public String transactionService(UserTransactionDTO dto) {

        if (businessRules.checkUsersExistence(dto.getPayee(), dto.getPayer()))
            throw new UserNotFoundException("payee or payer id not found!");
        if (!businessRules.checkBalance(dto.getPayer(), dto.getValue())) {
            businessRules.transferValue(dto.getPayee(), dto.getPayer(), dto.getValue());
        }
        return "successfully";
    }
}
