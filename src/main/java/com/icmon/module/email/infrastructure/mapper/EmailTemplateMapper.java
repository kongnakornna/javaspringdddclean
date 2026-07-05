package com.icmon.module.email.infrastructure.mapper;

import com.icmon.module.email.domain.MEmailTemplate;
import com.icmon.module.email.infrastructure.entity.EmailTemplateEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailTemplateMapper {

    MEmailTemplate toDomain(EmailTemplateEntity entity);

    EmailTemplateEntity toEntity(MEmailTemplate domain);
}
