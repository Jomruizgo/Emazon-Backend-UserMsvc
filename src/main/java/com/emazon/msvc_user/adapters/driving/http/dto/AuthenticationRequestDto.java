package com.emazon.msvc_user.adapters.driving.http.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AuthenticationRequestDto {
    private String username;
    private String password;

    
}
