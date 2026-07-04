package com.icmon.module.auth.infrastructure.mapper;

import com.icmon.module.auth.domain.MUser;
import com.icmon.module.auth.infrastructure.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "email", expression = "java(entity.getEmail() != null ? new com.icmon.module.auth.domain.valueobjects.Email(entity.getEmail()) : null)")
    MUser toDomain(UserEntity entity);

    @Mapping(target = "email", expression = "java(domain.getEmail() != null ? domain.getEmail().getValue() : null)")
    UserEntity toEntity(MUser domain);
}
