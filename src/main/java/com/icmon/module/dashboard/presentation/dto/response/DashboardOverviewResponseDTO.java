package com.icmon.module.dashboard.presentation.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DashboardOverviewResponseDTO {
    private Long totalInvoices;
    private BigDecimal totalRevenue;
    private BigDecimal totalOutstanding;
    private Long totalJobs;
    private Long totalCustomers;
    private Long lowStockCount;
    private Long totalPayments;
    private LocalDateTime cacheTimestamp;
}
