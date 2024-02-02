package com.bank.transfersystem.configuration;

import com.bank.transfersystem.error.ConnectionFailureException;
import com.bank.transfersystem.proxy.RegisterProxyConnection;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final RegisterProxyConnection proxyConnection;
    private final TokenService tokenService;

    @Autowired
    public SecurityFilter(RegisterProxyConnection proxy, TokenService tokenService) {
        this.proxyConnection = proxy;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var token = this.recoverToken(request);

        try {
            if (token != null) {
                var login = tokenService.validationToken(token);
                var user = proxyConnection.getUserAuthorities(login);

                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
            filterChain.doFilter(request, response);
        } catch (RuntimeException ex) {
            throw new ConnectionFailureException("login authorization error");
        }
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
