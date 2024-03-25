package com.gateway.apigateway.configuration;

import com.gateway.apigateway.error.UnauthorizedRequestException;
import com.gateway.apigateway.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RefreshScope
@Component
public class AuthenticationFilter implements GatewayFilter {

    private static final String ERROR_MESSAGE = "you aren't authorized to access this path";
    private final TokenService tokenService;
    private final RouteValidator validator;

    @Autowired
    public AuthenticationFilter(TokenService tokenService, RouteValidator routeValidator) {
        this.validator = routeValidator;
        this.tokenService = tokenService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();


        if (validator.isSecured.test(request)) {
            if (authMissing(request)) {
                throw new UnauthorizedRequestException(ERROR_MESSAGE);
            }

            final String token = request.getHeaders().getOrEmpty("Authorization").get(0);

            if (tokenService.isExpired(token)) {
                throw new UnauthorizedRequestException(ERROR_MESSAGE);
            }
        }

        return chain.filter(exchange);
    }

    private boolean authMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }
}
