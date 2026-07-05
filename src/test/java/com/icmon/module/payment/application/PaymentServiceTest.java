package com.icmon.module.payment.application;

import com.icmon.module.payment.application.impl.PaymentServiceImpl;
import com.icmon.module.payment.infrastructure.cache.PaymentCacheService;
import com.icmon.module.payment.infrastructure.entity.PaymentEntity;
import com.icmon.module.payment.infrastructure.mapper.PaymentMapper;
import com.icmon.module.payment.infrastructure.repository.PaymentHistoryRepository;
import com.icmon.module.payment.infrastructure.repository.PaymentMethodRepository;
import com.icmon.module.payment.infrastructure.repository.PaymentRepository;
import com.icmon.module.payment.infrastructure.repository.ReceiptRepository;
import com.icmon.module.payment.presentation.dto.request.PaymentRecordRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private ReceiptRepository receiptRepository;
    @Mock
    private PaymentMethodRepository paymentMethodRepository;
    @Mock
    private PaymentHistoryRepository paymentHistoryRepository;
    @Mock
    private PaymentMapper paymentMapper;
    @Mock
    private PaymentCacheService paymentCacheService;

    private PaymentServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new PaymentServiceImpl(paymentRepository, receiptRepository,
                paymentMethodRepository, paymentHistoryRepository,
                paymentMapper, paymentCacheService);
    }

    @Test
    void shouldRecordPayment() throws Exception {
        PaymentRecordRequestDTO request = new PaymentRecordRequestDTO();
        request.setInvoiceId(UUID.randomUUID());
        request.setPaymentMethodId(UUID.randomUUID());
        request.setAmount(new BigDecimal("500"));
        request.setAmountReceived(new BigDecimal("500"));
        request.setReceivedBy(UUID.randomUUID());

        when(paymentMapper.toEntity(any())).thenReturn(new PaymentEntity());
        when(paymentRepository.save(any(PaymentEntity.class))).thenAnswer(invocation -> {
            PaymentEntity entity = invocation.getArgument(0);
            entity.setId(UUID.randomUUID());
            entity.setPaymentNo("PAY-TEST");
            return entity;
        });

        assertDoesNotThrow(() -> service.recordPayment(request));
        verify(paymentRepository).save(any(PaymentEntity.class));
        verify(paymentCacheService).savePayment(any(PaymentEntity.class));
    }

    @Test
    void shouldThrowWhenPaymentNotFound() {
        UUID id = UUID.randomUUID();
        when(paymentRepository.findById(id)).thenReturn(java.util.Optional.empty());
        assertThrows(Exception.class, () -> service.getPayment(id));
    }
}
