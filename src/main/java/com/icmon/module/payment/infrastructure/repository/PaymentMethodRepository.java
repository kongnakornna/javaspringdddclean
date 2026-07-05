package com.icmon.module.payment.infrastructure.repository;

import com.icmon.module.payment.infrastructure.entity.PaymentMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethodEntity, UUID> {
    Optional<PaymentMethodEntity> findByMethodCode(String methodCode);
    List<PaymentMethodEntity> findByIsActiveTrue();
}
