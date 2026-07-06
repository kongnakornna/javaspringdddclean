package com.icmon.module.weborder.infrastructure.cache;

import com.icmon.module.weborder.domain.TWebOrder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderCacheService {

    @Cacheable(value = "orders", key = "#orderId")
    public TWebOrder getOrder(UUID orderId) {
        return null;
    }

    @Cacheable(value = "order_number", key = "#orderNo")
    public TWebOrder getOrderByNumber(String orderNo) {
        return null;
    }

    @CachePut(value = "orders", key = "#order.id")
    public TWebOrder saveOrder(TWebOrder order) {
        return order;
    }

    @CacheEvict(value = {"orders", "order_number"}, key = "#orderId")
    public void evictOrder(UUID orderId) {
    }

    @CacheEvict(value = {"orders", "order_number"}, allEntries = true)
    public void evictAllOrders() {
    }
}
