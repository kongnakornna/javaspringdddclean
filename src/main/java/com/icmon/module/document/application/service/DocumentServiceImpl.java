package com.icmon.module.document.application.service;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.document.application.interfaces.DocumentService;
import com.icmon.module.document.application.interfaces.DocumentStorageService;
import com.icmon.module.document.application.interfaces.ReportGenerationService;
import com.icmon.module.document.domain.TDocument;
import com.icmon.module.document.domain.enums.DocumentType;
import com.icmon.module.document.domain.enums.DocumentStatus;
import com.icmon.module.document.infrastructure.entity.DocumentEntity;
import com.icmon.module.document.infrastructure.entity.DocumentHistoryEntity;
import com.icmon.module.document.infrastructure.mapper.DocumentMapper;
import com.icmon.module.document.infrastructure.repository.DocumentHistoryRepository;
import com.icmon.module.document.infrastructure.repository.DocumentRepository;
import com.icmon.module.document.infrastructure.storage.FileStorageService;
import org.springframework.beans.factory.annotation.Qualifier;
import com.icmon.module.document.presentation.dto.request.DocumentGenerateRequestDTO;
import com.icmon.module.document.presentation.dto.request.DocumentSearchRequestDTO;
import com.icmon.module.document.presentation.dto.response.DocumentHistoryDTO;
import com.icmon.module.document.presentation.dto.response.DocumentResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentHistoryRepository documentHistoryRepository;
    private final DocumentMapper documentMapper;
    private final ReportGenerationService reportGenerationService;
    private final FileStorageService fileStorageService;
    private final DocumentStorageService documentStorageService;

    public DocumentServiceImpl(DocumentRepository documentRepository,
                               DocumentHistoryRepository documentHistoryRepository,
                               DocumentMapper documentMapper,
                               ReportGenerationService reportGenerationService,
                               @Qualifier("localFileStorageService") FileStorageService fileStorageService,
                               DocumentStorageService documentStorageService) {
        this.documentRepository = documentRepository;
        this.documentHistoryRepository = documentHistoryRepository;
        this.documentMapper = documentMapper;
        this.reportGenerationService = reportGenerationService;
        this.fileStorageService = fileStorageService;
        this.documentStorageService = documentStorageService;
    }

    @Override
    @Transactional
    public DocumentResponseDTO generateDocument(DocumentGenerateRequestDTO request) throws SystemGlobalException {
        log.info("Generating document from template: {}", request.getTemplateCode());

        byte[] data = reportGenerationService.generatePDF(request.getTemplateCode(), request.getParameters());
        String fileName = "doc_" + System.currentTimeMillis() + ".pdf";

        DocumentResponseDTO stored = documentStorageService.storeGeneratedDocument(request, data, fileName, "application/pdf");
        return stored;
    }

    @Override
    @Transactional
    public DocumentResponseDTO uploadDocument(MultipartFile file, String documentType, String referenceType, UUID referenceId, String description) throws SystemGlobalException {
        log.info("Uploading document: type={}, refType={}, refId={}", documentType, referenceType, referenceId);

        String filePath = fileStorageService.storeMultipartFile(file, "documents");

        DocumentEntity entity = new DocumentEntity();
        entity.setDocumentNo("DOC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        entity.setDocumentType(documentType);
        entity.setStatus(DocumentStatus.UPLOADED.name());
        entity.setReferenceType(referenceType);
        entity.setReferenceId(referenceId);
        entity.setUploadedAt(LocalDateTime.now());
        entity.setFilePath(filePath);
        entity.setFileName(file.getOriginalFilename());
        entity.setMimeType(file.getContentType());
        entity.setFileSize(file.getSize());
        entity.setDescription(description);

        entity = documentRepository.save(entity);

        saveDocumentHistory(entity.getId(), "UPLOAD", "Document uploaded: " + file.getOriginalFilename());

        return toResponseDTO(documentMapper.toDomain(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentResponseDTO getDocument(UUID id) throws SystemGlobalException {
        DocumentEntity entity = documentRepository.findById(id)
                .orElseThrow(() -> new SystemGlobalException("Document not found with id: " + id, null));
        return toResponseDTO(documentMapper.toDomain(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentResponseDTO getDocumentByReference(String referenceType, UUID referenceId) throws SystemGlobalException {
        List<DocumentEntity> docs = documentRepository.findByReference(referenceType, referenceId);
        if (docs.isEmpty()) {
            throw new SystemGlobalException("No document found for reference: " + referenceType + "/" + referenceId, null);
        }
        return toResponseDTO(documentMapper.toDomain(docs.get(0)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DocumentResponseDTO> searchDocuments(DocumentSearchRequestDTO request, Pageable pageable) throws SystemGlobalException {
        Page<DocumentEntity> entities = documentRepository.searchDocuments(
                request.getDocumentType(), request.getStatus(), request.getDocumentSubType(), pageable);
        return entities.map(e -> toResponseDTO(documentMapper.toDomain(e)));
    }

    @Override
    @Transactional(readOnly = true)
    public Resource downloadDocument(UUID id) throws SystemGlobalException {
        DocumentEntity entity = documentRepository.findById(id)
                .orElseThrow(() -> new SystemGlobalException("Document not found with id: " + id, null));
        if (entity.getFilePath() == null) {
            throw new SystemGlobalException("Document has no file: " + id, null);
        }
        return fileStorageService.loadFile(entity.getFilePath());
    }

    @Override
    @Transactional(readOnly = true)
    public String getDocumentFileName(UUID id) throws SystemGlobalException {
        DocumentEntity entity = documentRepository.findById(id)
                .orElseThrow(() -> new SystemGlobalException("Document not found with id: " + id, null));
        return entity.getFileName();
    }

    @Override
    @Transactional(readOnly = true)
    public String getDocumentMimeType(UUID id) throws SystemGlobalException {
        DocumentEntity entity = documentRepository.findById(id)
                .orElseThrow(() -> new SystemGlobalException("Document not found with id: " + id, null));
        return entity.getMimeType();
    }

    @Override
    @Transactional
    public void deleteDocument(UUID id) throws SystemGlobalException {
        DocumentEntity entity = documentRepository.findById(id)
                .orElseThrow(() -> new SystemGlobalException("Document not found with id: " + id, null));
        if (entity.getFilePath() != null) {
            fileStorageService.deleteFile(entity.getFilePath());
        }
        documentRepository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentHistoryDTO> getDocumentHistory(UUID id) throws SystemGlobalException {
        return documentHistoryRepository.findByDocumentIdOrderByPerformedAtDesc(id)
                .stream().map(this::toHistoryDTO).collect(Collectors.toList());
    }

    private void saveDocumentHistory(UUID documentId, String action, String details) {
        DocumentHistoryEntity history = new DocumentHistoryEntity();
        history.setDocumentId(documentId);
        history.setAction(action);
        history.setDetails(details);
        history.setPerformedBy(UUID.fromString("00000000-0000-0000-0000-000000000000"));
        history.setPerformedAt(LocalDateTime.now());
        documentHistoryRepository.save(history);
    }

    private DocumentResponseDTO toResponseDTO(TDocument domain) {
        DocumentResponseDTO dto = new DocumentResponseDTO();
        dto.setId(domain.getId());
        dto.setDocumentNo(domain.getDocumentNo());
        dto.setDocumentType(domain.getDocumentType() != null ? domain.getDocumentType().name() : null);
        dto.setDocumentSubType(domain.getDocumentSubType());
        dto.setStatus(domain.getStatus() != null ? domain.getStatus().name() : null);
        dto.setReferenceType(domain.getReferenceType());
        dto.setReferenceId(domain.getReferenceId());
        dto.setGeneratedAt(domain.getGeneratedAt());
        dto.setUploadedAt(domain.getUploadedAt());
        dto.setFilePath(domain.getFilePath());
        dto.setFileName(domain.getFileName());
        dto.setMimeType(domain.getMimeType());
        dto.setFileSize(domain.getFileSize());
        dto.setDescription(domain.getDescription());
        return dto;
    }

    private DocumentHistoryDTO toHistoryDTO(DocumentHistoryEntity entity) {
        DocumentHistoryDTO dto = new DocumentHistoryDTO();
        dto.setId(entity.getId());
        dto.setDocumentId(entity.getDocumentId());
        dto.setAction(entity.getAction());
        dto.setDetails(entity.getDetails());
        dto.setPerformedBy(entity.getPerformedBy() != null ? entity.getPerformedBy().toString() : null);
        dto.setPerformedAt(entity.getPerformedAt());
        return dto;
    }
}
