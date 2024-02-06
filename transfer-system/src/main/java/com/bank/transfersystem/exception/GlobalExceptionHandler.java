package com.bank.transfersystem.exception;

import com.bank.transfersystem.error.BusinessRuleException;
import com.bank.transfersystem.error.ConnectionFailureException;
import com.bank.transfersystem.error.ErrorTemplate;
import com.bank.transfersystem.error.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> connectionFailureHandler(ConnectionFailureException ex) {
        final int code = HttpStatus.SERVICE_UNAVAILABLE.value();
        final String type = HttpStatus.SERVICE_UNAVAILABLE.toString();
        String message = ex.getMessage();

        var response = new ErrorTemplate(code, type, message);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<Object> userNotFoundHandler(UserNotFoundException ex) {
        final int code = HttpStatus.NOT_FOUND.value();
        final String type = HttpStatus.NOT_FOUND.toString();
        String message = ex.getMessage();

        var response = new ErrorTemplate(code, type, message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler
    public ResponseEntity<Object> businessRuleHandler(BusinessRuleException ex) {
        final int code = HttpStatus.BAD_REQUEST.value();
        final String type = HttpStatus.BAD_REQUEST.toString();
        String message = ex.getMessage();

        var response = new ErrorTemplate(code, type, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
