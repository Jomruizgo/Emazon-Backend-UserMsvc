package com.emazon.msvc_user.domain.api.usecase;

import com.emazon.msvc_user.domain.api.IUserServicePort;
import com.emazon.msvc_user.domain.model.User;
import com.emazon.msvc_user.domain.spi.IUserPersistencePort;

public class UserUseCase implements IUserServicePort {
    private IUserPersistencePort userPersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public void saveUser(User user) {

    }
}
