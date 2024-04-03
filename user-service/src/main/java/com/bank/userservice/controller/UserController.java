package com.bank.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/update")
    public ResponseEntity<String> updateUser() {
        return ResponseEntity.ok("unsecured path");
    }

    @GetMapping("/delete")
    ResponseEntity<String> deleteUser() {
        return ResponseEntity.ok("secured path");
    }
}
