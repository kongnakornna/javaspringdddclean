package com.icmon.module.dashboard.application.interfaces;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.dashboard.presentation.dto.request.DashboardFilterRequestDTO;
import com.icmon.module.dashboard.presentation.dto.response.*;

import java.util.List;

public interface DashboardService {
    DashboardOverviewResponseDTO getDashboardOverview() throws SystemGlobalException;
    SalesOverviewResponseDTO getSalesOverview(String period, String startDate, String endDate) throws SystemGlobalException;
    List<JobStatusSummaryResponseDTO> getJobStatusSummary() throws SystemGlobalException;
    InventoryOverviewResponseDTO getInventoryOverview() throws SystemGlobalException;
    List<TopPartsResponseDTO> getTopParts(int limit) throws SystemGlobalException;
    List<ServiceCategoryResponseDTO> getServiceCategory() throws SystemGlobalException;
    List<RevenueResponseDTO> getRevenueByPeriod(String period, Integer months) throws SystemGlobalException;
    FinancialSummaryResponseDTO getFinancialSummary(String startDate, String endDate) throws SystemGlobalException;
    DashboardOverviewResponseDTO getFilteredDashboard(DashboardFilterRequestDTO request) throws SystemGlobalException;
}
