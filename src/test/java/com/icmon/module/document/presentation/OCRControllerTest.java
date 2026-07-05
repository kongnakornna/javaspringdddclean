package com.icmon.module.document.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icmon.module.document.application.usecase.ProcessOCRUseCase;
import com.icmon.module.document.presentation.dto.request.OCRRequestDTO;
import com.icmon.module.document.presentation.dto.response.OCRResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OCRControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProcessOCRUseCase processOCRUseCase;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new OCRController(processOCRUseCase)).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldProcessOCR() throws Exception {
        OCRRequestDTO request = new OCRRequestDTO();
        request.setImageUrl("https://example.com/invoice.jpg");
        request.setLanguage("tha+eng");
        request.setProvider("TESSERACT");

        OCRResponseDTO response = new OCRResponseDTO();
        response.setProvider("TESSERACT");
        response.setExtractedText("Invoice text");
        response.setConfidenceScore(85.0);
        response.setStatus("COMPLETED");

        when(processOCRUseCase.execute(any(OCRRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/ocr/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.provider").value("TESSERACT"))
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }
}
