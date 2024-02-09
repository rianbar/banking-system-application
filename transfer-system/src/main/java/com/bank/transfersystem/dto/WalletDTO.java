package com.bank.transfersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WalletDTO {
    private Long id;
    private double balance;
}
