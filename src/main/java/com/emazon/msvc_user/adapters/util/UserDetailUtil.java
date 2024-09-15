package com.emazon.msvc_user.adapters.util;

import com.emazon.msvc_user.domain.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserDetailUtil {
    public UserDetails loadUserDetail(User userModel){

        if (userModel == null) {throw new UsernameNotFoundException("Bad credentials");}

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_" + userModel.getRole().getName()));
        return new org.springframework.security.core.userdetails.User(userModel.getEmail(),
                userModel.getPassword(),
                userModel.getIsActive(),
                true,
                true,
                true,
                authorities);
    }
}
