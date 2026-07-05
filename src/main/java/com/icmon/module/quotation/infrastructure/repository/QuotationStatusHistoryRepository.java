package com.icmon.module.quotation.infrastructure.repository;

import com.icmon.module.quotation.infrastructure.entity.QuotationStatusHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuotationStatusHistoryRepository extends JpaRepository<QuotationStatusHistoryEntity, UUID> {
    List<QuotationStatusHistoryEntity> findByQuotationIdOrderByChangedAtAsc(UUID quotationId);
}
