package com.icmon.module.document.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "ข้อมูลเอกสาร // Document response")
public class DocumentResponseDTO {
    @Schema(description = "ID ของเอกสาร")
    private UUID id;

    @Schema(description = "เลขที่เอกสาร")
    private String documentNo;

    @Schema(description = "ประเภทเอกสาร")
    private String documentType;

    @Schema(description = "ประเภทย่อยของเอกสาร")
    private String documentSubType;

    @Schema(description = "สถานะเอกสาร")
    private String status;

    @Schema(description = "ประเภทของ reference")
    private String referenceType;

    @Schema(description = "ID ของ reference")
    private UUID referenceId;

    @Schema(description = "วันที่สร้างเอกสาร")
    private LocalDateTime generatedAt;

    @Schema(description = "วันที่อัปโหลดเอกสาร")
    private LocalDateTime uploadedAt;

    @Schema(description = "ที่อยู่ไฟล์เอกสาร")
    private String filePath;

    @Schema(description = "ชื่อไฟล์เอกสาร")
    private String fileName;

    @Schema(description = "MIME type ของเอกสาร")
    private String mimeType;

    @Schema(description = "ขนาดไฟล์ (ไบต์)")
    private Long fileSize;

    @Schema(description = "คำอธิบาย")
    private String description;
}
