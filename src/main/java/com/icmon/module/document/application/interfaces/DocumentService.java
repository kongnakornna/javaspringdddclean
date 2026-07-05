package com.icmon.module.document.application.interfaces;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.document.presentation.dto.request.DocumentGenerateRequestDTO;
import com.icmon.module.document.presentation.dto.request.DocumentSearchRequestDTO;
import com.icmon.module.document.presentation.dto.response.DocumentHistoryDTO;
import com.icmon.module.document.presentation.dto.response.DocumentResponseDTO;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface DocumentService {
    DocumentResponseDTO generateDocument(DocumentGenerateRequestDTO request) throws SystemGlobalException;
    DocumentResponseDTO uploadDocument(MultipartFile file, String documentType, String referenceType, UUID referenceId, String description) throws SystemGlobalException;
    DocumentResponseDTO getDocument(UUID id) throws SystemGlobalException;
    DocumentResponseDTO getDocumentByReference(String referenceType, UUID referenceId) throws SystemGlobalException;
    Page<DocumentResponseDTO> searchDocuments(DocumentSearchRequestDTO request, Pageable pageable) throws SystemGlobalException;
    Resource downloadDocument(UUID id) throws SystemGlobalException;
    String getDocumentFileName(UUID id) throws SystemGlobalException;
    String getDocumentMimeType(UUID id) throws SystemGlobalException;
    void deleteDocument(UUID id) throws SystemGlobalException;
    List<DocumentHistoryDTO> getDocumentHistory(UUID id) throws SystemGlobalException;
}
