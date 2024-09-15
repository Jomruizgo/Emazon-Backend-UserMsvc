package com.emazon.msvc_user.domain.spi;


import com.emazon.msvc_user.domain.model.User;

public interface ITokenPort {
    String generateToken(User authenticatedUser);
}
