package com.icmon.module.inventory.infrastructure.repository;

import com.icmon.module.inventory.infrastructure.entity.InventoryLayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface InventoryLayerRepository extends JpaRepository<InventoryLayerEntity, UUID> {
    @Query("SELECT l FROM InventoryLayerEntity l WHERE l.partId = :partId AND l.isActive = true ORDER BY l.receivedDate ASC")
    List<InventoryLayerEntity> findActiveLayersByPartIdOrderByDateAsc(UUID partId);
}
