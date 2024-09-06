package com.emazon.msvc_user.adapters.driven.jpa.mysql.entity;

import com.emazon.msvc_user.domain.util.Constants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.print.attribute.standard.DateTimeAtCreation;
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
    private boolean isActive = true;

    @Column(name = "created_at")
    private DateTimeAtCreation createAt;
}
