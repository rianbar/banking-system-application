package com.bank.registersystem.configuration;

import com.bank.registersystem.error.UnauthorizedUserException;
import com.bank.registersystem.repository.UserRepository;
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

import static com.bank.registersystem.constant.ErrorMessageConstant.UNAUTHORIZED_USER_MESSAGE;

@Component
public class SecurityFilter extends OncePerRequestFilter {


    private final TokenService tokenService;

    private final UserRepository repository;

    @Autowired
    public SecurityFilter(TokenService tokenService, UserRepository repository) {
        this.tokenService = tokenService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            var token = this.recoverToken(request);

            if (token != null) {
                var login = tokenService.validationToken(token);
                var user = repository.findByName(login);

                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else throw new UnauthorizedUserException(UNAUTHORIZED_USER_MESSAGE);

            filterChain.doFilter(request, response);
        } catch (RuntimeException ex) {
            throw new UnauthorizedUserException(UNAUTHORIZED_USER_MESSAGE);
        }
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
