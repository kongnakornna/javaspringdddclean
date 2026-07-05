package com.icmon.module.purchase.infrastructure.cache;

import com.icmon.module.purchase.infrastructure.entity.PurchaseOrderHeaderEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PurchaseOrderCacheService {

    @Cacheable(value = "purchase_orders", key = "#id")
    public PurchaseOrderHeaderEntity getPurchaseOrder(UUID id) {
        return null;
    }

    @Cacheable(value = "purchase_orders_by_po_no", key = "#poNo")
    public PurchaseOrderHeaderEntity getPurchaseOrderByPoNo(String poNo) {
        return null;
    }

    @CachePut(value = "purchase_orders", key = "#entity.id")
    public PurchaseOrderHeaderEntity cache(PurchaseOrderHeaderEntity entity) {
        return entity;
    }

    @CacheEvict(value = {"purchase_orders", "purchase_orders_by_po_no"}, key = "#id")
    public void evict(UUID id) {
    }

    @CacheEvict(value = {"purchase_orders", "purchase_orders_by_po_no"}, allEntries = true)
    public void evictAll() {
    }
}
