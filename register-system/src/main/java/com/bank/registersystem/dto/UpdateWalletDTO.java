package com.bank.registersystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateWalletDTO {
    @NotNull
    private double balance;
}
