package com.icmon.module.dashboard.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.dashboard.application.interfaces.DashboardService;
import com.icmon.module.dashboard.presentation.dto.request.DashboardFilterRequestDTO;
import com.icmon.module.dashboard.presentation.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
@Tag(name = "Dashboard", description = "Dashboard and Analytics APIs")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/overview")
    @RateLimit(limit = 30, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Get dashboard overview")
    public ResponseEntity<DashboardOverviewResponseDTO> getDashboardOverview() throws SystemGlobalException {
        DashboardOverviewResponseDTO response = dashboardService.getDashboardOverview();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/sales")
    @RateLimit(limit = 30, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Get sales overview")
    public ResponseEntity<SalesOverviewResponseDTO> getSalesOverview(
            @RequestParam(required = false) String period,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) throws SystemGlobalException {
        SalesOverviewResponseDTO response = dashboardService.getSalesOverview(period, startDate, endDate);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/job-status")
    @RateLimit(limit = 30, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Get job status summary")
    public ResponseEntity<List<JobStatusSummaryResponseDTO>> getJobStatusSummary() throws SystemGlobalException {
        List<JobStatusSummaryResponseDTO> response = dashboardService.getJobStatusSummary();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/inventory")
    @RateLimit(limit = 30, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Get inventory overview")
    public ResponseEntity<InventoryOverviewResponseDTO> getInventoryOverview() throws SystemGlobalException {
        InventoryOverviewResponseDTO response = dashboardService.getInventoryOverview();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/top-parts")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Get top selling parts")
    public ResponseEntity<List<TopPartsResponseDTO>> getTopParts(
            @RequestParam(defaultValue = "10") int limit) throws SystemGlobalException {
        List<TopPartsResponseDTO> response = dashboardService.getTopParts(limit);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/service-category")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Get revenue by service category")
    public ResponseEntity<List<ServiceCategoryResponseDTO>> getServiceCategory() throws SystemGlobalException {
        List<ServiceCategoryResponseDTO> response = dashboardService.getServiceCategory();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/revenue")
    @RateLimit(limit = 25, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Get revenue by period")
    public ResponseEntity<List<RevenueResponseDTO>> getRevenueByPeriod(
            @RequestParam(defaultValue = "MONTH") String period,
            @RequestParam(required = false) Integer months) throws SystemGlobalException {
        List<RevenueResponseDTO> response = dashboardService.getRevenueByPeriod(period, months);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/financial")
    @RateLimit(limit = 15, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Get financial summary")
    public ResponseEntity<FinancialSummaryResponseDTO> getFinancialSummary(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) throws SystemGlobalException {
        FinancialSummaryResponseDTO response = dashboardService.getFinancialSummary(startDate, endDate);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/filtered")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Get filtered dashboard data")
    public ResponseEntity<DashboardOverviewResponseDTO> getFilteredDashboard(
            @Valid @RequestBody DashboardFilterRequestDTO request) throws SystemGlobalException {
        DashboardOverviewResponseDTO response = dashboardService.getFilteredDashboard(request);
        return ResponseEntity.ok(response);
    }
}
