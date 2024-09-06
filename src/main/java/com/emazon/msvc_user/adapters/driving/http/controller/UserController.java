package com.emazon.msvc_user.adapters.driving.http.controller;

import com.emazon.msvc_user.adapters.driving.http.dto.AddUserRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:9091"})
@RequestMapping(value ="api/user")
public class UserController {

    @PostMapping("")
    public ResponseEntity<Void> addUser(@RequestBody AddUserRequestDto request){

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
