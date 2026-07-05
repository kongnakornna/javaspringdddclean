package com.icmon.module.inventory.infrastructure.mapper;

import com.icmon.module.inventory.domain.InventoryAdjustmentHeader;
import com.icmon.module.inventory.domain.InventoryAdjustmentDetail;
import com.icmon.module.inventory.infrastructure.entity.InventoryAdjustmentHeaderEntity;
import com.icmon.module.inventory.infrastructure.entity.InventoryAdjustmentDetailEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdjustmentMapper {
    InventoryAdjustmentHeader toDomain(InventoryAdjustmentHeaderEntity entity);
    InventoryAdjustmentHeaderEntity toEntity(InventoryAdjustmentHeader domain);
    InventoryAdjustmentDetail toDetailDomain(InventoryAdjustmentDetailEntity entity);
    InventoryAdjustmentDetailEntity toDetailEntity(InventoryAdjustmentDetail domain);
}
