package com.icmon.module.weborder.infrastructure.mapper;

import com.icmon.module.weborder.domain.MCatalogueItem;
import com.icmon.module.weborder.infrastructure.entity.CatalogueItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CatalogueItemMapper {
    MCatalogueItem toDomain(CatalogueItemEntity entity);

    CatalogueItemEntity toEntity(MCatalogueItem domain);
}
