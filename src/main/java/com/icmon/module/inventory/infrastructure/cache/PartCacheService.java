package com.icmon.module.inventory.infrastructure.cache;

import com.icmon.module.inventory.domain.MPartMaster;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class PartCacheService {
    @Cacheable(value = "part", key = "#partId")
    public MPartMaster getPart(UUID partId) { return null; }
    @Cacheable(value = "part_code", key = "#partCode")
    public MPartMaster getPartByCode(String partCode) { return null; }
    @CachePut(value = "part", key = "#part.id")
    public MPartMaster savePart(MPartMaster part) { return part; }
    @CacheEvict(value = {"part", "part_code"}, key = "#partId")
    public void evictPart(UUID partId) {}
    @CacheEvict(value = "low_stock_list", allEntries = true)
    public void evictLowStockCache() {}
}
