package com.icmon.module.payment.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icmon.module.payment.application.usecase.*;
import com.icmon.module.payment.presentation.controller.PaymentController;
import com.icmon.module.payment.presentation.dto.request.PaymentRecordRequestDTO;
import com.icmon.module.payment.presentation.dto.response.PaymentResponseDTO;
import com.icmon.module.payment.presentation.validator.PaymentValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RecordPaymentUseCase recordPaymentUseCase;
    @Mock
    private GetPaymentUseCase getPaymentUseCase;
    @Mock
    private SearchPaymentsUseCase searchPaymentsUseCase;
    @Mock
    private ProcessRefundUseCase processRefundUseCase;
    @Mock
    private CancelPaymentUseCase cancelPaymentUseCase;
    @Mock
    private GetOutstandingBalanceUseCase getOutstandingBalanceUseCase;

    private PaymentValidator paymentValidator;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        paymentValidator = new PaymentValidator();
        mockMvc = MockMvcBuilders.standaloneSetup(new PaymentController(
                recordPaymentUseCase, getPaymentUseCase, searchPaymentsUseCase,
                processRefundUseCase, cancelPaymentUseCase,
                getOutstandingBalanceUseCase, paymentValidator
        )).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldRecordPayment() throws Exception {
        PaymentRecordRequestDTO request = new PaymentRecordRequestDTO();
        request.setInvoiceId(UUID.randomUUID());
        request.setPaymentMethodId(UUID.randomUUID());
        request.setAmount(new BigDecimal("500"));
        request.setAmountReceived(new BigDecimal("500"));
        request.setReceivedBy(UUID.randomUUID());

        PaymentResponseDTO response = new PaymentResponseDTO();
        response.setId(UUID.randomUUID());
        response.setPaymentNo("PAY-001");
        response.setStatus("COMPLETED");

        when(recordPaymentUseCase.execute(any(PaymentRecordRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.paymentNo").value("PAY-001"));
    }

    @Test
    void shouldGetPaymentById() throws Exception {
        UUID id = UUID.randomUUID();
        PaymentResponseDTO response = new PaymentResponseDTO();
        response.setId(id);
        response.setPaymentNo("PAY-001");

        when(getPaymentUseCase.execute(id)).thenReturn(response);

        mockMvc.perform(get("/api/v1/payments/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentNo").value("PAY-001"));
    }

    @Test
    void shouldDeletePayment() throws Exception {
        UUID id = UUID.randomUUID();
        PaymentResponseDTO response = new PaymentResponseDTO();
        response.setId(id);
        response.setStatus("CANCELLED");

        when(cancelPaymentUseCase.execute(eq(id), anyString())).thenReturn(response);

        mockMvc.perform(delete("/api/v1/payments/{id}", id)
                        .param("reason", "Test cancel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CANCELLED"));
    }
}
