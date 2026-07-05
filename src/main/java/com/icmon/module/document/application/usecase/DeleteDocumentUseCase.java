package com.icmon.module.document.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.document.application.interfaces.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteDocumentUseCase {

    private final DocumentService documentService;

    public void execute(UUID id) throws SystemGlobalException {
        documentService.deleteDocument(id);
    }
}
