package com.icmon.module.payment.application;

import com.icmon.module.payment.application.impl.ReceiptServiceImpl;
import com.icmon.module.payment.infrastructure.cache.ReceiptCacheService;
import com.icmon.module.payment.infrastructure.entity.ReceiptEntity;
import com.icmon.module.payment.infrastructure.report.ReceiptReportGenerator;
import com.icmon.module.payment.infrastructure.repository.ReceiptRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceiptServiceTest {

    @Mock
    private ReceiptRepository receiptRepository;
    @Mock
    private ReceiptCacheService receiptCacheService;
    @Mock
    private ReceiptReportGenerator receiptReportGenerator;

    private ReceiptServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new ReceiptServiceImpl(receiptRepository, receiptCacheService, receiptReportGenerator);
    }

    @Test
    void shouldGetReceipt() throws Exception {
        UUID id = UUID.randomUUID();
        ReceiptEntity entity = new ReceiptEntity();
        entity.setId(id);
        entity.setReceiptNo("RCT-001");
        when(receiptRepository.findById(id)).thenReturn(java.util.Optional.of(entity));
        assertDoesNotThrow(() -> service.getReceipt(id));
    }

    @Test
    void shouldThrowWhenReceiptNotFound() {
        UUID id = UUID.randomUUID();
        when(receiptRepository.findById(id)).thenReturn(java.util.Optional.empty());
        assertThrows(Exception.class, () -> service.getReceipt(id));
    }

    @Test
    void shouldCancelReceipt() throws Exception {
        UUID id = UUID.randomUUID();
        ReceiptEntity entity = new ReceiptEntity();
        entity.setId(id);
        entity.setStatus("ISSUED");
        when(receiptRepository.findById(id)).thenReturn(java.util.Optional.of(entity));
        when(receiptRepository.save(any(ReceiptEntity.class))).thenReturn(entity);
        assertDoesNotThrow(() -> service.cancelReceipt(id, "Test cancel"));
        verify(receiptRepository).save(any(ReceiptEntity.class));
        verify(receiptCacheService).saveReceipt(any(ReceiptEntity.class));
    }

    @Test
    void shouldGenerateReceiptPDF() throws Exception {
        UUID id = UUID.randomUUID();
        ReceiptEntity entity = new ReceiptEntity();
        entity.setId(id);
        entity.setReceiptNo("RCT-001");
        when(receiptRepository.findById(id)).thenReturn(java.util.Optional.of(entity));
        when(receiptReportGenerator.generateReceiptPdf(entity)).thenReturn("PDF".getBytes());
        byte[] pdf = service.generateReceiptPDF(id);
        assertNotNull(pdf);
        assertTrue(pdf.length > 0);
    }
}
