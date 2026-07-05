package com.icmon.module.inventory.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "คำขออนุมัติรายการปรับปรุงสต็อก | Adjustment Approve Request")
public class AdjustmentApproveRequestDTO {
    @Schema(description = "รหัสผู้อนุมัติ | Approved By", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID approvedBy;
    @Schema(description = "คำอธิบาย | Description", example = "อนุมัติการปรับปรุงสต็อกประจำเดือน")
    private String description;
}
