package com.icmon.module.payment.infrastructure.repository;

import com.icmon.module.payment.infrastructure.entity.PaymentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID> {
    Optional<PaymentEntity> findByPaymentNo(String paymentNo);
    Optional<PaymentEntity> findByInvoiceId(UUID invoiceId);
    List<PaymentEntity> findByCustomerId(UUID customerId);
    List<PaymentEntity> findByStatus(String status);

    @Query("SELECT p FROM PaymentEntity p WHERE p.deleted = false " +
           "AND (:customerId IS NULL OR p.customerId = :customerId) " +
           "AND (:status IS NULL OR p.status = :status) " +
           "AND (:startDate IS NULL OR p.paymentDate >= :startDate) " +
           "AND (:endDate IS NULL OR p.paymentDate <= :endDate)")
    Page<PaymentEntity> searchPayments(
            @Param("customerId") UUID customerId,
            @Param("status") String status,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);
}
