package com.icmon.module.purchase.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.purchase.application.usecase.*;
import com.icmon.module.purchase.domain.enums.PurchaseOrderStatus;
import com.icmon.module.purchase.presentation.controller.PurchaseOrderController;
import com.icmon.module.purchase.presentation.dto.request.PurchaseOrderCreateRequestDTO;
import com.icmon.module.purchase.presentation.dto.response.PurchaseOrderResponseDTO;
import com.icmon.module.purchase.presentation.validator.CreatePurchaseOrderValidator;
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
class PurchaseOrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CreatePurchaseOrderUseCase createPurchaseOrderUseCase;
    @Mock
    private GetPurchaseOrderUseCase getPurchaseOrderUseCase;
    @Mock
    private ListPurchaseOrdersUseCase listPurchaseOrdersUseCase;
    @Mock
    private UpdatePurchaseOrderUseCase updatePurchaseOrderUseCase;
    @Mock
    private DeletePurchaseOrderUseCase deletePurchaseOrderUseCase;
    @Mock
    private SendPurchaseOrderUseCase sendPurchaseOrderUseCase;
    @Mock
    private ReceivePurchaseOrderUseCase receivePurchaseOrderUseCase;
    @Mock
    private CancelPurchaseOrderUseCase cancelPurchaseOrderUseCase;
    @Mock
    private GetPurchaseOrderByPoNoUseCase getPurchaseOrderByPoNoUseCase;
    @Mock
    private GetPurchaseOrderHistoryUseCase getPurchaseOrderHistoryUseCase;

    private CreatePurchaseOrderValidator createValidator;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        createValidator = new CreatePurchaseOrderValidator();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        PurchaseOrderController controller = new PurchaseOrderController(
                createPurchaseOrderUseCase, getPurchaseOrderUseCase, listPurchaseOrdersUseCase,
                updatePurchaseOrderUseCase, deletePurchaseOrderUseCase, sendPurchaseOrderUseCase,
                receivePurchaseOrderUseCase, cancelPurchaseOrderUseCase,
                getPurchaseOrderByPoNoUseCase, getPurchaseOrderHistoryUseCase, createValidator
        );

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new TestExceptionHandler())
                .build();
    }

    @Test
    void testCreatePurchaseOrderSuccess() throws Exception {
        PurchaseOrderCreateRequestDTO request = new PurchaseOrderCreateRequestDTO();
        request.setSupplierId(UUID.randomUUID());

        PurchaseOrderResponseDTO response = PurchaseOrderResponseDTO.builder()
                .id(UUID.randomUUID())
                .poNo("PO-2026-0001")
                .status(PurchaseOrderStatus.DRAFT.name())
                .createdAt(LocalDateTime.now())
                .build();

        when(createPurchaseOrderUseCase.execute(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/purchase-orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.poNo").value("PO-2026-0001"))
                .andExpect(jsonPath("$.status").value("DRAFT"));
    }

    @Test
    void testGetPurchaseOrderSuccess() throws Exception {
        UUID id = UUID.randomUUID();
        PurchaseOrderResponseDTO response = PurchaseOrderResponseDTO.builder()
                .id(id)
                .poNo("PO-2026-0001")
                .status(PurchaseOrderStatus.DRAFT.name())
                .build();

        when(getPurchaseOrderUseCase.execute(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/purchase-orders/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.poNo").value("PO-2026-0001"));
    }

    @Test
    void testDeletePurchaseOrderSuccess() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(delete("/api/v1/purchase-orders/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCreatePurchaseOrderValidationFails() throws Exception {
        PurchaseOrderCreateRequestDTO request = new PurchaseOrderCreateRequestDTO();

        mockMvc.perform(post("/api/v1/purchase-orders")
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
