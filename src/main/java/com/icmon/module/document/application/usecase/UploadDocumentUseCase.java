package com.icmon.module.document.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.document.application.interfaces.DocumentService;
import com.icmon.module.document.presentation.dto.response.DocumentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UploadDocumentUseCase {

    private final DocumentService documentService;

    public DocumentResponseDTO execute(MultipartFile file, String documentType, String referenceType, UUID referenceId, String description) throws SystemGlobalException {
        return documentService.uploadDocument(file, documentType, referenceType, referenceId, description);
    }
}
