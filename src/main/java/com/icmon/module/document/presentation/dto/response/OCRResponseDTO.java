package com.icmon.module.document.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "ผลลัพธ์ OCR // OCR processing result")
public class OCRResponseDTO {
    @Schema(description = "ชื่อผู้ให้บริการ // Provider name", example = "TESSERACT")
    private String provider;

    @Schema(description = "ข้อความที่สกัดได้ // Extracted text", example = "ใบแจ้งหนี้เลขที่ INV-2026-0001")
    private String extractedText;

    @Schema(description = "คะแนนความเชื่อมั่น // Confidence score (0-100)", example = "95.5")
    private Double confidenceScore;

    @Schema(description = "สถานะ // Status (PROCESSING, COMPLETED, FAILED)", example = "COMPLETED")
    private String status;
}
