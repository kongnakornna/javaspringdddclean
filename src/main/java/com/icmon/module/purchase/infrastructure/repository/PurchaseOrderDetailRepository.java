package com.icmon.module.purchase.infrastructure.repository;

import com.icmon.module.purchase.infrastructure.entity.PurchaseOrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PurchaseOrderDetailRepository extends JpaRepository<PurchaseOrderDetailEntity, UUID> {
    List<PurchaseOrderDetailEntity> findByPoHeaderId(UUID poHeaderId);
    void deleteByPoHeaderId(UUID poHeaderId);
}
