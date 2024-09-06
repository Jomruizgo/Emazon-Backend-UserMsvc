package com.emazon.msvc_user.adapters.driving.http.mapper.request;

import com.emazon.msvc_user.adapters.driving.http.dto.AddUserRequestDto;
import com.emazon.msvc_user.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IUserRequestMapper {
    @Mapping(source = "documentId", target = "documentId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "mobileNumber", target = "mobileNumber")
    @Mapping(source = "birthdate", target = "birthdate")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "active", target = "isActive")
    User addRequestDtotoModel(AddUserRequestDto addUserRequestDto);
}
