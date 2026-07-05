package com.icmon.module.document.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "ประวัติเอกสาร // Document history entry")
public class DocumentHistoryDTO {
    @Schema(description = "ID // ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID id;

    @Schema(description = "ID ของเอกสาร // Document ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID documentId;

    @Schema(description = "การกระทำ // Action", example = "GENERATE")
    private String action;

    @Schema(description = "รายละเอียด // Details", example = "Document generated from quotation Q-2026-001")
    private String details;

    @Schema(description = "ผู้ดำเนินการ // Performed by", example = "admin@icmon.com")
    private String performedBy;

    @Schema(description = "วันที่ดำเนินการ // Performed at", example = "2026-07-05T10:30:00")
    private LocalDateTime performedAt;
}
