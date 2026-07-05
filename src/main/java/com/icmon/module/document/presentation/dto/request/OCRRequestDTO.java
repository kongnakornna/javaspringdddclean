package com.icmon.module.document.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "คำขอประมวลผล OCR // OCR processing request")
public class OCRRequestDTO {
    @Schema(description = "URL หรือ path ของรูปภาพ // Image URL or file path", example = "https://example.com/invoice.jpg")
    private String imageUrl;

    @Schema(description = "ภาษา // Language (tha, eng, jpn, etc.)", example = "tha+eng")
    private String language = "tha+eng";

    @Schema(description = "ผู้ให้บริการ OCR // OCR provider (TESSERACT, GOOGLE_VISION)", example = "TESSERACT")
    private String provider = "TESSERACT";
}
