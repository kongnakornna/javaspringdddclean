package com.icmon.module.dashboard.infrastructure.cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ReportCacheService {

    @Cacheable(value = "reports", key = "#reportId")
    public String getReportStatus(String reportId) {
        return null;
    }

    @CachePut(value = "reports", key = "#reportId")
    public String updateReportStatus(String reportId, String status) {
        return status;
    }

    @CacheEvict(value = "reports", key = "#reportId")
    public void evictReport(String reportId) {
    }

    @CacheEvict(value = "reports", allEntries = true)
    public void evictAllReports() {
    }
}
