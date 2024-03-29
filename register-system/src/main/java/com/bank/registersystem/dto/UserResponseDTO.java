package com.bank.registersystem.dto;

import com.bank.registersystem.constant.UserTypeEnum;
import com.bank.registersystem.model.WalletModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private Long id;
    private String name;
    private String identity;
    private String email;
    private String password;
    private UserTypeEnum userType;
    @JsonIgnore
    private WalletModel wallet;
}
