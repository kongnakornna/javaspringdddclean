package com.icmon.module.quotation.infrastructure.repository;

import com.icmon.module.quotation.infrastructure.entity.QuotationServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuotationServiceRepository extends JpaRepository<QuotationServiceEntity, UUID> {
    List<QuotationServiceEntity> findByQuotationId(UUID quotationId);
}
