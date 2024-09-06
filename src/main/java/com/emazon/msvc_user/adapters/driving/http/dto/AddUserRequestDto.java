package com.emazon.msvc_user.adapters.driving.http.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class AddUserRequestDto {
    private Long documentId;
    private String name;
    private String lastName;
    private String mobileNumber;
    private Date birthdate;
    private String email;
    private String password;
}
