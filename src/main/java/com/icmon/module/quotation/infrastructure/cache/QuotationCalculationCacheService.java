package com.icmon.module.quotation.infrastructure.cache;

import com.icmon.module.quotation.domain.TQuotation;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class QuotationCalculationCacheService {

    @Cacheable(value = "quotation_calculation", key = "#quotationId")
    public TQuotation getCachedCalculation(UUID quotationId) {
        return null;
    }

    @CachePut(value = "quotation_calculation", key = "#quotation.id")
    public TQuotation cacheCalculation(TQuotation quotation) {
        return quotation;
    }

    @CacheEvict(value = "quotation_calculation", key = "#quotationId")
    public void evictCalculation(UUID quotationId) {
    }
}
