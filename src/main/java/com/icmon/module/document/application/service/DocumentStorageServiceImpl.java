package com.icmon.module.document.application.service;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.document.application.interfaces.DocumentStorageService;
import com.icmon.module.document.infrastructure.entity.DocumentEntity;
import com.icmon.module.document.infrastructure.repository.DocumentHistoryRepository;
import com.icmon.module.document.infrastructure.repository.DocumentRepository;
import com.icmon.module.document.infrastructure.storage.FileStorageService;
import org.springframework.beans.factory.annotation.Qualifier;
import com.icmon.module.document.presentation.dto.request.DocumentGenerateRequestDTO;
import com.icmon.module.document.presentation.dto.response.DocumentResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class DocumentStorageServiceImpl implements DocumentStorageService {

    private final DocumentRepository documentRepository;
    private final DocumentHistoryRepository documentHistoryRepository;
    private final FileStorageService fileStorageService;

    public DocumentStorageServiceImpl(DocumentRepository documentRepository,
                                      DocumentHistoryRepository documentHistoryRepository,
                                      @Qualifier("localFileStorageService") FileStorageService fileStorageService) {
        this.documentRepository = documentRepository;
        this.documentHistoryRepository = documentHistoryRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    @Transactional
    public DocumentResponseDTO storeGeneratedDocument(DocumentGenerateRequestDTO request, byte[] data, String fileName, String mimeType) throws SystemGlobalException {
        log.info("Storing generated document: fileName={}, type={}", fileName, request.getDocumentType());

        String filePath = fileStorageService.storeFile(fileName, data, "documents/generated");

        DocumentEntity entity = new DocumentEntity();
        entity.setDocumentNo("DOC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        entity.setDocumentType(request.getDocumentType());
        entity.setDocumentSubType(request.getDocumentSubType());
        entity.setStatus("GENERATED");
        entity.setReferenceType(request.getReferenceType());
        entity.setReferenceId(request.getReferenceId() != null ? UUID.fromString(request.getReferenceId()) : null);
        entity.setGeneratedAt(LocalDateTime.now());
        entity.setFilePath(filePath);
        entity.setFileName(fileName);
        entity.setMimeType(mimeType);
        entity.setFileSize((long) data.length);
        entity.setDescription(request.getDescription());

        entity = documentRepository.save(entity);

        com.icmon.module.document.infrastructure.entity.DocumentHistoryEntity history =
                new com.icmon.module.document.infrastructure.entity.DocumentHistoryEntity();
        history.setDocumentId(entity.getId());
        history.setAction("GENERATE");
        history.setDetails("Document generated from template: " + request.getTemplateCode());
        history.setPerformedBy(UUID.fromString("00000000-0000-0000-0000-000000000000"));
        history.setPerformedAt(LocalDateTime.now());
        documentHistoryRepository.save(history);

        DocumentResponseDTO dto = new DocumentResponseDTO();
        dto.setId(entity.getId());
        dto.setDocumentNo(entity.getDocumentNo());
        dto.setDocumentType(entity.getDocumentType());
        dto.setDocumentSubType(entity.getDocumentSubType());
        dto.setStatus(entity.getStatus());
        dto.setReferenceType(entity.getReferenceType());
        dto.setReferenceId(entity.getReferenceId());
        dto.setGeneratedAt(entity.getGeneratedAt());
        dto.setFilePath(entity.getFilePath());
        dto.setFileName(entity.getFileName());
        dto.setMimeType(entity.getMimeType());
        dto.setFileSize(entity.getFileSize());
        dto.setDescription(entity.getDescription());
        return dto;
    }
}
