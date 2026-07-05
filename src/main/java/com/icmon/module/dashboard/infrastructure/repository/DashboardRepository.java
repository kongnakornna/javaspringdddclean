package com.icmon.module.dashboard.infrastructure.repository;

import com.icmon.module.dashboard.domain.*;
import com.icmon.module.dashboard.domain.valueobjects.DateRange;
import com.icmon.module.dashboard.presentation.dto.response.DashboardOverviewResponseDTO;

import java.util.List;
import java.util.UUID;

public interface DashboardRepository {
    DashboardOverviewResponseDTO getDashboardOverview(UUID whitelabelId);
    DSalesOverview getSalesOverview(String period, DateRange dateRange, UUID whitelabelId);
    List<DJobStatusSummary> getJobStatusSummary(UUID whitelabelId);
    DInventoryOverview getInventoryOverview(UUID whitelabelId);
    List<DTopSellingParts> getTopParts(int limit, UUID whitelabelId);
    List<DServiceCategory> getServiceCategory(UUID whitelabelId);
    List<DRevenueByPeriod> getRevenueByPeriod(String period, int months, UUID whitelabelId);
    List<DFinancialSummary> getFinancialSummary(DateRange dateRange, UUID whitelabelId);
}
