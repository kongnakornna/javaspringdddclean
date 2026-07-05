package com.icmon.module.document.infrastructure.mapper;

import com.icmon.module.document.domain.MDocumentTemplate;
import com.icmon.module.document.infrastructure.entity.DocumentTemplateEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocumentTemplateMapper {
    MDocumentTemplate toDomain(DocumentTemplateEntity entity);
    DocumentTemplateEntity toEntity(MDocumentTemplate domain);
}
