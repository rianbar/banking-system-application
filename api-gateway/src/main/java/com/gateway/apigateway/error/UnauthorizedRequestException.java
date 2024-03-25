package com.gateway.apigateway.error;

public class UnauthorizedRequestException extends RuntimeException{
    public UnauthorizedRequestException(String message) {
        super(message);
    }
}
