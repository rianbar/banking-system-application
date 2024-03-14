package com.bank.registersystem.service;

import com.bank.registersystem.configuration.TokenService;
import com.bank.registersystem.dto.LoginPayloadDTO;
import com.bank.registersystem.dto.UserRequestDTO;
import com.bank.registersystem.dto.UserResponseDTO;
import com.bank.registersystem.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserRepository repository;

    private final TokenService tokenService;

    private final TransferObjectService tos;

    private final CheckRulesService checkRules;

    public LoginService(TokenService tokenService, UserRepository repository,
                        TransferObjectService tos, CheckRulesService checkRules) {
        this.checkRules = checkRules;
        this.tokenService = tokenService;
        this.repository = repository;
        this.tos = tos;
    }

    public String loginUserService(LoginPayloadDTO dto) {

        var validatedUser = checkRules.checkLoginCompatibility(dto);

        return tokenService.generateToken(validatedUser);
    }

    public UserResponseDTO registerUserService(UserRequestDTO dto) {
        checkRules.checkUserExistence(dto);

        var saveUser = repository.save(tos.transferToModel(dto));

        return tos.transferToDto(saveUser);
    }
}
