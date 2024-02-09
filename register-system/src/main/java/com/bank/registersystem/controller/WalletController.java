package com.bank.registersystem.controller;

import com.bank.registersystem.dto.UpdateWalletDTO;
import com.bank.registersystem.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@CrossOrigin(origins = "*")
public class WalletController {

    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/wallet/{id}")
    public ResponseEntity<Object> findWalletById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(walletService.findWalletByIdService(id));
    }

    @PutMapping("/wallet/{id}")
    public ResponseEntity<Object> updateWallet(@PathVariable(name = "id") Long id,
                                               @RequestBody @Valid UpdateWalletDTO dto) {
        walletService.updateWalletService(id, dto);
        return ResponseEntity.noContent().build();
    }
}
