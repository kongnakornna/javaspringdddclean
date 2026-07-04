package com.icmon.module.auth.infrastructure.mapper;

import com.icmon.module.auth.domain.MRole;
import com.icmon.module.auth.infrastructure.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "roleType", ignore = true)
    MRole toDomain(RoleEntity entity);
    RoleEntity toEntity(MRole domain);
}
