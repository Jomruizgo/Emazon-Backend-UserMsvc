package com.emazon.msvc_user.adapters.driven.encoder;

import com.emazon.msvc_user.domain.spi.IPasswordEncoderPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncoderAdapter implements IPasswordEncoderPort {
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    public EncoderAdapter() {
        this.bcryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encode(String password) {
        return bcryptPasswordEncoder.encode(password);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return bcryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }

    public PasswordEncoder getPasswordEncoder() {
        return bcryptPasswordEncoder;
    }
}
