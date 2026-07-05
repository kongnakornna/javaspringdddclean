package com.icmon.module.payment.infrastructure.cache;

import com.icmon.module.payment.infrastructure.entity.ReceiptEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReceiptCacheService {

    @Cacheable(value = "receipts", key = "#receiptId")
    public ReceiptEntity getReceipt(UUID receiptId) {
        return null;
    }

    @Cacheable(value = "receipt_payment", key = "#paymentId")
    public ReceiptEntity getReceiptByPaymentId(UUID paymentId) {
        return null;
    }

    @CachePut(value = "receipts", key = "#entity.id")
    public ReceiptEntity saveReceipt(ReceiptEntity entity) {
        return entity;
    }

    @CacheEvict(value = {"receipts", "receipt_payment"}, key = "#receiptId")
    public void evictReceipt(UUID receiptId) {
    }
}
