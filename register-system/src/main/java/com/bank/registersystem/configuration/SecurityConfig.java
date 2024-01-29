package com.bank.registersystem.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        return security
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorization -> authorization
                        .requestMatchers(HttpMethod.POST, "user/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "user/register").permitAll()
                        .anyRequest().hasAnyAuthority("USER, SHOPKEEPER")).build();
    }
}
