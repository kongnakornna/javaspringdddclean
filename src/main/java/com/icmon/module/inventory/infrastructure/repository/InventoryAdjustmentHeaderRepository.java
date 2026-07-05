package com.icmon.module.inventory.infrastructure.repository;

import com.icmon.module.inventory.infrastructure.entity.InventoryAdjustmentHeaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InventoryAdjustmentHeaderRepository extends JpaRepository<InventoryAdjustmentHeaderEntity, UUID> {
    Optional<InventoryAdjustmentHeaderEntity> findByAdjustmentNo(String adjustmentNo);
    List<InventoryAdjustmentHeaderEntity> findByStatus(String status);
    List<InventoryAdjustmentHeaderEntity> findByAdjustmentType(String adjustmentType);
}
