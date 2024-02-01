package com.bank.transfersystem.error;

public class ConnectionFailureException extends RuntimeException{
    public ConnectionFailureException(String message) {
        super(message);
    }
}
