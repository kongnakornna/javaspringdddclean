package com.icmon.module.document.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "ข้อมูลเอกสาร // Document response")
public class DocumentResponseDTO {
    @Schema(description = "ID ของเอกสาร // Document ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID id;

    @Schema(description = "เลขที่เอกสาร // Document number", example = "INV-2026-0001")
    private String documentNo;

    @Schema(description = "ประเภทเอกสาร // Document type", example = "INVOICE")
    private String documentType;

    @Schema(description = "ประเภทย่อยของเอกสาร // Document sub-type", example = "STANDARD")
    private String documentSubType;

    @Schema(description = "สถานะเอกสาร // Document status", example = "GENERATED")
    private String status;

    @Schema(description = "ประเภทของ reference // Reference type", example = "QUOTATION")
    private String referenceType;

    @Schema(description = "ID ของ reference // Reference ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID referenceId;

    @Schema(description = "วันที่สร้างเอกสาร // Generated at", example = "2026-07-05T10:30:00")
    private LocalDateTime generatedAt;

    @Schema(description = "วันที่อัปโหลดเอกสาร // Uploaded at", example = "2026-07-05T10:30:00")
    private LocalDateTime uploadedAt;

    @Schema(description = "ที่อยู่ไฟล์เอกสาร // File path", example = "/documents/inv-2026-0001.pdf")
    private String filePath;

    @Schema(description = "ชื่อไฟล์เอกสาร // File name", example = "inv-2026-0001.pdf")
    private String fileName;

    @Schema(description = "MIME type ของเอกสาร // MIME type", example = "application/pdf")
    private String mimeType;

    @Schema(description = "ขนาดไฟล์ (ไบต์) // File size (bytes)", example = "204800")
    private Long fileSize;

    @Schema(description = "คำอธิบาย // Description", example = "Invoice for July 2026")
    private String description;
}
