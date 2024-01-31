package com.bank.transfersystem.controller;

import com.bank.transfersystem.dto.UserTransactionDTO;
import com.bank.transfersystem.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tbu")
public class TransferController {

    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transaction")
    public ResponseEntity<String> sendValue(@RequestBody UserTransactionDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(transferService.transactionService(dto));
    }
}
