package com.icmon.module.dashboard.infrastructure.cache;

import com.icmon.module.dashboard.presentation.dto.response.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DashboardCacheService {

    @Cacheable(value = "dashboard_overview", key = "#whitelabelId")
    public DashboardOverviewResponseDTO getDashboardOverview(UUID whitelabelId) {
        return null;
    }

    @Cacheable(value = "sales_by_period", key = "#whitelabelId + ':' + #period")
    public List<RevenueResponseDTO> getSalesByPeriod(UUID whitelabelId, String period) {
        return null;
    }

    @Cacheable(value = "top_parts", key = "#whitelabelId")
    public List<TopPartsResponseDTO> getTopParts(UUID whitelabelId) {
        return null;
    }

    @CacheEvict(value = {
        "dashboard_overview",
        "sales_by_period",
        "top_parts",
        "job_status_summary",
        "inventory_overview"
    }, allEntries = true)
    public void evictAllDashboardCache() {
    }

    @CacheEvict(value = "dashboard_overview", key = "#whitelabelId")
    public void evictDashboardOverview(UUID whitelabelId) {
    }
}
