package com.icmon.module.document.infrastructure.mapper;

import com.icmon.module.document.domain.TDocument;
import com.icmon.module.document.infrastructure.entity.DocumentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocumentMapper {
    TDocument toDomain(DocumentEntity entity);
    DocumentEntity toEntity(TDocument domain);
}
