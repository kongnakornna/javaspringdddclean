package com.icmon.module.document.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icmon.module.document.application.interfaces.DocumentService;
import com.icmon.module.document.application.usecase.*;
import com.icmon.module.document.presentation.dto.request.DocumentGenerateRequestDTO;
import com.icmon.module.document.presentation.dto.request.DocumentSearchRequestDTO;
import com.icmon.module.document.presentation.dto.response.DocumentResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class DocumentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GenerateDocumentUseCase generateDocumentUseCase;
    @Mock
    private UploadDocumentUseCase uploadDocumentUseCase;
    @Mock
    private GetDocumentUseCase getDocumentUseCase;
    @Mock
    private SearchDocumentUseCase searchDocumentUseCase;
    @Mock
    private GetDocumentHistoryUseCase getDocumentHistoryUseCase;
    @Mock
    private DeleteDocumentUseCase deleteDocumentUseCase;
    @Mock
    private DocumentService documentService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new DocumentController(
                generateDocumentUseCase, uploadDocumentUseCase, getDocumentUseCase,
                searchDocumentUseCase, getDocumentHistoryUseCase, deleteDocumentUseCase,
                documentService
        )).setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldGenerateDocument() throws Exception {
        DocumentGenerateRequestDTO request = new DocumentGenerateRequestDTO();
        request.setTemplateCode("TMPL-001");
        request.setDocumentType("INVOICE");

        DocumentResponseDTO response = new DocumentResponseDTO();
        response.setId(UUID.randomUUID());
        response.setDocumentNo("DOC-001");

        when(generateDocumentUseCase.execute(any(DocumentGenerateRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/documents/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.documentNo").value("DOC-001"));
    }

    @Test
    void shouldGetDocumentById() throws Exception {
        UUID id = UUID.randomUUID();
        DocumentResponseDTO response = new DocumentResponseDTO();
        response.setId(id);
        response.setDocumentNo("DOC-001");

        when(getDocumentUseCase.byId(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/documents/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.documentNo").value("DOC-001"));
    }

    @Test
    void shouldSearchDocuments() throws Exception {
        DocumentResponseDTO response = new DocumentResponseDTO();
        response.setId(UUID.randomUUID());
        response.setDocumentNo("DOC-001");

        Page<DocumentResponseDTO> page = new PageImpl<>(List.of(response), PageRequest.of(0, 20), 1);

        when(searchDocumentUseCase.execute(any(DocumentSearchRequestDTO.class), any()))
                .thenReturn(page);

        mockMvc.perform(get("/api/v1/documents")
                        .param("documentType", "INVOICE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].documentNo").value("DOC-001"));
    }

    @Test
    void shouldDownloadDocument() throws Exception {
        UUID id = UUID.randomUUID();
        Resource resource = new ByteArrayResource("test content".getBytes());

        when(documentService.downloadDocument(id)).thenReturn(resource);
        when(documentService.getDocumentFileName(id)).thenReturn("test.pdf");
        when(documentService.getDocumentMimeType(id)).thenReturn("application/pdf");

        mockMvc.perform(get("/api/v1/documents/{id}/download", id))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=\"test.pdf\""))
                .andExpect(header().string("Content-Type", "application/pdf"));
    }

    @Test
    void shouldDeleteDocument() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(delete("/api/v1/documents/{id}", id))
                .andExpect(status().isNoContent());
    }
}
