package com.icmon.module.dashboard.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "สรุปสถานะงาน / Job status summary response")
public class JobStatusSummaryResponseDTO {
    @Schema(description = "สถานะ / Status", example = "COMPLETED")
    private String status;
    @Schema(description = "จำนวน / Count", example = "75")
    private Long count;
    @Schema(description = "เปอร์เซ็นต์ / Percentage", example = "45.5")
    private Double percentage;
}
