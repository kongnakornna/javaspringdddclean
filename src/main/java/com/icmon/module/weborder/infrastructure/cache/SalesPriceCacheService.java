package com.icmon.module.weborder.infrastructure.cache;

import com.icmon.module.weborder.domain.MSalesPrice;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SalesPriceCacheService {

    @Cacheable(value = "sales_price_item", key = "#itemId")
    public List<MSalesPrice> getPricesByItem(UUID itemId) {
        return null;
    }

    @CachePut(value = "sales_price_item", key = "#price.itemId")
    public MSalesPrice savePrice(MSalesPrice price) {
        return price;
    }

    @CacheEvict(value = "sales_price_item", key = "#itemId")
    public void evictPrices(UUID itemId) {
    }
}
