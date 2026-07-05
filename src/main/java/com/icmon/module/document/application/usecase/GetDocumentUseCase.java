package com.icmon.module.document.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.document.application.interfaces.DocumentService;
import com.icmon.module.document.presentation.dto.response.DocumentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetDocumentUseCase {

    private final DocumentService documentService;

    public DocumentResponseDTO byId(UUID id) throws SystemGlobalException {
        return documentService.getDocument(id);
    }

    public DocumentResponseDTO byReference(String referenceType, UUID referenceId) throws SystemGlobalException {
        return documentService.getDocumentByReference(referenceType, referenceId);
    }
}
