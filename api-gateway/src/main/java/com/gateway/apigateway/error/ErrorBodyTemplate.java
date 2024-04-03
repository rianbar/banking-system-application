package com.gateway.apigateway.error;

public record ErrorBodyTemplate(int code, String type, String message) {
}
