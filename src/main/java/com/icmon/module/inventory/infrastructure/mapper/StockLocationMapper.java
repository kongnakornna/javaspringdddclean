package com.icmon.module.inventory.infrastructure.mapper;

import com.icmon.module.inventory.domain.StockLocation;
import com.icmon.module.inventory.infrastructure.entity.StockLocationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StockLocationMapper {
    StockLocation toDomain(StockLocationEntity entity);
    StockLocationEntity toEntity(StockLocation domain);
}
