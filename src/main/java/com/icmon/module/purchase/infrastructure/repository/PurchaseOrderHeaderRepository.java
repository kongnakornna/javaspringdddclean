package com.icmon.module.purchase.infrastructure.repository;

import com.icmon.module.purchase.infrastructure.entity.PurchaseOrderHeaderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PurchaseOrderHeaderRepository extends JpaRepository<PurchaseOrderHeaderEntity, UUID> {
    Optional<PurchaseOrderHeaderEntity> findByPoNo(String poNo);
    List<PurchaseOrderHeaderEntity> findBySupplierId(UUID supplierId);
    List<PurchaseOrderHeaderEntity> findByStatus(String status);
    List<PurchaseOrderHeaderEntity> findByQuotationId(UUID quotationId);
    List<PurchaseOrderHeaderEntity> findByJobId(UUID jobId);

    @Query("SELECT p FROM PurchaseOrderHeaderEntity p WHERE p.deleted = false " +
           "AND (:status IS NULL OR p.status = :status) " +
           "AND (:supplierId IS NULL OR p.supplierId = :supplierId) " +
           "AND (:startDate IS NULL OR p.poDate >= :startDate) " +
           "AND (:endDate IS NULL OR p.poDate <= :endDate)")
    Page<PurchaseOrderHeaderEntity> searchPurchaseOrders(
            @Param("status") String status,
            @Param("supplierId") UUID supplierId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            Pageable pageable);
}
