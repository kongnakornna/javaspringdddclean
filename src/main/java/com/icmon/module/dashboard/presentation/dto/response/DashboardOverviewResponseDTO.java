package com.icmon.module.dashboard.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "ข้อมูลภาพรวมแดชบอร์ด / Dashboard overview response")
public class DashboardOverviewResponseDTO {
    @Schema(description = "จำนวนใบแจ้งหนี้ทั้งหมด / Total invoices", example = "250")
    private Long totalInvoices;
    @Schema(description = "รายได้ทั้งหมด / Total revenue", example = "500000.00")
    private BigDecimal totalRevenue;
    @Schema(description = "ยอดค้างชำระทั้งหมด / Total outstanding", example = "75000.00")
    private BigDecimal totalOutstanding;
    @Schema(description = "จำนวนงานทั้งหมด / Total jobs", example = "180")
    private Long totalJobs;
    @Schema(description = "จำนวนลูกค้าทั้งหมด / Total customers", example = "120")
    private Long totalCustomers;
    @Schema(description = "จำนวนสินค้าคงคลังต่ำ / Low stock count", example = "15")
    private Long lowStockCount;
    @Schema(description = "จำนวนการชำระเงินทั้งหมด / Total payments", example = "200")
    private Long totalPayments;
    @Schema(description = "เวลาที่เก็บแคช / Cache timestamp", example = "2024-12-31T23:59:59")
    private LocalDateTime cacheTimestamp;
}
