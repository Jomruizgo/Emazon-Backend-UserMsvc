package com.emazon.msvc_user.domain.api;

import com.emazon.msvc_user.domain.model.AuthUser;

public interface IAuthServicePort {
    AuthUser login(AuthUser authUser);
}
