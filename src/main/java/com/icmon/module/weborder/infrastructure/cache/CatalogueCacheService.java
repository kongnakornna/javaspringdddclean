package com.icmon.module.weborder.infrastructure.cache;

import com.icmon.module.weborder.domain.MCatalogueItem;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CatalogueCacheService {

    @Cacheable(value = "catalogue", key = "#itemId")
    public MCatalogueItem getItem(UUID itemId) {
        return null;
    }

    @Cacheable(value = "catalogue_code", key = "#itemCode")
    public MCatalogueItem getItemByCode(String itemCode) {
        return null;
    }

    @Cacheable(value = "catalogue_category", key = "#categoryId")
    public List<MCatalogueItem> getItemsByCategory(UUID categoryId) {
        return null;
    }

    @CachePut(value = "catalogue", key = "#item.id")
    public MCatalogueItem saveItem(MCatalogueItem item) {
        return item;
    }

    @CacheEvict(value = {"catalogue", "catalogue_code"}, key = "#itemId")
    public void evictItem(UUID itemId) {
    }

    @CacheEvict(value = {"catalogue", "catalogue_code", "catalogue_category"}, allEntries = true)
    public void evictAllCatalogues() {
    }
}
