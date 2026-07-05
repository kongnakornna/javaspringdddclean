package com.icmon.module.document.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.document.application.interfaces.OCRService;
import com.icmon.module.document.presentation.dto.request.OCRRequestDTO;
import com.icmon.module.document.presentation.dto.response.OCRResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessOCRUseCase {

    private final OCRService ocrService;

    public OCRResponseDTO execute(OCRRequestDTO request) throws SystemGlobalException {
        return ocrService.processOCR(request);
    }
}
