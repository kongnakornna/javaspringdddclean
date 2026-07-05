package com.icmon.module.document.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "คำขอสร้างเอกสารแบบอัตโนมัติ // Request to auto-generate a document")
public class DocumentGenerateRequestDTO {
    @NotBlank(message = "Template code is required")
    @Schema(description = "รหัสเทมเพลต // Template code", example = "INV-001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String templateCode;

    @NotBlank(message = "Document type is required")
    @Schema(description = "ประเภทเอกสาร // Document type (INVOICE, RECEIPT, etc.)", example = "INVOICE", requiredMode = Schema.RequiredMode.REQUIRED)
    private String documentType;

    @Schema(description = "ประเภทย่อยของเอกสาร // Document sub-type", example = "STANDARD")
    private String documentSubType;

    @Schema(description = "ประเภทของ reference ที่เอกสารอ้างอิงถึง // Reference type", example = "QUOTATION")
    private String referenceType;

    @Schema(description = "ID ของ reference // Reference entity UUID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private String referenceId;

    @Schema(description = "พารามิเตอร์สำหรับสร้างเอกสาร // JSON parameters for generation", example = "{\"date\": \"2026-07-05\"}")
    private String parameters;

    @Schema(description = "คำอธิบาย // Description note", example = "Invoice for July 2026")
    private String description;
}
