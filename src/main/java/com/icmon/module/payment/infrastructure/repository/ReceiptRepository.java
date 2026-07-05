package com.icmon.module.payment.infrastructure.repository;

import com.icmon.module.payment.infrastructure.entity.ReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReceiptRepository extends JpaRepository<ReceiptEntity, UUID> {
    Optional<ReceiptEntity> findByReceiptNo(String receiptNo);
    Optional<ReceiptEntity> findByPaymentId(UUID paymentId);
}
