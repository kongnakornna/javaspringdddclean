package com.icmon.module.document.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "คำขอสร้างเอกสารแบบอัตโนมัติ // Request to auto-generate a document")
public class DocumentGenerateRequestDTO {
    @NotBlank(message = "Template code is required")
    @Schema(description = "รหัสเทมเพลต // Template code")
    private String templateCode;

    @NotBlank(message = "Document type is required")
    @Schema(description = "ประเภทเอกสาร // Document type (INVOICE, RECEIPT, etc.)")
    private String documentType;

    @Schema(description = "ประเภทย่อยของเอกสาร // Document sub-type")
    private String documentSubType;

    @Schema(description = "ประเภทของ reference ที่เอกสารอ้างอิงถึง // Reference type")
    private String referenceType;

    @Schema(description = "ID ของ reference // Reference entity UUID")
    private String referenceId;

    @Schema(description = "พารามิเตอร์สำหรับสร้างเอกสาร // JSON parameters for generation")
    private String parameters;

    @Schema(description = "คำอธิบาย // Description note")
    private String description;
}
