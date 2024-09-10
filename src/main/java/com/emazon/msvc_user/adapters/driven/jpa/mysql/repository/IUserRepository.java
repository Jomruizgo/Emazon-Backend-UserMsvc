package com.emazon.msvc_user.adapters.driven.jpa.mysql.repository;

import com.emazon.msvc_user.adapters.driven.jpa.mysql.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByDocumentId(Long documentId);

    Optional<UserEntity> findByEmail(String email);
}
