package com.icmon.module.inventory.infrastructure.repository;

import com.icmon.module.inventory.infrastructure.entity.InventoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, UUID> {
    List<InventoryEntity> findByPartId(UUID partId);
    List<InventoryEntity> findByTransactionType(String transactionType);
    List<InventoryEntity> findByReferenceTypeAndReferenceId(String referenceType, UUID referenceId);

    @Query("SELECT i FROM InventoryEntity i WHERE i.partId = :partId " +
           "AND i.transactionDate BETWEEN :startDate AND :endDate " +
           "ORDER BY i.transactionDate DESC")
    List<InventoryEntity> findByPartIdAndDateRange(
            @Param("partId") UUID partId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT i FROM InventoryEntity i WHERE i.deleted = false " +
           "AND (:partId IS NULL OR i.partId = :partId) " +
           "AND (:transactionType IS NULL OR i.transactionType = :transactionType) " +
           "AND (:startDate IS NULL OR i.transactionDate >= :startDate) " +
           "AND (:endDate IS NULL OR i.transactionDate <= :endDate)")
    Page<InventoryEntity> searchTransactions(
            @Param("partId") UUID partId,
            @Param("transactionType") String transactionType,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);
}
