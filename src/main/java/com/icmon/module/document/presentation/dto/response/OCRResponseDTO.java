package com.icmon.module.document.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "ผลลัพธ์ OCR // OCR processing result")
public class OCRResponseDTO {
    @Schema(description = "ชื่อผู้ให้บริการ // Provider name")
    private String provider;

    @Schema(description = "ข้อความที่สกัดได้ // Extracted text")
    private String extractedText;

    @Schema(description = "คะแนนความเชื่อมั่น // Confidence score (0-100)")
    private Double confidenceScore;

    @Schema(description = "สถานะ // Status (PROCESSING, COMPLETED, FAILED)")
    private String status;
}
