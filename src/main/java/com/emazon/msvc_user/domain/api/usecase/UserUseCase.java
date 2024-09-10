package com.emazon.msvc_user.domain.api.usecase;

import com.emazon.msvc_user.domain.api.IUserServicePort;
import com.emazon.msvc_user.domain.model.Role;
import com.emazon.msvc_user.domain.model.User;
import com.emazon.msvc_user.domain.spi.IPasswordEncoderPort;
import com.emazon.msvc_user.domain.spi.IRolePersistencePort;
import com.emazon.msvc_user.domain.spi.IUserPersistencePort;
import com.emazon.msvc_user.domain.util.Constants;


public class UserUseCase implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;
    private final IRolePersistencePort rolePersistencePort;
    private final IPasswordEncoderPort passwordEncoder;

    public UserUseCase(IUserPersistencePort userPersistencePort, IRolePersistencePort rolePersistencePort, IPasswordEncoderPort passwordEncoder) {
        this.userPersistencePort = userPersistencePort;
        this.rolePersistencePort = rolePersistencePort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(User user) {
        ValidateUser validateUser= new ValidateUser(rolePersistencePort, userPersistencePort);

        if (user.getRole() == null){
            Role defaultRole = rolePersistencePort.findRoleByName(Constants.DEFAULT_USER_ROLE_NAME);

            user.setRole(defaultRole);
        }

        validateUser.validateUserData(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userPersistencePort.save(user);

    }

}
