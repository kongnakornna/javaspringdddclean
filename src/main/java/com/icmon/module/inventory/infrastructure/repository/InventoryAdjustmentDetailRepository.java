package com.icmon.module.inventory.infrastructure.repository;

import com.icmon.module.inventory.infrastructure.entity.InventoryAdjustmentDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InventoryAdjustmentDetailRepository extends JpaRepository<InventoryAdjustmentDetailEntity, UUID> {
    List<InventoryAdjustmentDetailEntity> findByAdjustmentHeaderId(UUID adjustmentHeaderId);
    List<InventoryAdjustmentDetailEntity> findByPartId(UUID partId);
}
