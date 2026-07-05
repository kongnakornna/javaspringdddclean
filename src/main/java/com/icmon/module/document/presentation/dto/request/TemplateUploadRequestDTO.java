package com.icmon.module.document.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "คำขออัปโหลดเทมเพลต // Template upload request")
public class TemplateUploadRequestDTO {
    @Schema(description = "รหัสเทมเพลต // Template code", example = "INV-TPL-001")
    private String templateCode;

    @Schema(description = "ชื่อเทมเพลต // Template name", example = "Invoice Template")
    private String templateName;

    @Schema(description = "ประเภทเทมเพลต // Template type (PDF, EXCEL, HTML)", example = "PDF")
    private String templateType;

    @Schema(description = "คำอธิบาย // Description", example = "Standard invoice template")
    private String description;
}
