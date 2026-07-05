package com.icmon.module.inventory.infrastructure.cache;

import com.icmon.module.inventory.infrastructure.entity.PartMasterEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PartMasterCacheService {

    @Cacheable(value = "parts", key = "#id")
    public PartMasterEntity getPart(UUID id) {
        return null;
    }

    @Cacheable(value = "parts_by_code", key = "#partCode")
    public PartMasterEntity getPartByCode(String partCode) {
        return null;
    }

    @CachePut(value = "parts", key = "#entity.id")
    public PartMasterEntity cache(PartMasterEntity entity) {
        return entity;
    }

    @CacheEvict(value = {"parts", "parts_by_code"}, key = "#id")
    public void evict(UUID id) {
    }

    @CacheEvict(value = {"parts", "parts_by_code"}, allEntries = true)
    public void evictAll() {
    }
}
