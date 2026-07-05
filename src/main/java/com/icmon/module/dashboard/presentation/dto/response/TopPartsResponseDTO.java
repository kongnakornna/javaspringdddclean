package com.icmon.module.dashboard.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Schema(description = "อะไหล่ขายดีอันดับต้น / Top parts response")
public class TopPartsResponseDTO {
    @Schema(description = "รหัสอะไหล่ / Part ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID partId;
    @Schema(description = "รหัสอะไหล่ / Part code", example = "PRT-001")
    private String partCode;
    @Schema(description = "ชื่ออะไหล่ / Part name", example = "กรองน้ำมันเครื่อง")
    private String partName;
    @Schema(description = "ยอดขายรวม / Total sold", example = "350")
    private Long totalSold;
    @Schema(description = "รายได้รวม / Total revenue", example = "175000.00")
    private BigDecimal totalRevenue;
    @Schema(description = "อันดับ / Rank", example = "1")
    private Integer rank;
}
