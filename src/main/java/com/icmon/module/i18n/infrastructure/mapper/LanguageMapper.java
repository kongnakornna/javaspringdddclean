package com.icmon.module.i18n.infrastructure.mapper;

import com.icmon.module.i18n.domain.MLanguage;
import com.icmon.module.i18n.infrastructure.entity.LanguageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LanguageMapper {

    MLanguage toDomain(LanguageEntity entity);

    LanguageEntity toEntity(MLanguage domain);
}
