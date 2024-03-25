package com.bank.transfersystem.service;

import com.bank.transfersystem.error.BusinessRuleException;
import com.bank.transfersystem.error.ConnectionFailureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class BusinessRulesServiceTest {

    private ProxyResponseDTO responseDTO;
    private WalletDTO walletDTO;

    @InjectMocks
    BusinessRulesService businessRules;

    @Mock
    RegisterProxyConnection proxyConnection;

    @Mock
    AuthorizingServiceProxy proxyAuthorizing;

    @BeforeEach
    void setUp() {
        walletDTO = new WalletDTO(1L, 450.60);
        responseDTO = new ProxyResponseDTO(1L, "any", "090.331.556-89", "any@email.com",
                "any", walletDTO, ProxyResponseDTO.UserTypeEnum.USER);
    }

    @Test
    void checkUserExistence_withValidData_returnsFalse() {
        when(proxyConnection.getUserById(anyLong())).thenReturn(responseDTO);

        var response = businessRules.checkUsersExistence(1L, 2L);

        assertFalse(response);
    }

    @Test
    void checkUserExistence_withInvalidData_returnTrue() {
        var responseId = responseDTO;
        responseId.setId(null);

        when(proxyConnection.getUserById(anyLong())).thenReturn(responseId);

        var response = businessRules.checkUsersExistence(1L, 2L);

        assertTrue(response);
    }

    @Test
    void checkUserExistence_withForbiddenRole_throwException() {
        var responseRole = responseDTO;
        responseRole.setUserType(ProxyResponseDTO.UserTypeEnum.SHOPKEEPER);

        when(proxyConnection.getUserById(anyLong())).thenReturn(responseRole);

        assertThatThrownBy(() -> businessRules.checkUsersExistence(1L, 1L))
                .isInstanceOf(BusinessRuleException.class);
    }

    @Test
    void checkBalance_withValidValue_returnTrue() {
        var responseValue = responseDTO;
        responseValue.setWallet(new WalletDTO(1L, 500.50));

        when(proxyConnection.getUserById(anyLong())).thenReturn(responseValue);

        var response = businessRules.checkBalance(1L, 250.60);

        assertTrue(response);
    }

    @Test
    void checkBalance_withInvalidValue_returnFalse() {
        var responseValue = responseDTO;
        responseValue.setWallet(new WalletDTO(1L, 150.50));

        when(proxyConnection.getUserById(anyLong())).thenReturn(responseValue);

        var response = businessRules.checkBalance(1L, 250.60);

        assertFalse(response);
    }

    @Test
    void transferValue_withValidData_DoesNotThrowException() {
        var getMock = new GetMockDTO("Autorizado");

        when(proxyConnection.getUserById(anyLong())).thenReturn(responseDTO);
        when(proxyConnection.getWalletById(anyLong())).thenReturn(walletDTO);
        when(proxyAuthorizing.getAuthorization()).thenReturn(getMock);

        assertDoesNotThrow(() -> businessRules.transferValue(1L, 2L , 30.49));
    }

    @Test
    void transferValue_withInvalidData_throwException() {
        var getMock = new GetMockDTO("Negado");

        when(proxyConnection.getUserById(anyLong())).thenReturn(responseDTO);
        when(proxyConnection.getWalletById(anyLong())).thenReturn(walletDTO);
        when(proxyAuthorizing.getAuthorization()).thenReturn(getMock);

        assertThatThrownBy(() -> businessRules.transferValue(1L, 2L, 12.50))
                .isInstanceOf(ConnectionFailureException.class);
    }
}
