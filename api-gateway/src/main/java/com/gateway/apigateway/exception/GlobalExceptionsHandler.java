package com.gateway.apigateway.exception;

import com.gateway.apigateway.error.ErrorBodyTemplate;
import com.gateway.apigateway.error.UnauthorizedUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler
    public ResponseEntity<Object> unauthorizedUserExceptionHandler(UnauthorizedUserException ex) {
        int code = HttpStatus.UNAUTHORIZED.value();
        String type= HttpStatus.UNAUTHORIZED.getReasonPhrase();
        String message = ex.getMessage();

        ErrorBodyTemplate errorResponse = new ErrorBodyTemplate(code, type, message);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
