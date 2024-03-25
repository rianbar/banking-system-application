package com.gateway.apigateway.error;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseTemplate {
    private int code;
    private String type;
    private String message;
}
