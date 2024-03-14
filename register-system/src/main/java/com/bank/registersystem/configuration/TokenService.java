package com.bank.registersystem.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.bank.registersystem.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
@Slf4j
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(UserModel user) {
        String token = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            token = JWT.create()
                    .withIssuer("auth-application")
                    .withSubject(user.getName())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException ex) {
            log.info("error at token generation!");
        }
        return token;
    }

    public String validationToken(String token) {
        String subject = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            subject = JWT.require(algorithm)
                    .withIssuer("auth-application")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException ex) {
            log.info("error at token verification!");
        }
        return subject;
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
