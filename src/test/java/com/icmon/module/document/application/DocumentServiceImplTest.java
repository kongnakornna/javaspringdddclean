package com.icmon.module.document.application;

import com.icmon.module.document.application.interfaces.DocumentStorageService;
import com.icmon.module.document.application.interfaces.ReportGenerationService;
import com.icmon.module.document.application.service.DocumentServiceImpl;
import com.icmon.module.document.infrastructure.entity.DocumentEntity;
import com.icmon.module.document.infrastructure.entity.DocumentHistoryEntity;
import com.icmon.module.document.infrastructure.mapper.DocumentMapper;
import com.icmon.module.document.infrastructure.repository.DocumentHistoryRepository;
import com.icmon.module.document.infrastructure.repository.DocumentRepository;
import com.icmon.module.document.infrastructure.storage.FileStorageService;
import com.icmon.module.document.presentation.dto.request.DocumentGenerateRequestDTO;
import com.icmon.module.document.presentation.dto.response.DocumentResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentServiceImplTest {

    @Mock
    private DocumentRepository documentRepository;
    @Mock
    private DocumentHistoryRepository documentHistoryRepository;
    @Mock
    private DocumentMapper documentMapper;
    @Mock
    private ReportGenerationService reportGenerationService;
    @Mock
    private FileStorageService fileStorageService;
    @Mock
    private DocumentStorageService documentStorageService;

    private DocumentServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new DocumentServiceImpl(documentRepository, documentHistoryRepository,
                documentMapper, reportGenerationService, fileStorageService, documentStorageService);
    }

    @Test
    void shouldGenerateDocument() throws Exception {
        DocumentGenerateRequestDTO request = new DocumentGenerateRequestDTO();
        request.setTemplateCode("TMPL-001");
        request.setDocumentType("INVOICE");
        request.setParameters("{}");

        DocumentResponseDTO expected = new DocumentResponseDTO();
        expected.setId(UUID.randomUUID());
        expected.setDocumentNo("DOC-TEST");

        when(reportGenerationService.generatePDF(anyString(), anyString())).thenReturn(new byte[]{});
        when(documentStorageService.storeGeneratedDocument(any(), any(), anyString(), anyString()))
                .thenReturn(expected);

        DocumentResponseDTO result = service.generateDocument(request);

        assertNotNull(result);
        assertEquals("DOC-TEST", result.getDocumentNo());
        verify(reportGenerationService).generatePDF(eq("TMPL-001"), eq("{}"));
    }

    @Test
    void shouldThrowWhenDocumentNotFound() {
        UUID id = UUID.randomUUID();
        when(documentRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> service.getDocument(id));
    }

    @Test
    void shouldReturnDocumentById() throws Exception {
        UUID id = UUID.randomUUID();
        DocumentEntity entity = new DocumentEntity();
        entity.setId(id);
        entity.setDocumentNo("DOC-001");

        when(documentRepository.findById(id)).thenReturn(Optional.of(entity));
        when(documentMapper.toDomain(entity)).thenReturn(null);
        when(documentMapper.toDomain(entity)).thenReturn(new com.icmon.module.document.domain.TDocument() {{ setDocumentNo("DOC-001"); }});

        DocumentResponseDTO result = service.getDocument(id);
        assertNotNull(result);
    }
}
