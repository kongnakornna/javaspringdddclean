package com.icmon.module.quotation.infrastructure.repository;

import com.icmon.module.quotation.infrastructure.entity.QuotationPartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuotationPartRepository extends JpaRepository<QuotationPartEntity, UUID> {
    List<QuotationPartEntity> findByQuotationId(UUID quotationId);
}
