package com.bank.registersystem.service;

import com.bank.registersystem.dto.UserRequestDTO;
import com.bank.registersystem.dto.UserResponseDTO;
import com.bank.registersystem.model.UserModel;
import com.bank.registersystem.model.WalletModel;
import org.springframework.stereotype.Component;

@Component
public class TransferObjectService {

    public UserModel transferToModel(UserRequestDTO dto) {
        var wallet = new WalletModel();
        wallet.setBalance(0.0);
        return UserModel.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .password(dto.getPassword())
                .identity(dto.getIdentity())
                .userType(dto.getUserType())
                .wallet(wallet)
                .build();
    }

    public UserResponseDTO transferToDto(UserModel model) {
        return UserResponseDTO.builder()
                .id(model.getId())
                .email(model.getEmail())
                .identity(model.getIdentity())
                .password(model.getPassword())
                .name(model.getName())
                .userType(model.getUserType())
                .build();
    }

    public UserModel updateToModel(Long id, UserRequestDTO dto) {
        return UserModel.builder()
                .id(id)
                .email(dto.getEmail())
                .name(dto.getName())
                .password(dto.getPassword())
                .identity(dto.getIdentity())
                .userType(dto.getUserType())
                .build();
    }
}
