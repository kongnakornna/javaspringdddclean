package com.icmon.module.i18n.infrastructure.mapper;

import com.icmon.module.i18n.domain.MTranslation;
import com.icmon.module.i18n.infrastructure.entity.TranslationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TranslationMapper {

    MTranslation toDomain(TranslationEntity entity);

    TranslationEntity toEntity(MTranslation domain);
}
