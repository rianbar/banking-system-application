package com.bank.registersystem.controller;

import com.bank.registersystem.dto.LoginPayloadDTO;
import com.bank.registersystem.dto.UserRequestDTO;
import com.bank.registersystem.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginPayloadDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(loginService.loginUserService(dto));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(loginService.registerUserService(dto));
    }
}
