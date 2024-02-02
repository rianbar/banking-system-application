package com.bank.transfersystem.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

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
}
