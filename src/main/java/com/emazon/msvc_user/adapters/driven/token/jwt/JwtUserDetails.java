package com.emazon.msvc_user.adapters.driven.token.jwt;

import com.emazon.msvc_user.domain.model.User;
import org.springframework.lang.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for configuring {@link UserDetails} objects.
 * <p>
 * This class is responsible for converting a {@link User} model into a {@link UserDetails} object,
 * which is used by Spring Security to handle authentication and authorization.
 * </p>
 * <p>
 * Any other class that requires configuring or creating {@link UserDetails} should use
 * this class to ensure consistency in the handling of user details.
 * </p>
 */
public class JwtUserDetails {

    public UserDetails loadUserDetail(@NonNull User userModel){

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
