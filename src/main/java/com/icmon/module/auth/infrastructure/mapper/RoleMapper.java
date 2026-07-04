package com.icmon.module.auth.infrastructure.mapper;

import com.icmon.module.auth.domain.MRole;
import com.icmon.module.auth.infrastructure.entity.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    MRole toDomain(RoleEntity entity);
    RoleEntity toEntity(MRole domain);
}
