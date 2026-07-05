package com.icmon.module.document.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.document.application.interfaces.DocumentService;
import com.icmon.module.document.presentation.dto.request.DocumentGenerateRequestDTO;
import com.icmon.module.document.presentation.dto.response.DocumentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GenerateDocumentUseCase {

    private final DocumentService documentService;

    public DocumentResponseDTO execute(DocumentGenerateRequestDTO request) throws SystemGlobalException {
        return documentService.generateDocument(request);
    }
}
