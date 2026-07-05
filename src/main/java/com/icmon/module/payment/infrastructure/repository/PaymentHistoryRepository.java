package com.icmon.module.payment.infrastructure.repository;

import com.icmon.module.payment.infrastructure.entity.PaymentHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistoryEntity, UUID> {
    List<PaymentHistoryEntity> findByPaymentIdOrderByChangedAtDesc(UUID paymentId);
}
