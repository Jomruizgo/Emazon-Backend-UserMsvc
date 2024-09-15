package com.emazon.msvc_user.adapters.driven.token.jwt;

import com.emazon.msvc_user.adapters.util.UserDetailUtil;
import com.emazon.msvc_user.domain.model.User;
import com.emazon.msvc_user.domain.spi.ITokenPort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


public class TokenAdapter implements ITokenPort {
    private final UserDetailUtil userDetailUtil;
    private final JavaJwtConfig javaJwtConfig;

    public TokenAdapter(UserDetailUtil userDetailUtil, JavaJwtConfig javaJwtConfig) {
        this.userDetailUtil = userDetailUtil;
        this.javaJwtConfig = javaJwtConfig;
    }


    @Override
    public String generateToken(User authenticatedUser) {
        UserDetails userDetails = userDetailUtil.loadUserDetail(authenticatedUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return javaJwtConfig.createToken(authentication);
    }
}
