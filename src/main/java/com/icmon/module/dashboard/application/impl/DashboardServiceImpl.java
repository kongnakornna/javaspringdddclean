package com.icmon.module.dashboard.application.impl;

import com.icmon._shared.application.GenericAuthDomainServiceImpl;
import com.icmon.exception.SystemGlobalException;
import com.icmon.module.dashboard.application.interfaces.DashboardService;
import com.icmon.module.dashboard.domain.*;
import com.icmon.module.dashboard.domain.valueobjects.DateRange;
import com.icmon.module.dashboard.infrastructure.cache.DashboardCacheService;
import com.icmon.module.dashboard.infrastructure.repository.DashboardRepository;
import com.icmon.module.dashboard.presentation.dto.request.DashboardFilterRequestDTO;
import com.icmon.module.dashboard.presentation.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl extends GenericAuthDomainServiceImpl implements DashboardService {

    private final DashboardRepository dashboardRepository;
    private final DashboardCacheService cacheService;

    @Override
    public DashboardOverviewResponseDTO getDashboardOverview() throws SystemGlobalException {
        UUID whitelabelId = getWhitelabelId();
        DashboardOverviewResponseDTO cached = cacheService.getDashboardOverview(whitelabelId);
        if (cached != null) {
            return cached;
        }
        DashboardOverviewResponseDTO response = dashboardRepository.getDashboardOverview(whitelabelId);
        response.setCacheTimestamp(LocalDateTime.now());
        return response;
    }

    @Override
    public SalesOverviewResponseDTO getSalesOverview(String period, String startDate, String endDate) throws SystemGlobalException {
        if (period == null || period.isEmpty()) period = "MONTH";
        DateRange dateRange = DateRange.of(startDate, endDate);
        DSalesOverview domain = dashboardRepository.getSalesOverview(period, dateRange, getWhitelabelId());
        SalesOverviewResponseDTO dto = new SalesOverviewResponseDTO();
        dto.setTotalInvoices(domain.getTotalInvoices());
        dto.setTotalRevenue(domain.getTotalRevenue());
        dto.setPeriod(period);
        return dto;
    }

    @Override
    public List<JobStatusSummaryResponseDTO> getJobStatusSummary() throws SystemGlobalException {
        List<DJobStatusSummary> domainList = dashboardRepository.getJobStatusSummary(getWhitelabelId());
        long total = domainList.stream().mapToLong(DJobStatusSummary::getCount).sum();
        return domainList.stream().map(d -> {
            JobStatusSummaryResponseDTO dto = new JobStatusSummaryResponseDTO();
            dto.setStatus(d.getStatus());
            dto.setCount(d.getCount());
            dto.setPercentage(d.getPercentage(total));
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public InventoryOverviewResponseDTO getInventoryOverview() throws SystemGlobalException {
        DInventoryOverview domain = dashboardRepository.getInventoryOverview(getWhitelabelId());
        InventoryOverviewResponseDTO dto = new InventoryOverviewResponseDTO();
        dto.setTotalParts(domain.getTotalParts());
        dto.setTotalQuantity(domain.getTotalQuantity());
        dto.setTotalValue(domain.getTotalValue());
        dto.setLowStockCount(domain.getLowStockCount());
        dto.setActiveParts(domain.getActiveParts());
        return dto;
    }

    @Override
    public List<TopPartsResponseDTO> getTopParts(int limit) throws SystemGlobalException {
        List<DTopSellingParts> domainList = dashboardRepository.getTopParts(limit, getWhitelabelId());
        return domainList.stream().map(d -> {
            TopPartsResponseDTO dto = new TopPartsResponseDTO();
            dto.setPartId(d.getPartId());
            dto.setPartCode(d.getPartCode());
            dto.setPartName(d.getPartName());
            dto.setTotalSold(d.getTotalSold());
            dto.setTotalRevenue(d.getTotalRevenue());
            dto.setRank(d.getRank());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ServiceCategoryResponseDTO> getServiceCategory() throws SystemGlobalException {
        List<DServiceCategory> domainList = dashboardRepository.getServiceCategory(getWhitelabelId());
        return domainList.stream().map(d -> {
            ServiceCategoryResponseDTO dto = new ServiceCategoryResponseDTO();
            dto.setCategoryName(d.getCategoryName());
            dto.setServiceCount(d.getServiceCount());
            dto.setTotalRevenue(d.getTotalRevenue());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<RevenueResponseDTO> getRevenueByPeriod(String period, Integer months) throws SystemGlobalException {
        if (period == null) period = "MONTH";
        if (months == null) months = 12;
        List<DRevenueByPeriod> domainList = dashboardRepository.getRevenueByPeriod(period, months, getWhitelabelId());
        return domainList.stream().map(d -> {
            RevenueResponseDTO dto = new RevenueResponseDTO();
            dto.setPeriod(d.getPeriod());
            dto.setInvoiceCount(d.getInvoiceCount());
            dto.setRevenue(d.getRevenue());
            dto.setAverageRevenue(d.getAverageRevenue());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public FinancialSummaryResponseDTO getFinancialSummary(String startDate, String endDate) throws SystemGlobalException {
        DateRange dateRange = DateRange.of(startDate, endDate);
        List<DFinancialSummary> domainList = dashboardRepository.getFinancialSummary(dateRange, getWhitelabelId());
        FinancialSummaryResponseDTO dto = new FinancialSummaryResponseDTO();
        BigDecimal totalIncome = domainList.stream().map(DFinancialSummary::getTotalInvoice).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalExpense = domainList.stream().map(DFinancialSummary::getTotalPayment).reduce(BigDecimal.ZERO, BigDecimal::add);
        dto.setTotalIncome(totalIncome);
        dto.setTotalExpense(totalExpense);
        dto.setNetIncome(totalIncome.subtract(totalExpense));
        dto.setStartDate(dateRange.startDate());
        dto.setEndDate(dateRange.endDate());
        return dto;
    }

    @Override
    public DashboardOverviewResponseDTO getFilteredDashboard(DashboardFilterRequestDTO request) throws SystemGlobalException {
        return dashboardRepository.getDashboardOverview(getWhitelabelId());
    }

}
