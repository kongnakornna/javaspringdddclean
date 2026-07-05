package com.icmon.module.document.application.interfaces;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.document.presentation.dto.request.DocumentGenerateRequestDTO;
import com.icmon.module.document.presentation.dto.response.DocumentResponseDTO;

public interface DocumentStorageService {
    DocumentResponseDTO storeGeneratedDocument(DocumentGenerateRequestDTO request, byte[] data, String fileName, String mimeType) throws SystemGlobalException;
}
