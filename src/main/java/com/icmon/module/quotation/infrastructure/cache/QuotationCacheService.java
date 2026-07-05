package com.icmon.module.quotation.infrastructure.cache;

import com.icmon.module.quotation.domain.TQuotation;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class QuotationCacheService {

    @Cacheable(value = "quotations", key = "#quotationId")
    public TQuotation getQuotation(UUID quotationId) {
        return null;
    }

    @Cacheable(value = "quotation_job", key = "#jobId")
    public TQuotation getQuotationByJobId(UUID jobId) {
        return null;
    }

    @CachePut(value = "quotations", key = "#quotation.id")
    public TQuotation saveQuotation(TQuotation quotation) {
        return quotation;
    }

    @CacheEvict(value = {"quotations", "quotation_job"}, key = "#quotationId")
    public void evictQuotation(UUID quotationId) {
    }

    @CacheEvict(value = {"quotations", "quotation_job"}, allEntries = true)
    public void evictAllQuotations() {
    }
}
