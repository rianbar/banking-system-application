package com.bank.transfersystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateWalletDTO {

    @NotNull
    private double balance;
}
