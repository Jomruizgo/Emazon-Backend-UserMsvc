package com.emazon.msvc_user.adapters.driving.http.controller;

import com.emazon.msvc_user.adapters.driving.http.dto.AuthenticationRequestDto;
import com.emazon.msvc_user.adapters.driving.http.dto.AuthenticationResponseDto;
import com.emazon.msvc_user.adapters.driving.http.mapper.request.IAuthRequestMapper;
import com.emazon.msvc_user.adapters.driving.http.mapper.response.IAuthResponseMapper;
import com.emazon.msvc_user.domain.api.IAuthServicePort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value ="api/auth")
public class AuthController {
    private final IAuthServicePort authServicePort;
    private final IAuthRequestMapper authRequestMapper;
    private final IAuthResponseMapper authResponseMapper;

    public AuthController(IAuthServicePort authServicePort, IAuthRequestMapper authRequestMapper, IAuthResponseMapper authResponseMapper) {
        this.authServicePort = authServicePort;
        this.authRequestMapper = authRequestMapper;
        this.authResponseMapper = authResponseMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationRequestDto authRequest){
        AuthenticationResponseDto token = authResponseMapper.modelToAuthResponse(authServicePort.login(authRequestMapper.authRequestToModel(authRequest)));
        return new ResponseEntity<>(token, HttpStatus.OK);
    }


}
