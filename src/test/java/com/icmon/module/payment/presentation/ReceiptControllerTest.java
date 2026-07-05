package com.icmon.module.payment.presentation;

import com.icmon.module.payment.application.usecase.GenerateReceiptPDFUseCase;
import com.icmon.module.payment.application.usecase.GetReceiptUseCase;
import com.icmon.module.payment.presentation.controller.ReceiptController;
import com.icmon.module.payment.presentation.dto.response.ReceiptResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ReceiptControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GetReceiptUseCase getReceiptUseCase;
    @Mock
    private GenerateReceiptPDFUseCase generateReceiptPDFUseCase;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ReceiptController(
                getReceiptUseCase, generateReceiptPDFUseCase
        )).build();
    }

    @Test
    void shouldGetReceipt() throws Exception {
        UUID id = UUID.randomUUID();
        ReceiptResponseDTO response = new ReceiptResponseDTO();
        response.setId(id);
        response.setReceiptNo("RCT-001");
        response.setStatus("ISSUED");

        when(getReceiptUseCase.execute(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/receipts/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.receiptNo").value("RCT-001"));
    }
}
