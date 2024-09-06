package com.emazon.msvc_user.adapters.driving.http.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class AddUserRequestDto {
    @NotNull(message = "Document is mandatory")
    @Pattern(regexp = "^\\d+$", message = "Document must be numeric")
    private Long documentId;

    @NotBlank(message = "Name is mandatory")
    private String name;

    private String lastName;

    @Pattern(regexp = "^\\+?\\d{1,13}$", message = "Mobile number must be up to 13 characters: 12 digits and additionally can start with '+'")
    private String mobileNumber;

    @NotNull(message = "Birthdate is mandatory")
    private Date birthdate;

    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    private boolean active;
}
