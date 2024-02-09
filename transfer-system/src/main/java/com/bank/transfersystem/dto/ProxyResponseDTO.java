package com.bank.transfersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProxyResponseDTO {
    private Long id;
    private String name;
    private String identity;
    private String email;
    private String password;
    private WalletDTO wallet;
    private UserTypeEnum userType;

    public enum UserTypeEnum {
        USER, SHOPKEEPER
    }
}
