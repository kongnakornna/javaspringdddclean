package com.icmon.module.document.application.interfaces;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.document.presentation.dto.request.OCRRequestDTO;
import com.icmon.module.document.presentation.dto.response.OCRResponseDTO;

public interface OCRService {
    OCRResponseDTO processOCR(OCRRequestDTO request) throws SystemGlobalException;
}
