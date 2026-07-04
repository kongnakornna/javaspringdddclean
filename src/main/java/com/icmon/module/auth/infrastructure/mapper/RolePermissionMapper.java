package com.icmon.module.auth.infrastructure.mapper;

import com.icmon.module.auth.infrastructure.entity.RolePermissionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RolePermissionMapper {
    // Mapping between RolePermissionEntity and domain if needed
    RolePermissionEntity toEntity(RolePermissionEntity entity);
}
