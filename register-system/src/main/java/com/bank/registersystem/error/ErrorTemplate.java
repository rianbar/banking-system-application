package com.bank.registersystem.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorTemplate {
    private int code;
    private String type;
    private String message;
}
