package com.bank.registersystem.exception;

import com.bank.registersystem.error.ErrorTemplate;
import com.bank.registersystem.error.ExistingUserException;
import com.bank.registersystem.error.InvalidLoginException;
import com.bank.registersystem.error.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExistingUserException.class)
    public ResponseEntity<Object> existingUserExceptionHandler(ExistingUserException ex) {
        final int code = HttpStatus.FORBIDDEN.value();
        final String type = HttpStatus.FORBIDDEN.toString();
        String message = ex.getMessage();

        var response = new ErrorTemplate(code, type, message);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> argumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        final int code = HttpStatus.BAD_REQUEST.value();
        final String type = HttpStatus.BAD_REQUEST.toString();
        String message = Objects.requireNonNull(ex.getFieldError()).getDefaultMessage();

        var response = new ErrorTemplate(code, type, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> userNotFoundExceptionHandler(UserNotFoundException ex) {
        final int code = HttpStatus.NOT_FOUND.value();
        final String type = HttpStatus.NOT_FOUND.toString();
        String message = ex.getMessage();

        var response = new ErrorTemplate(code, type, message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<Object> invalidLoginExceptionHandler(InvalidLoginException ex) {
        final int code = HttpStatus.UNAUTHORIZED.value();
        final String type = HttpStatus.UNAUTHORIZED.toString();
        String message = ex.getMessage();

        var response = new ErrorTemplate(code, type, message);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
