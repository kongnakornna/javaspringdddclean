package com.icmon.module.document.infrastructure.ocr;

import com.icmon.module.document.presentation.dto.response.OCRResponseDTO;

public interface OCRProvider {
    OCRResponseDTO processImage(String imageUrl, String language);
    String getProviderName();
}
