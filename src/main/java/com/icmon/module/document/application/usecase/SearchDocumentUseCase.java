package com.icmon.module.document.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.document.application.interfaces.DocumentService;
import com.icmon.module.document.presentation.dto.request.DocumentSearchRequestDTO;
import com.icmon.module.document.presentation.dto.response.DocumentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchDocumentUseCase {

    private final DocumentService documentService;

    public Page<DocumentResponseDTO> execute(DocumentSearchRequestDTO request, Pageable pageable) throws SystemGlobalException {
        return documentService.searchDocuments(request, pageable);
    }
}
