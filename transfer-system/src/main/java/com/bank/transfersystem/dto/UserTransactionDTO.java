package com.bank.transfersystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserTransactionDTO {

    @NotNull(message = "'value' field cannot be null")
    private double value;
    @NotNull(message = "'payer field cannot be null'")
    private Long payer;
    @NotNull(message = "'payee' field cannot be null")
    private Long payee;
}
