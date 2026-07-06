package com.icmon.module.inventory.infrastructure.repository;

import com.icmon.module.inventory.infrastructure.entity.PartMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PartMasterRepository extends JpaRepository<PartMasterEntity, UUID> {
    Optional<PartMasterEntity> findByPartCode(String partCode);
    @Query("SELECT p FROM PartMasterEntity p WHERE p.stockQuantity <= p.reorderLevel AND p.deleted = false")
    List<PartMasterEntity> findLowStockParts();
}
