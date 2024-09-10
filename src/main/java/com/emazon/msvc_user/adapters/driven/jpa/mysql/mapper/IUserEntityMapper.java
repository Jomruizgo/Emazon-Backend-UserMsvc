package com.emazon.msvc_user.adapters.driven.jpa.mysql.mapper;

import com.emazon.msvc_user.adapters.driven.jpa.mysql.entity.UserEntity;
import com.emazon.msvc_user.domain.model.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface IUserEntityMapper {

    User toModel(UserEntity userEntity);

    @Mapping(target = "createdAt", ignore = true)
    UserEntity toEntity(User user);


    @AfterMapping
    default void setCreateAt(User user, @MappingTarget UserEntity userEntity) {
        if (userEntity.getCreatedAt() == null) {
            userEntity.setCreatedAt(LocalDateTime.now());
        }
    }
}
