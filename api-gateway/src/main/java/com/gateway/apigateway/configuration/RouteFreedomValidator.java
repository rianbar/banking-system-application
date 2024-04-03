package com.gateway.apigateway.configuration;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
public class RouteFreedomValidator {

    private RouteFreedomValidator() {}

    public static final List<String> openEndpoints = List.of(
            "/auth/login",
            "/auth/register",
            "/user/update"
    );

    public static final Predicate<ServerHttpRequest> isSecured =
            request -> openEndpoints.stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
