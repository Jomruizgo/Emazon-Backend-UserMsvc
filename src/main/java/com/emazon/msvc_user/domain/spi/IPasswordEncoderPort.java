package com.emazon.msvc_user.domain.spi;

public interface IPasswordEncoderPort {
    String encode(String password);
    boolean matches(String rawPassword, String encodedPassword);
}
