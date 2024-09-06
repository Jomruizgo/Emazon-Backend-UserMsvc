package com.emazon.msvc_user.domain.model;

import java.util.Date;

public class User {
    private Long documentId;
    private String name;

    private String lastName;
    private String mobileNumber;
    private Date birthdate;
    private String email;
    private String password;
    private boolean isActive;
    private Date createdAt;

    public User() {}

    public User(Long documentId, String name, String lastName, String mobileNumber, Date birthdate, String email, String password, boolean isActive, Date createdAt) {
        this.documentId = documentId;
        this.name = name;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.birthdate = birthdate;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    public Long getDocumentId() { return documentId; }

    public void setDocumentId(Long documentId) { this.documentId = documentId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() { return mobileNumber; }

    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public Date getBirthdate() { return birthdate; }

    public void setBirthdate(Date birthdate) { this.birthdate = birthdate; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public boolean isActive() { return isActive; }

    public void setIsActive(boolean active) { isActive = active; }

    public Date getCreatedAt() { return createdAt; }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
