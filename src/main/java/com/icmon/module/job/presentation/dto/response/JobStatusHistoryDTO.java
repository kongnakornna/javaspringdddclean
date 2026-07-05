package com.icmon.module.job.presentation.dto.response;

import com.icmon.module.job.domain.enums.JobStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "ประวัติสถานะงาน - Job status history")
@Data
@Builder
public class JobStatusHistoryDTO {
    @Schema(description = "รหัสประวัติ - History ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;
    @Schema(description = "รหัสงาน - Job ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID jobId;
    @Schema(description = "สถานะเดิม - From status", example = "PENDING")
    private JobStatus fromStatus;
    @Schema(description = "สถานะใหม่ - To status", example = "IN_PROGRESS")
    private JobStatus toStatus;
    @Schema(description = "ผู้เปลี่ยนสถานะ - Changed by", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID changedBy;
    @Schema(description = "วันที่เปลี่ยน - Changed at", example = "2024-01-15T10:30:00")
    private LocalDateTime changedAt;
    @Schema(description = "เหตุผล - Reason", example = "เริ่มดำเนินการซ่อม")
    private String reason;
}
