package com.icmon.module.auth.infrastructure.mapper;

import com.icmon.module.auth.infrastructure.entity.UserRoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {
    // Mapping between UserRoleEntity and domain if needed
    UserRoleEntity toEntity(UserRoleEntity entity);
}
