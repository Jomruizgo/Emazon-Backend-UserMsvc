package com.emazon.msvc_user.adapters.driving.http.security.authorization;

import com.emazon.msvc_user.adapters.util.UserDetailUtil;
import com.emazon.msvc_user.domain.model.User;
import com.emazon.msvc_user.domain.spi.IUserPersistencePort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class UserDetailServiceImpl implements UserDetailsService {
    private final IUserPersistencePort userPersistencePort;
    private final UserDetailUtil userDetailUtil;


    public UserDetailServiceImpl(IUserPersistencePort userPersistencePort, UserDetailUtil userDetailUtil) {
        this.userPersistencePort = userPersistencePort;
        this.userDetailUtil = userDetailUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userModel = userPersistencePort.findUserByEmail(username);

        return userDetailUtil.loadUserDetail(userModel);
    }
}
