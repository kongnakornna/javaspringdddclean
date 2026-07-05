package com.icmon.module.document.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.document.application.interfaces.DocumentService;
import com.icmon.module.document.presentation.dto.response.DocumentHistoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetDocumentHistoryUseCase {

    private final DocumentService documentService;

    public List<DocumentHistoryDTO> execute(UUID id) throws SystemGlobalException {
        return documentService.getDocumentHistory(id);
    }
}
