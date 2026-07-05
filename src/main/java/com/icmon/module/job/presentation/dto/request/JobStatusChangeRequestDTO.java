package com.icmon.module.job.presentation.dto.request;

import com.icmon.module.job.domain.enums.JobStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "คำขอเปลี่ยนสถานะงาน - Job status change request")
@Data
public class JobStatusChangeRequestDTO {

    @NotNull(message = "New status is required")
    @Schema(description = "สถานะใหม่ - New status", example = "IN_PROGRESS", requiredMode = Schema.RequiredMode.REQUIRED)
    private JobStatus newStatus;

    @Schema(description = "เหตุผลในการเปลี่ยนสถานะ - Reason", example = "เริ่มดำเนินการซ่อมแล้ว")
    private String reason;
}
