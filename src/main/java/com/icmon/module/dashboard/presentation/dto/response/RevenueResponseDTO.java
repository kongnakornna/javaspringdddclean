package com.icmon.module.dashboard.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Schema(description = "ข้อมูลรายได้ / Revenue response")
public class RevenueResponseDTO {
    @Schema(description = "รอบระยะเวลา / Period", example = "2024-12-01")
    private LocalDate period;
    @Schema(description = "จำนวนใบแจ้งหนี้ / Invoice count", example = "95")
    private Integer invoiceCount;
    @Schema(description = "รายได้ / Revenue", example = "320000.00")
    private BigDecimal revenue;
    @Schema(description = "รายได้เฉลี่ย / Average revenue", example = "3368.42")
    private BigDecimal averageRevenue;
}
