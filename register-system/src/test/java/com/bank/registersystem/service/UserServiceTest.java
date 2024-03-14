package com.bank.registersystem.service;

import com.bank.registersystem.configuration.TokenService;
import com.bank.registersystem.constant.UserTypeEnum;
import com.bank.registersystem.dto.LoginPayloadDTO;
import com.bank.registersystem.dto.UserRequestDTO;
import com.bank.registersystem.dto.UserResponseDTO;
import com.bank.registersystem.error.InvalidLoginException;
import com.bank.registersystem.error.UserNotFoundException;
import com.bank.registersystem.model.UserModel;
import com.bank.registersystem.model.WalletModel;
import com.bank.registersystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    private UserModel userModel;
    private UserResponseDTO responseDTO;
    private LoginPayloadDTO loginPayload;
    private UserRequestDTO requestDTO;
    private WalletModel wallet;

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository repository;

    @Mock
    TransferObjectService tos;

    @Mock
    TokenService tokenService;

    @BeforeEach
    void setUp() {
        wallet = new WalletModel(1L, 500.50);

        userModel = new UserModel(1L, "any", "090.335.664-76",
                "any@email.com", "any", UserTypeEnum.USER, wallet);

        responseDTO = new UserResponseDTO(1L, "any", "090.335.664-76",
                "any@email.com", "any", UserTypeEnum.USER, wallet);
        loginPayload = new LoginPayloadDTO("any@email.com", "any");

        requestDTO = new UserRequestDTO("any", "090.335.664-76",
                "any@email.com", "any", UserTypeEnum.USER);
    }

    @Test
    void getUser_withValidUser_returnObject() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(userModel));
        when(tos.transferToDto(userModel)).thenReturn(responseDTO);

        var response = userService.getUserByIdService(1L);

        assertNotNull(response);
    }

    @Test
    void getUser_withInvalidUser_throwException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserByIdService(1L))
                .isInstanceOf(UserNotFoundException.class);
    }

    /*
    @Test
    void loginUser_withValidData_returnString() {
        when(repository.findByEmail(userModel.getEmail())).thenReturn(Optional.of(userModel));
        when(tokenService.generateToken(userModel)).thenReturn(anyString());

        var response = userService.loginUserService(loginPayload);

        assertNotNull(response);
    }

    @Test
    void loginUser_withEmptyUser_throwException() {
        when(repository.findByEmail(userModel.getEmail())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.loginUserService(loginPayload))
                .isInstanceOf(InvalidLoginException.class);
    }

    @Test
    void loginUser_withNotEqualPasswords_throwException() {
        when(repository.findByEmail(userModel.getEmail())).thenReturn(Optional.of(userModel));
        var login = loginPayload;
        login.setPassword("any123");

        assertNotEquals(login.getPassword(), userModel.getPassword());
        assertThatThrownBy(() -> userService.loginUserService(login))
                .isInstanceOf(InvalidLoginException.class);
    }

    @Test
    void createUser_withValidData_returnObject() {
        when(tos.transferToModel(requestDTO)).thenReturn(userModel);
        when(repository.save(userModel)).thenReturn(userModel);
        when(tos.transferToDto(userModel)).thenReturn(responseDTO);

        var response = userService.createUserService(requestDTO);

        assertNotNull(response);
    }
    */
    @Test
    void updateUser_withValidData_returnObject() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(userModel));
        when(tos.updateToModel(1L, requestDTO, wallet)).thenReturn(userModel);
        when(repository.save(userModel)).thenReturn(userModel);
        when(tos.transferToDto(userModel)).thenReturn(responseDTO);

        var response = userService.updateUserService(1L, requestDTO);

        assertNotNull(response);
    }

    @Test
    void updateUser_withInvalidData_throwException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.updateUserService(1L, requestDTO))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void deleteUser_withValidId_DoNotThrowException() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(userModel));

        assertDoesNotThrow(() -> userService.deleteUserService(1L));
    }

    @Test
    void deleteUser_withInvalidId_ThrowException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.deleteUserService(1L))
                .isInstanceOf(UserNotFoundException.class);
    }
    /*
    @Test
    void getUserByEmail_withValidEmail_returnObject() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(userModel));
        when(tos.transferToDto(userModel)).thenReturn(responseDTO);

        var response = userService.getUserByEmail("any@email.com");

        assertNotNull(response);
    }

    @Test
    void getUserByEmail_withInvalidEmail_throwException() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserByEmail("any@email.com"))
                .isInstanceOf(UserNotFoundException.class);
    }

     */
}
