package com.bank.registersystem.controller;

import com.bank.registersystem.dto.LoginPayloadDTO;
import com.bank.registersystem.dto.UpdateWalletDTO;
import com.bank.registersystem.dto.UserRequestDTO;
import com.bank.registersystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByIdService(id));
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginPayloadDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.loginUserService(dto));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUserService(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(name = "id") Long id,
                                             @RequestBody @Valid UserRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserService(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUserService(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/wallet/{id}")
    public ResponseEntity<Object> findWalletById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findWalletByIdService(id));
    }

    @PutMapping("/wallet/{id}")
    public ResponseEntity<Object> updateWallet(@PathVariable(name = "id") Long id,
                                               @RequestBody @Valid UpdateWalletDTO dto) {
        userService.updateWalletService(id, dto);
        return ResponseEntity.noContent().build();
    }
}
