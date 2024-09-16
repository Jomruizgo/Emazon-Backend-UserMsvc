package com.emazon.msvc_user.adapters.driven.token.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.emazon.msvc_user.domain.model.User;
import com.emazon.msvc_user.domain.spi.ITokenServicePort;
import com.emazon.msvc_user.domain.util.AuthMessages;
import com.emazon.msvc_user.domain.util.JwtClaim;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;


public class JavaJwtAdapter implements ITokenServicePort {
    private final JwtUserDetails jwtUserDetails;


    @Value("${security.jwt.secret-key}")
    private String privateKey;

    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    public JavaJwtAdapter(JwtUserDetails jwtUserDetails) {
        this.jwtUserDetails = jwtUserDetails;
    }


    @Override
    public String generateToken(User authenticatedUser) {
        UserDetails userDetails = this.jwtUserDetails.loadUserDetail(authenticatedUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return this.createToken(authentication, authenticatedUser.getId());
    }

    @Override
    public String extractUsername(String token) {
        DecodedJWT decodedToken = this.decodeValidToken(token);
        return decodedToken.getSubject();
    }

    @Override
    public String extractSpecificClaim(String token, String claimName) {
        DecodedJWT decodedToken = this.decodeValidToken(token);
        return decodedToken.getClaim(claimName).asString();
    }

    @Override
    public boolean isValidToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator)
                    .build();

            verifier.verify(token);

            return true;

        } catch (JWTVerificationException exception) {
            return false;
        }
    }


    private String createToken(Authentication authentication, Long userId) {
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

        String username = authentication.getPrincipal().toString();
        String authorities;

        if (authentication.getAuthorities().size() > 1) {
            authorities = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));
        } else {
            authorities = authentication.getAuthorities()
                    .stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse("");
        }

        return JWT.create()
                .withIssuer(this.userGenerator)
                .withSubject(username)
                .withClaim(JwtClaim.USER_ID.getClaimName(), userId)
                .withClaim(JwtClaim.AUTHORITIES.getClaimName(), authorities)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1800000))
                .withJWTId(UUID.randomUUID().toString())
                .sign(algorithm);
    }

    private DecodedJWT decodeValidToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator)
                    .build();

            return verifier.verify(token);
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException(AuthMessages.INVALID_TOKEN_MESSAGE);
        }
    }
}
