package com.icmon.module.document.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "คำขออัปโหลดเทมเพลต // Template upload request")
public class TemplateUploadRequestDTO {
    @Schema(description = "รหัสเทมเพลต // Template code")
    private String templateCode;

    @Schema(description = "ชื่อเทมเพลต // Template name")
    private String templateName;

    @Schema(description = "ประเภทเทมเพลต // Template type (PDF, EXCEL, HTML)")
    private String templateType;

    @Schema(description = "คำอธิบาย // Description")
    private String description;
}
