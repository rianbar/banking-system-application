package com.bank.registersystem.error;

public class InvalidLoginException extends RuntimeException{
    public InvalidLoginException(String message) {
        super(message);
    }
}
