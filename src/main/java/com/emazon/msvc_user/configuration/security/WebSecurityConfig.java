package com.emazon.msvc_user.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Deshabilitar la seguridad para todas las solicitudes
        http
                .csrf().disable() // Desactiva la protección CSRF
                .authorizeRequests()
                .anyRequest().permitAll(); // Permite todas las solicitudes sin autenticación

        return http.build();
    }
}