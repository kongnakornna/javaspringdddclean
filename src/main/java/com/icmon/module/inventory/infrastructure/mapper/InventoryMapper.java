package com.icmon.module.inventory.infrastructure.mapper;

import com.icmon.module.inventory.domain.Inventory;
import com.icmon.module.inventory.infrastructure.entity.InventoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    Inventory toDomain(InventoryEntity entity);
    InventoryEntity toEntity(Inventory domain);
}
