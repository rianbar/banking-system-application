package com.bank.registersystem.service;

import com.bank.registersystem.configuration.TokenService;
import com.bank.registersystem.dto.LoginPayloadDTO;
import com.bank.registersystem.dto.UserRequestDTO;
import com.bank.registersystem.dto.UserResponseDTO;
import com.bank.registersystem.error.ExistingUserException;
import com.bank.registersystem.error.InvalidLoginException;
import com.bank.registersystem.error.UserNotFoundException;
import com.bank.registersystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    private final UserRepository repository;
    private final TransferObjectService tos;

    private final TokenService tokenService;

    @Autowired
    public UserService(UserRepository repository, TransferObjectService tos, TokenService service) {
        this.repository = repository;
        this.tos = tos;
        this.tokenService = service;
    }

    public UserResponseDTO getUserByIdService(Long id) {
        var user = repository.findById(id);
        if (user.isEmpty()) throw new UserNotFoundException("user 'id' not found!");

        return tos.transferToDto(user.get());
    }

    public String loginUserService(LoginPayloadDTO dto) {
        var user = repository.findByEmail(dto.getEmail());
        if (user.isEmpty() || !Objects.equals(user.get().getPassword(), dto.getPassword()))
            throw new InvalidLoginException("email or password is wrong!");

        return tokenService.generateToken(user.get());
    }

    public UserResponseDTO createUserService(UserRequestDTO dto) {
        checkUserExistence(dto);
        var saveUser = repository.save(tos.transferToModel(dto));

        return tos.transferToDto(saveUser);
    }

    public UserResponseDTO updateUserService(Long id, UserRequestDTO dto) {
        checkUserExistence(dto);
        if (repository.findById(id).isEmpty()) throw new UserNotFoundException("User id not found!");

        var saveUser = repository.save(tos.updateToModel(id, dto));
        return tos.transferToDto(saveUser);
    }

    public void deleteUserService(Long id) {
        var user = repository.findById(id);
        if (user.isEmpty()) throw new UserNotFoundException("User id not found!");

        repository.delete(user.get());
    }

    private void checkUserExistence(UserRequestDTO dto) {
        if (repository.findByIdentity(dto.getIdentity()).isPresent())
            throw new ExistingUserException("cpf or cnpj already exists");
        if (repository.findByEmail(dto.getEmail()).isPresent())
            throw new ExistingUserException("email already exists");
    }
}
