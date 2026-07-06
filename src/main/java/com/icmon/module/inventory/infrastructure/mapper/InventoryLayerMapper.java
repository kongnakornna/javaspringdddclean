package com.icmon.module.inventory.infrastructure.mapper;

import com.icmon.module.inventory.domain.TInventoryLayer;
import com.icmon.module.inventory.infrastructure.entity.InventoryLayerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryLayerMapper {
    TInventoryLayer toDomain(InventoryLayerEntity entity);
    InventoryLayerEntity toEntity(TInventoryLayer domain);
}
