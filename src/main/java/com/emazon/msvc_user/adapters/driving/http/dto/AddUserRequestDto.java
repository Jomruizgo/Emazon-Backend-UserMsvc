package com.emazon.msvc_user.adapters.driving.http.dto;


import com.emazon.msvc_user.domain.util.ValidationMessages;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;

import java.util.Date;

@AllArgsConstructor
public class AddUserRequestDto {

    private Long documentId;

    private String name;

    private String lastName;

    private String mobileNumber;

    private Date birthdate;

    private String email;

    @NotBlank(message = ValidationMessages.PASSWORD_MANDATORY)
    @Size(min = 8, message = ValidationMessages.MIN_PASSWORD_SIZE)
    private String password;


    private boolean isActive;

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }
}
