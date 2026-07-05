package com.icmon.module.document.application.service;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.document.application.interfaces.OCRService;
import com.icmon.module.document.infrastructure.ocr.GoogleVisionOCRProvider;
import com.icmon.module.document.infrastructure.ocr.OCRProvider;
import com.icmon.module.document.infrastructure.ocr.TesseractOCRProvider;
import com.icmon.module.document.presentation.dto.request.OCRRequestDTO;
import com.icmon.module.document.presentation.dto.response.OCRResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OCRServiceImpl implements OCRService {

    private final TesseractOCRProvider tesseractProvider;
    private final GoogleVisionOCRProvider googleVisionProvider;

    @Override
    public OCRResponseDTO processOCR(OCRRequestDTO request) throws SystemGlobalException {
        log.info("Processing OCR for image: {}, provider: {}", request.getImageUrl(), request.getProvider());

        OCRProvider provider;
        if ("GOOGLE_VISION".equalsIgnoreCase(request.getProvider())) {
            provider = googleVisionProvider;
        } else {
            provider = tesseractProvider;
        }

        return provider.processImage(request.getImageUrl(), request.getLanguage());
    }
}
