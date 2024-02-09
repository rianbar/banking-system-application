package com.bank.registersystem.service;

import com.bank.registersystem.dto.UpdateWalletDTO;
import com.bank.registersystem.error.UserNotFoundException;
import com.bank.registersystem.model.WalletModel;
import com.bank.registersystem.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    @Autowired
    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public WalletModel findWalletByIdService(Long id) {
        var wallet = walletRepository.findById(id);
        if (wallet.isEmpty()) throw new UserNotFoundException("wallet not found!");
        return wallet.get();
    }

    public void updateWalletService(Long id, UpdateWalletDTO dto) {
        var wallet = walletRepository.findById(id);
        if (wallet.isEmpty()) throw new UserNotFoundException("wallet not found!");
        wallet.get().setBalance(dto.getBalance());
        walletRepository.save(wallet.get());
    }
}
