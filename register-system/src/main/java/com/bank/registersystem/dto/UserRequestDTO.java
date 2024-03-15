package com.bank.registersystem.dto;

import com.bank.registersystem.constant.UserTypeEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    @NotEmpty
    private String name;
    @NotEmpty
    @Pattern(regexp = "[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}", message = "Invalid 'cpf' or 'cnpj'")
    private String identity;
    @NotEmpty
    @Pattern(regexp=".+@.+\\.[a-z]+", message="Invalid email address!")
    private String email;
    @NotEmpty
    private String password;
    @Enumerated(value = EnumType.STRING)
    private UserTypeEnum userType;
}
