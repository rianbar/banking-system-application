package com.bank.registersystem.error;

public class ExistingUserException extends RuntimeException{
    public ExistingUserException(String message) {
        super(message);
    }
}
