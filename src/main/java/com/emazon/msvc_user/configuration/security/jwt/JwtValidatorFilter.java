package com.emazon.msvc_user.configuration.security.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.emazon.msvc_user.adapters.driven.token.jwt.JavaJwtConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

public class JwtValidatorFilter extends OncePerRequestFilter {

    private JavaJwtConfig javaJwtConfig;

    public JwtValidatorFilter(JavaJwtConfig javaJwtConfig) {
        this.javaJwtConfig = javaJwtConfig;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwtToken != null) {
            jwtToken = jwtToken.substring(7);

            DecodedJWT decodedJWT = javaJwtConfig.validateToken(jwtToken);

            String username = javaJwtConfig.extractUsername(decodedJWT);
            String stringAuthorities = javaJwtConfig.getSpecificClaim(decodedJWT, "authorities").asString();

            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities);

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            context.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(context);

        }
        filterChain.doFilter(request, response);


    }
}