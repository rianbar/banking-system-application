package com.bank.transfersystem.dto;

import lombok.Data;

@Data
public class ProxyResponseDTO {
    private Long id;
    private String name;
    private String identity;
    private String email;
    private String password;
    private Wallet wallet;

    @Data
    public static class Wallet {
        private Long id;
        private Double balance;
    }
}
