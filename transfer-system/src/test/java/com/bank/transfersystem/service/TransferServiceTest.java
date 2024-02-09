package com.bank.transfersystem.service;

import com.bank.transfersystem.dto.UserTransactionDTO;
import com.bank.transfersystem.error.BusinessRuleException;
import com.bank.transfersystem.error.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class TransferServiceTest {

    private UserTransactionDTO transactionDTO;

    @InjectMocks
    TransferService transferService;

    @Mock
    BusinessRulesService businessRules;

    @BeforeEach
    void setUp() {
        transactionDTO = new UserTransactionDTO(350.5, 2L, 1L);
    }

    @Test
    void transaction_withValidData_returnObject() {
        when(businessRules.checkUsersExistence(anyLong(), anyLong())).thenReturn(false);
        when(businessRules.checkBalance(anyLong(), anyDouble())).thenReturn(true);

        var response = transferService.transactionService(transactionDTO);

        assertNotNull(response);
        assertDoesNotThrow(() -> transferService.transactionService(transactionDTO));
    }

    @Test
    void transaction_withInvalidUser_throwException() {
        when(businessRules.checkUsersExistence(anyLong(), anyLong())).thenReturn(true);

        assertThatThrownBy(() -> transferService.transactionService(transactionDTO))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void transaction_withInvalidValue_throwException() {
        when(businessRules.checkUsersExistence(anyLong(), anyLong())).thenReturn(false);
        when(businessRules.checkBalance(anyLong(), anyDouble())).thenReturn(false);

        assertThatThrownBy(() -> transferService.transactionService(transactionDTO))
                .isInstanceOf(BusinessRuleException.class);
    }
}
