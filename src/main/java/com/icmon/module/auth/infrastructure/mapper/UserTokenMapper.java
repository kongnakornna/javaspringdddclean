package com.icmon.module.auth.infrastructure.mapper;

import com.icmon.module.auth.domain.MUserToken;
import com.icmon.module.auth.infrastructure.entity.UserTokenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserTokenMapper {
    @Mapping(source = "userId", target = "tokenUserId")
    MUserToken toDomain(UserTokenEntity entity);

    @Mapping(source = "tokenUserId", target = "userId")
    UserTokenEntity toEntity(MUserToken domain);
}
