package com.icmon.module.document.infrastructure.ocr;

import com.icmon.module.document.presentation.dto.response.OCRResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GoogleVisionOCRProvider implements OCRProvider {

    @Override
    public OCRResponseDTO processImage(String imageUrl, String language) {
        log.info("Processing OCR via Google Vision for image: {}, language: {}", imageUrl, language);
        OCRResponseDTO dto = new OCRResponseDTO();
        dto.setProvider("GOOGLE_VISION");
        dto.setExtractedText("[Google Vision OCR placeholder]");
        dto.setConfidenceScore(95.0);
        dto.setStatus("COMPLETED");
        return dto;
    }

    @Override
    public String getProviderName() {
        return "GOOGLE_VISION";
    }
}
