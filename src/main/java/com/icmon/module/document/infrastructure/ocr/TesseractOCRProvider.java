package com.icmon.module.document.infrastructure.ocr;

import com.icmon.module.document.presentation.dto.response.OCRResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TesseractOCRProvider implements OCRProvider {

    @Override
    public OCRResponseDTO processImage(String imageUrl, String language) {
        log.info("Processing OCR via Tesseract for image: {}, language: {}", imageUrl, language);
        OCRResponseDTO dto = new OCRResponseDTO();
        dto.setProvider("TESSERACT");
        dto.setExtractedText("[Tesseract OCR placeholder]");
        dto.setConfidenceScore(0.0);
        dto.setStatus("COMPLETED");
        return dto;
    }

    @Override
    public String getProviderName() {
        return "TESSERACT";
    }
}
