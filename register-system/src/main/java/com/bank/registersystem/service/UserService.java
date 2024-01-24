package com.bank.registersystem.service;

import com.bank.registersystem.dto.UserRequestDTO;
import com.bank.registersystem.dto.UserResponseDTO;
import com.bank.registersystem.error.ExistingUserException;
import com.bank.registersystem.error.UserNotFoundException;
import com.bank.registersystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;
    private final TransferObjectService tos;

    @Autowired
    public UserService(UserRepository repository, TransferObjectService tos) {
        this.repository = repository;
        this.tos = tos;
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
