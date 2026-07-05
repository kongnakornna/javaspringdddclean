package com.icmon.module.quotation.infrastructure.repository;

import com.icmon.module.quotation.infrastructure.entity.QuotationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuotationRepository extends JpaRepository<QuotationEntity, UUID> {
    Optional<QuotationEntity> findByQuotationNo(String quotationNo);

    Optional<QuotationEntity> findTopByJobIdAndDeletedFalseOrderByCreatedAtDesc(UUID jobId);

    @Query("SELECT q FROM QuotationEntity q WHERE q.deleted = false " +
           "AND (:status IS NULL OR q.status = :status) " +
           "AND (:customerId IS NULL OR q.customerId = :customerId) " +
           "AND (:startDate IS NULL OR q.quotationDate >= :startDate) " +
           "AND (:endDate IS NULL OR q.quotationDate <= :endDate)")
    Page<QuotationEntity> searchQuotations(
            @Param("status") String status,
            @Param("customerId") UUID customerId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            Pageable pageable);
}
