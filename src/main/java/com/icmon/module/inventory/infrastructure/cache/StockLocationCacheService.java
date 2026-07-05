package com.icmon.module.inventory.infrastructure.cache;

import com.icmon.module.inventory.infrastructure.entity.StockLocationEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StockLocationCacheService {

    @Cacheable(value = "locations", key = "#id")
    public StockLocationEntity getLocation(UUID id) {
        return null;
    }

    @CachePut(value = "locations", key = "#entity.id")
    public StockLocationEntity cache(StockLocationEntity entity) {
        return entity;
    }

    @CacheEvict(value = "locations", key = "#id")
    public void evict(UUID id) {
    }

    @CacheEvict(value = "locations", allEntries = true)
    public void evictAll() {
    }
}
