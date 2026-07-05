package com.icmon.module.inventory.infrastructure.cache;

import com.icmon.module.inventory.infrastructure.entity.InventoryEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InventoryCacheService {

    @Cacheable(value = "inventory_transactions", key = "#id")
    public InventoryEntity getTransaction(UUID id) {
        return null;
    }

    @CachePut(value = "inventory_transactions", key = "#entity.id")
    public InventoryEntity cache(InventoryEntity entity) {
        return entity;
    }

    @CacheEvict(value = "inventory_transactions", key = "#id")
    public void evict(UUID id) {
    }

    @CacheEvict(value = "inventory_transactions", allEntries = true)
    public void evictAll() {
    }
}
