package com.bank.transfersystem.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionResponseDTO {
    private String successMessage;
    private String successMailMessage;
}
