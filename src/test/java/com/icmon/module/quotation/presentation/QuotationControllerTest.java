package com.icmon.module.quotation.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.icmon.module.quotation.application.usecase.*;
import com.icmon.module.quotation.domain.enums.QuotationStatus;
import com.icmon.module.quotation.presentation.controller.QuotationController;
import com.icmon.module.quotation.presentation.dto.request.QuotationCreateRequestDTO;
import com.icmon.module.quotation.presentation.dto.response.QuotationDetailResponseDTO;
import com.icmon.module.quotation.presentation.dto.response.QuotationResponseDTO;
import com.icmon.exception.SystemGlobalException;
import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.quotation.presentation.validator.QuotationValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class QuotationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CreateQuotationUseCase createQuotationUseCase;
    @Mock
    private UpdateQuotationUseCase updateQuotationUseCase;
    @Mock
    private GetQuotationUseCase getQuotationUseCase;
    @Mock
    private GetQuotationByJobIdUseCase getQuotationByJobIdUseCase;
    @Mock
    private ListQuotationsUseCase listQuotationsUseCase;
    @Mock
    private DeleteQuotationUseCase deleteQuotationUseCase;
    @Mock
    private ApproveQuotationUseCase approveQuotationUseCase;
    @Mock
    private RejectQuotationUseCase rejectQuotationUseCase;
    @Mock
    private GenerateQuotationPDFUseCase generateQuotationPDFUseCase;
    @Mock
    private GetQuotationHistoryUseCase getQuotationHistoryUseCase;

    private QuotationValidator validator;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        validator = new QuotationValidator();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        QuotationController controller = new QuotationController(
                createQuotationUseCase, updateQuotationUseCase, getQuotationUseCase,
                getQuotationByJobIdUseCase, listQuotationsUseCase, deleteQuotationUseCase,
                approveQuotationUseCase, rejectQuotationUseCase, generateQuotationPDFUseCase,
                getQuotationHistoryUseCase, validator
        );

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new TestExceptionHandler())
                .build();
    }

    @Test
    void testCreateQuotationSuccess() throws Exception {
        QuotationCreateRequestDTO request = new QuotationCreateRequestDTO();
        request.setJobId(UUID.randomUUID());
        request.setCustomerId(UUID.randomUUID());

        QuotationResponseDTO response = QuotationResponseDTO.builder()
                .id(UUID.randomUUID())
                .quotationNo("QT-2026-0001")
                .status(QuotationStatus.DRAFT)
                .createdAt(LocalDateTime.now())
                .build();

        when(createQuotationUseCase.execute(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/quotations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.quotationNo").value("QT-2026-0001"))
                .andExpect(jsonPath("$.status").value("DRAFT"));
    }

    @Test
    void testGetQuotationSuccess() throws Exception {
        UUID id = UUID.randomUUID();
        QuotationDetailResponseDTO response = QuotationDetailResponseDTO.builder()
                .id(id)
                .quotationNo("QT-2026-0001")
                .status(QuotationStatus.DRAFT)
                .build();

        when(getQuotationUseCase.execute(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/quotations/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quotationNo").value("QT-2026-0001"));
    }

    @Test
    void testDeleteQuotationSuccess() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(delete("/api/v1/quotations/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCreateQuotationValidationFails() throws Exception {
        QuotationCreateRequestDTO request = new QuotationCreateRequestDTO();

        mockMvc.perform(post("/api/v1/quotations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @RestControllerAdvice
    static class TestExceptionHandler {
        @ExceptionHandler(FailedRequestException.class)
        public ResponseEntity<String> handleFailedRequest(FailedRequestException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
