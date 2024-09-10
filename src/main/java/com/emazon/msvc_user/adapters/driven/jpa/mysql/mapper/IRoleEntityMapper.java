package com.emazon.msvc_user.adapters.driven.jpa.mysql.mapper;


import com.emazon.msvc_user.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.emazon.msvc_user.domain.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IRoleEntityMapper {
    Role toModel(RoleEntity roleEntity);

    RoleEntity toEntity(Role role);
}
