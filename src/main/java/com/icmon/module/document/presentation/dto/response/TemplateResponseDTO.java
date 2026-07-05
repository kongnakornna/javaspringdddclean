package com.icmon.module.document.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "ข้อมูลเทมเพลตเอกสาร // Document template response")
public class TemplateResponseDTO {
    @Schema(description = "ID")
    private UUID id;

    @Schema(description = "รหัสเทมเพลต // Template code")
    private String templateCode;

    @Schema(description = "ชื่อเทมเพลต // Template name")
    private String templateName;

    @Schema(description = "ประเภทเทมเพลต // Template type")
    private String templateType;

    @Schema(description = "ที่อยู่ไฟล์ // File path")
    private String filePath;

    @Schema(description = "สถานะ active // Is active")
    private Boolean isActive;

    @Schema(description = "วันที่อัปโหลด // Uploaded at")
    private LocalDateTime uploadedAt;

    @Schema(description = "คำอธิบาย")
    private String description;
}
