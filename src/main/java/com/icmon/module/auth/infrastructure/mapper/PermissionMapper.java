package com.icmon.module.auth.infrastructure.mapper;

import com.icmon.module.auth.domain.MPermission;
import com.icmon.module.auth.infrastructure.entity.PermissionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    MPermission toDomain(PermissionEntity entity);
    PermissionEntity toEntity(MPermission domain);
}
