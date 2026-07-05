package com.icmon.module.purchase.infrastructure.repository;

import com.icmon.module.purchase.infrastructure.entity.PurchaseOrderStatusHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PurchaseOrderStatusHistoryRepository extends JpaRepository<PurchaseOrderStatusHistoryEntity, UUID> {
    List<PurchaseOrderStatusHistoryEntity> findByPoHeaderIdOrderByChangedAtAsc(UUID poHeaderId);
}
