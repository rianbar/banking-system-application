package com.bank.registersystem.service;

import com.bank.registersystem.dto.UserRequestDTO;
import com.bank.registersystem.dto.UserResponseDTO;
import com.bank.registersystem.error.UserNotFoundException;
import com.bank.registersystem.model.WalletModel;
import com.bank.registersystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.bank.registersystem.constant.ErrorMessageConstant.USER_NOT_FOUND_MESSAGE;

@Service
public class UserService {

    private final UserRepository repository;

    private final TransferObjectService tos;

    private final CheckRulesService checkRules;

    @Autowired
    public UserService(UserRepository repository, TransferObjectService tos, CheckRulesService checkRules) {
        this.checkRules = checkRules;
        this.repository = repository;
        this.tos = tos;
    }

    public UserResponseDTO getUserByIdService(Long id) {
        var user = repository.findById(id);

        if (user.isEmpty()) throw new UserNotFoundException(USER_NOT_FOUND_MESSAGE);

        return tos.transferToDto(user.get());
    }

    public UserResponseDTO updateUserService(Long id, UserRequestDTO dto) {
        checkRules.checkUserExistence(dto);
        var user = repository.findById(id);
        WalletModel userWallet;

        if (user.isPresent()) {
            userWallet = user.get().getWallet();
        } else {
            throw new UserNotFoundException(USER_NOT_FOUND_MESSAGE);
        }

        var saveUser = repository.save(tos.updateToModel(id, dto, userWallet));
        return tos.transferToDto(saveUser);
    }

    public void deleteUserService(Long id) {
        var user = repository.findById(id);

        if (user.isEmpty()) throw new UserNotFoundException(USER_NOT_FOUND_MESSAGE);

        repository.delete(user.get());
    }
}
