package com.emazon.msvc_user.adapters.driven.jpa.mysql.entity;

import com.emazon.msvc_user.domain.util.Constants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "document_id",unique = true,nullable = false)
    private Long documentId;

    private String name;

    private String lastName;

    @Column(name = "mobile_number", length = Constants.MAX_MOBILE_NUMBER_LENGTH)
    private String mobileNumber;

    private Date birthdate;

    @Column(name = "email",unique = true,nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;


    //These methods are generated manually because Lombok change their names and  this cause mappers issues.
    public boolean getIsActive() { return isActive; }
    public void setIsActive(boolean active) { isActive = active; }
}
