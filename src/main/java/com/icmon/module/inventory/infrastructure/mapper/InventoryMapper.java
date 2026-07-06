package com.icmon.module.inventory.infrastructure.mapper;

import com.icmon.module.inventory.domain.TInventory;
import com.icmon.module.inventory.infrastructure.entity.InventoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    TInventory toDomain(InventoryEntity entity);
    InventoryEntity toEntity(TInventory domain);
}
