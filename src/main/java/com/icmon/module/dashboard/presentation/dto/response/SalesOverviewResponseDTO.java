package com.icmon.module.dashboard.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Schema(description = "ข้อมูลภาพรวมยอดขาย / Sales overview response")
public class SalesOverviewResponseDTO {
    @Schema(description = "จำนวนใบแจ้งหนี้ / Total invoices", example = "85")
    private Integer totalInvoices;
    @Schema(description = "รายได้ทั้งหมด / Total revenue", example = "250000.00")
    private BigDecimal totalRevenue;
    @Schema(description = "รอบระยะเวลา / Period", example = "2024-12")
    private String period;
}
