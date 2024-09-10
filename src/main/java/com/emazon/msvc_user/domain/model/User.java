package com.emazon.msvc_user.domain.model;

import java.util.Date;

public class User {
    private Long id;
    private Long documentId;
    private String name;
    private String lastName;
    private String mobileNumber;
    private Date birthdate;
    private String email;
    private String password;
    private Role role;
    private boolean isActive;
    private Date createdAt;

    public User() {}

    public User(Long id, Long documentId, String name, String lastName, String mobileNumber, Date birthdate, String email, String password, Role role, boolean isActive, Date createdAt) {
        this.id = id;
        this.documentId = documentId;
        this.name = name;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.birthdate = birthdate;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

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

    public Role getRole() { return role; }

    public void setRole(Role role) { this.role = role; }

    public boolean getIsActive() { return isActive; }

    public void setIsActive(boolean active) { isActive = active; }

    public Date getCreatedAt() { return createdAt; }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
