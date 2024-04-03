package com.gateway.apigateway.configuration;

import com.gateway.apigateway.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.gateway.apigateway.configuration.RouteFreedomValidator.isSecured;


@RefreshScope
@Component
public class AuthenticationFilter implements GatewayFilter {
    private final TokenService tokenService;

    @Autowired
    public AuthenticationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

       if (isSecured.test(request)) {

           if (authMissing(request)) {
               return onError(exchange);
           }

           final String token = request.getHeaders().getOrEmpty("Authorization").get(0);

            if(tokenService.isExpired(token)) {
                return onError(exchange);
            }
       }

        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    private boolean authMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }
}
