package com.icmon.module.document.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "ประวัติเอกสาร // Document history entry")
public class DocumentHistoryDTO {
    @Schema(description = "ID")
    private UUID id;

    @Schema(description = "ID ของเอกสาร")
    private UUID documentId;

    @Schema(description = "การกระทำ // Action")
    private String action;

    @Schema(description = "รายละเอียด // Details")
    private String details;

    @Schema(description = "ผู้ดำเนินการ // Performed by")
    private String performedBy;

    @Schema(description = "วันที่ดำเนินการ // Performed at")
    private LocalDateTime performedAt;
}
