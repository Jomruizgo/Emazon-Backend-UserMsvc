package com.emazon.msvc_user.domain.api.usecase;

import com.emazon.msvc_user.domain.api.IAuthServicePort;
import com.emazon.msvc_user.domain.exceptions.BadCredentialsException;
import com.emazon.msvc_user.domain.exceptions.DisabledAccountException;
import com.emazon.msvc_user.domain.model.AuthUser;
import com.emazon.msvc_user.domain.model.User;
import com.emazon.msvc_user.domain.spi.IPasswordEncoderPort;
import com.emazon.msvc_user.domain.spi.ITokenServicePort;
import com.emazon.msvc_user.domain.spi.IUserPersistencePort;
import com.emazon.msvc_user.domain.util.AuthMessages;


public class AuthUseCase implements IAuthServicePort {
    private final IUserPersistencePort userPersistencePort;
    private final IPasswordEncoderPort passwordEncoder;
    private final ITokenServicePort tokenPort;

    public AuthUseCase(IUserPersistencePort userPersistencePort, IPasswordEncoderPort passwordEncoder, ITokenServicePort tokenPort) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoder = passwordEncoder;
        this.tokenPort = tokenPort;
    }

    @Override
    public AuthUser login(AuthUser authUser) {
        validateAuthUser(authUser);

        User validUser = userPersistencePort.findUserByEmail(authUser.getUsername());

        if (validUser == null) {
            throw new BadCredentialsException(AuthMessages.BAD_CREDENTIALS_MESSAGE);
        }
        if(!passwordEncoder.matches(authUser.getPassword(), validUser.getPassword())){
            throw new BadCredentialsException(AuthMessages.BAD_CREDENTIALS_MESSAGE);
        }
        if(!validUser.getIsActive()){
            throw new DisabledAccountException(AuthMessages.NOT_ACTIVE_MESSAGE);
        }

        authUser.setPassword(null);
        authUser.setToken(tokenPort.generateToken(validUser));

        return authUser;
    }


    private void validateAuthUser(AuthUser authUser) {
        if (authUser==null){
            throw new BadCredentialsException(AuthMessages.INVALID_CREDENTIALS_MESSAGE);
        }
        if (authUser.getUsername()==null){
            throw new BadCredentialsException(AuthMessages.INVALID_CREDENTIALS_MESSAGE);
        }
        if (authUser.getPassword()==null){
            throw new BadCredentialsException(AuthMessages.INVALID_CREDENTIALS_MESSAGE);
        }
    }
}
