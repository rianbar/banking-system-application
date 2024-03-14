package com.bank.registersystem.service;

import com.bank.registersystem.dto.LoginPayloadDTO;
import com.bank.registersystem.dto.UserRequestDTO;
import com.bank.registersystem.error.ExistingUserException;
import com.bank.registersystem.error.InvalidLoginException;
import com.bank.registersystem.model.UserModel;
import com.bank.registersystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.bank.registersystem.constant.ErrorMessageConstant.*;

@Component
public class CheckRulesService {

    private final UserRepository repository;

    @Autowired
    public CheckRulesService(UserRepository repository) {
        this.repository = repository;
    }

    public void checkUserExistence(UserRequestDTO dto) {
        if (repository.findByIdentity(dto.getIdentity()).isPresent())
            throw new ExistingUserException(INVALID_IDENTITY_MESSAGE);

        if (repository.findByEmail(dto.getEmail()).isPresent())
            throw new ExistingUserException(INVALID_EMAIL_MESSAGE);
    }

    public UserModel checkLoginCompatibility(LoginPayloadDTO dto) {
        var user = repository.findByEmail(dto.getEmail());

        if (user.isEmpty() || !Objects.equals(user.get().getPassword(), dto.getPassword())) {
            throw new InvalidLoginException(INVALID_LOGIN_MESSAGE);}

        if (!Objects.equals(user.get().getEmail(), dto.getEmail())) {
            throw new InvalidLoginException(INVALID_LOGIN_MESSAGE);
        }

        return user.get();
    }
}
