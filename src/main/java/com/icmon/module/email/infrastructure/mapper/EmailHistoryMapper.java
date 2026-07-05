package com.icmon.module.email.infrastructure.mapper;

import com.icmon.module.email.domain.TEmailHistory;
import com.icmon.module.email.infrastructure.entity.EmailHistoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailHistoryMapper {

    TEmailHistory toDomain(EmailHistoryEntity entity);

    EmailHistoryEntity toEntity(TEmailHistory domain);
}
