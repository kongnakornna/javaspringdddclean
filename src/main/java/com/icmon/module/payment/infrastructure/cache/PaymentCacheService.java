package com.icmon.module.payment.infrastructure.cache;

import com.icmon.module.payment.infrastructure.entity.PaymentEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentCacheService {

    @Cacheable(value = "payments", key = "#paymentId")
    public PaymentEntity getPayment(UUID paymentId) {
        return null;
    }

    @Cacheable(value = "payment_invoice", key = "#invoiceId")
    public PaymentEntity getPaymentByInvoiceId(UUID invoiceId) {
        return null;
    }

    @CachePut(value = "payments", key = "#entity.id")
    public PaymentEntity savePayment(PaymentEntity entity) {
        return entity;
    }

    @CacheEvict(value = {"payments", "payment_invoice"}, key = "#paymentId")
    public void evictPayment(UUID paymentId) {
    }

    @CacheEvict(value = {"payments", "payment_invoice"}, allEntries = true)
    public void evictAllPayments() {
    }
}
