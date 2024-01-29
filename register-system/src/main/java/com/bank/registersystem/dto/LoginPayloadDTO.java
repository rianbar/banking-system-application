package com.bank.registersystem.dto;

import lombok.Data;

@Data
public class LoginPayloadDTO {
    private String email;
    private String password;
}
