package com.bank.registersystem.service;

import com.bank.registersystem.dto.UpdateWalletDTO;
import com.bank.registersystem.error.UserNotFoundException;
import com.bank.registersystem.model.WalletModel;
import com.bank.registersystem.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class WalletServiceTest {

    private WalletModel walletModel;
    private UpdateWalletDTO updateWallet;

    @InjectMocks
    WalletService walletService;

    @Mock
    WalletRepository repository;

    @BeforeEach
    void SetUp() {
        walletModel = new WalletModel(1L, 500.50);
        updateWallet = new UpdateWalletDTO(500.50);
    }

    @Test
    void findWallet_withValidData_returnObject() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(walletModel));

        var response = walletService.findWalletByIdService(1L);

        assertNotNull(response);
    }

    @Test
    void findWallet_withInvalidData_throwException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> walletService.findWalletByIdService(1L))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void updateWallet_withValidData_doNotThrowException() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(walletModel));

        assertDoesNotThrow(() -> walletService.updateWalletService(1L, updateWallet));
    }

    @Test
    void updateWallet_withInvalidData_throwException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> walletService.updateWalletService(1L, updateWallet))
                .isInstanceOf(UserNotFoundException.class);
    }
}
