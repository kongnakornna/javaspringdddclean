package com.icmon.module.dashboard.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Schema(description = "สรุปข้อมูลทางการเงิน / Financial summary response")
public class FinancialSummaryResponseDTO {
    @Schema(description = "รายได้รวม / Total income", example = "500000.00")
    private BigDecimal totalIncome;
    @Schema(description = "ค่าใช้จ่ายรวม / Total expense", example = "350000.00")
    private BigDecimal totalExpense;
    @Schema(description = "กำไรสุทธิ / Net income", example = "150000.00")
    private BigDecimal netIncome;
    @Schema(description = "วันที่เริ่มต้น / Start date", example = "2024-01-01")
    private LocalDate startDate;
    @Schema(description = "วันที่สิ้นสุด / End date", example = "2024-12-31")
    private LocalDate endDate;
}
