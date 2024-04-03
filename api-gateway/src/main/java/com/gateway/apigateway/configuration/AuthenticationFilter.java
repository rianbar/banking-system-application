package com.gateway.apigateway.configuration;

import com.gateway.apigateway.error.UnauthorizedUserException;
import com.gateway.apigateway.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.gateway.apigateway.configuration.RouteFreedomValidator.isSecured;


@RefreshScope
@Component
@Slf4j
public class AuthenticationFilter implements GatewayFilter {
    private final TokenService tokenService;

    @Autowired
    public AuthenticationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("passing through the filter!");

        ServerHttpRequest request = exchange.getRequest();

       if (isSecured.test(request)) {

           if (authMissing(request)) {
               throw new UnauthorizedUserException("you don't have permission to access this page");
           }

           final String token = request.getHeaders().getOrEmpty("Authorization").get(0);

            if(tokenService.isExpired(token)) {
                throw new UnauthorizedUserException("your season was expired! log in again, please.");
            }
       }

        return chain.filter(exchange);
    }

    private boolean authMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }
}
