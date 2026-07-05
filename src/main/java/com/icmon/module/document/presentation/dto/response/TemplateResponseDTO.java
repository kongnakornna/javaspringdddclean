package com.icmon.module.document.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "ข้อมูลเทมเพลตเอกสาร // Document template response")
public class TemplateResponseDTO {
    @Schema(description = "ID // ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID id;

    @Schema(description = "รหัสเทมเพลต // Template code", example = "INV-TPL-001")
    private String templateCode;

    @Schema(description = "ชื่อเทมเพลต // Template name", example = "Invoice Template")
    private String templateName;

    @Schema(description = "ประเภทเทมเพลต // Template type", example = "PDF")
    private String templateType;

    @Schema(description = "ที่อยู่ไฟล์ // File path", example = "/templates/inv-tpl-001.pdf")
    private String filePath;

    @Schema(description = "สถานะ active // Is active", example = "true")
    private Boolean isActive;

    @Schema(description = "วันที่อัปโหลด // Uploaded at", example = "2026-07-05T10:30:00")
    private LocalDateTime uploadedAt;

    @Schema(description = "คำอธิบาย // Description", example = "Standard invoice template")
    private String description;
}
