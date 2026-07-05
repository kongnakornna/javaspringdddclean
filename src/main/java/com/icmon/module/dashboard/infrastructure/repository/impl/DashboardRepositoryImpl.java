package com.icmon.module.dashboard.infrastructure.repository.impl;

import com.icmon.module.dashboard.domain.*;
import com.icmon.module.dashboard.domain.valueobjects.DateRange;
import com.icmon.module.dashboard.infrastructure.query.DashboardNativeQuery;
import com.icmon.module.dashboard.infrastructure.repository.DashboardRepository;
import com.icmon.module.dashboard.presentation.dto.response.DashboardOverviewResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DashboardRepositoryImpl implements DashboardRepository {

    private final DashboardNativeQuery nativeQuery;

    @Override
    public DashboardOverviewResponseDTO getDashboardOverview(UUID whitelabelId) {
        DashboardOverviewResponseDTO dto = new DashboardOverviewResponseDTO();
        dto.setTotalInvoices(nativeQuery.countInvoices(whitelabelId));
        dto.setTotalRevenue(nativeQuery.sumRevenue(whitelabelId));
        dto.setTotalOutstanding(nativeQuery.sumOutstanding(whitelabelId));
        dto.setTotalJobs(nativeQuery.countJobs(whitelabelId));
        dto.setTotalCustomers(nativeQuery.countCustomers(whitelabelId));
        dto.setLowStockCount(nativeQuery.countLowStock(whitelabelId));
        dto.setTotalPayments(nativeQuery.countPayments(whitelabelId));
        return dto;
    }

    @Override
    public DSalesOverview getSalesOverview(String period, DateRange dateRange, UUID whitelabelId) {
        return nativeQuery.getSalesOverview(period, dateRange, whitelabelId);
    }

    @Override
    public List<DJobStatusSummary> getJobStatusSummary(UUID whitelabelId) {
        return nativeQuery.getJobStatusSummary(whitelabelId);
    }

    @Override
    public DInventoryOverview getInventoryOverview(UUID whitelabelId) {
        return nativeQuery.getInventoryOverview(whitelabelId);
    }

    @Override
    public List<DTopSellingParts> getTopParts(int limit, UUID whitelabelId) {
        return nativeQuery.getTopParts(limit, whitelabelId);
    }

    @Override
    public List<DServiceCategory> getServiceCategory(UUID whitelabelId) {
        return nativeQuery.getServiceCategory(whitelabelId);
    }

    @Override
    public List<DRevenueByPeriod> getRevenueByPeriod(String period, int months, UUID whitelabelId) {
        return nativeQuery.getRevenueByPeriod(period, months, whitelabelId);
    }

    @Override
    public List<DFinancialSummary> getFinancialSummary(DateRange dateRange, UUID whitelabelId) {
        return nativeQuery.getFinancialSummary(dateRange, whitelabelId);
    }
}
