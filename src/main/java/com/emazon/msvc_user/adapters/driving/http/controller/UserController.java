package com.emazon.msvc_user.adapters.driving.http.controller;

import com.emazon.msvc_user.adapters.driving.http.dto.AddUserRequestDto;
import com.emazon.msvc_user.adapters.driving.http.mapper.request.IUserRequestMapper;
import com.emazon.msvc_user.domain.api.IUserServicePort;
import com.emazon.msvc_user.domain.util.Constants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = Constants.API_USER_PATH)
public class UserController {
    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;

    public UserController(IUserServicePort userServicePort, IUserRequestMapper userRequestMapper) {
        this.userServicePort = userServicePort;
        this.userRequestMapper = userRequestMapper;
    }

    @PostMapping(Constants.WAREHOUSE_SEMI_PATH)
    public ResponseEntity<Void> addUser(@Valid  @RequestBody AddUserRequestDto request){
        userServicePort.saveUser(userRequestMapper.addRequestDtotoModel(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
