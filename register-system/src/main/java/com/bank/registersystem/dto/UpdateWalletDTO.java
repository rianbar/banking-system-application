package com.bank.registersystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateWalletDTO {
    @NotNull
    private double balance;
}
