package com.gateway.apigateway.exception;

import com.gateway.apigateway.error.ResponseTemplate;
import com.gateway.apigateway.error.UnauthorizedRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> unauthorizedRequestHandler(UnauthorizedRequestException ex) {
        final int code = HttpStatus.UNAUTHORIZED.value();
        final String type = HttpStatus.UNAUTHORIZED.getReasonPhrase();
        String message = ex.getMessage();

        var response = new ResponseTemplate(code, type, message);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
