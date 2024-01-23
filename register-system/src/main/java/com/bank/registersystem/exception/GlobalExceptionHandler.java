package com.bank.registersystem.exception;

import com.bank.registersystem.error.ErrorTemplate;
import com.bank.registersystem.error.ExistingUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExistingUserException.class)
    public ResponseEntity<Object> ExistingUserExceptionHandler(ExistingUserException ex) {
        final int code = HttpStatus.FORBIDDEN.value();
        final String type = HttpStatus.FORBIDDEN.toString();
        String message = ex.getMessage();

        var response = new ErrorTemplate(code, type, message);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> ArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        final int code = HttpStatus.BAD_REQUEST.value();
        final String type = HttpStatus.BAD_REQUEST.toString();
        String message = Objects.requireNonNull(ex.getFieldError()).getDefaultMessage();

        var response = new ErrorTemplate(code, type, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
