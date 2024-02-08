package com.bank.registersystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginPayloadDTO {
    private String email;
    private String password;
}
