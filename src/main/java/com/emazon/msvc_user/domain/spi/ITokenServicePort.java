package com.emazon.msvc_user.domain.spi;


import com.emazon.msvc_user.domain.model.User;

public interface ITokenServicePort {
    String generateToken(User authenticatedUser);
    String extractUsername(String token);
    String extractSpecificClaim(String token, String claimName);
    boolean isValidToken(String token);
}
