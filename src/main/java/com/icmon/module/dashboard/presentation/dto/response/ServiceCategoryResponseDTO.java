package com.icmon.module.dashboard.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Schema(description = "หมวดหมู่บริการ / Service category response")
public class ServiceCategoryResponseDTO {
    @Schema(description = "ชื่อหมวดหมู่ / Category name", example = "เปลี่ยนถ่ายน้ำมันเครื่อง")
    private String categoryName;
    @Schema(description = "จำนวนบริการ / Service count", example = "120")
    private Long serviceCount;
    @Schema(description = "รายได้รวม / Total revenue", example = "360000.00")
    private BigDecimal totalRevenue;
}
