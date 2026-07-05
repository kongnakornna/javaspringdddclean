package com.icmon.module.purchase.application;

import com.icmon.exception.SystemGlobalException;
import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.purchase.application.impl.PurchaseOrderServiceImpl;
import com.icmon.module.purchase.domain.enums.PurchaseOrderStatus;
import com.icmon.module.purchase.infrastructure.cache.PurchaseOrderCacheService;
import com.icmon.module.purchase.infrastructure.email.PurchaseOrderEmailService;
import com.icmon.module.purchase.infrastructure.entity.PurchaseOrderHeaderEntity;
import com.icmon.module.purchase.infrastructure.entity.PurchaseOrderStatusHistoryEntity;
import com.icmon.module.purchase.infrastructure.mapper.PurchaseOrderHeaderMapper;
import com.icmon.module.purchase.infrastructure.repository.PurchaseOrderDetailRepository;
import com.icmon.module.purchase.infrastructure.repository.PurchaseOrderHeaderRepository;
import com.icmon.module.purchase.infrastructure.repository.PurchaseOrderStatusHistoryRepository;
import com.icmon.module.purchase.infrastructure.report.PurchaseOrderReportService;
import com.icmon.module.purchase.presentation.dto.request.PurchaseOrderCreateRequestDTO;
import com.icmon.module.purchase.presentation.dto.response.PurchaseOrderResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseOrderServiceImplTest {

    @Mock
    private PurchaseOrderHeaderRepository poHeaderRepository;
    @Mock
    private PurchaseOrderStatusHistoryRepository statusHistoryRepository;
    @Mock
    private PurchaseOrderDetailRepository detailRepository;
    @Mock
    private PurchaseOrderHeaderMapper headerMapper;
    @Mock
    private PurchaseOrderCacheService cacheService;
    @Mock
    private PurchaseOrderEmailService emailService;
    @Mock
    private PurchaseOrderReportService reportService;

    private PurchaseOrderServiceImpl purchaseOrderService;
    private UUID testUserId;
    private UUID testWhitelabelId;

    @BeforeEach
    void setUp() {
        purchaseOrderService = new PurchaseOrderServiceImpl(
                poHeaderRepository, statusHistoryRepository, detailRepository,
                headerMapper, cacheService, emailService, reportService
        );
        testUserId = UUID.randomUUID();
        testWhitelabelId = UUID.randomUUID();
        MDC.put("userId", testUserId.toString());
        MDC.put("whitelabelId", testWhitelabelId.toString());
        MDC.put("requestId", UUID.randomUUID().toString());
    }

    @Test
    void testCreatePurchaseOrderSuccess() throws SystemGlobalException {
        PurchaseOrderCreateRequestDTO request = new PurchaseOrderCreateRequestDTO();
        UUID supplierId = UUID.randomUUID();
        request.setSupplierId(supplierId);

        PurchaseOrderHeaderEntity savedEntity = new PurchaseOrderHeaderEntity();
        UUID entityId = UUID.randomUUID();
        savedEntity.setId(entityId);
        savedEntity.setPoNo("PO-2026-0001");
        savedEntity.setSupplierId(supplierId);
        savedEntity.setStatus(PurchaseOrderStatus.DRAFT.name());
        savedEntity.setSubtotal(BigDecimal.ZERO);
        savedEntity.setTotal(BigDecimal.ZERO);

        when(poHeaderRepository.save(any(PurchaseOrderHeaderEntity.class))).thenReturn(savedEntity);

        PurchaseOrderResponseDTO response = purchaseOrderService.createPurchaseOrder(request);

        assertNotNull(response);
        assertEquals("PO-2026-0001", response.getPoNo());
        assertEquals(PurchaseOrderStatus.DRAFT.name(), response.getStatus());
        verify(poHeaderRepository).save(any(PurchaseOrderHeaderEntity.class));
    }

    @Test
    void testGetPurchaseOrderNotFound() {
        UUID id = UUID.randomUUID();
        when(poHeaderRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(FailedRequestException.class, () -> purchaseOrderService.getPurchaseOrder(id));
    }

    @Test
    void testDeletePurchaseOrderSuccess() throws SystemGlobalException {
        UUID id = UUID.randomUUID();
        PurchaseOrderHeaderEntity entity = new PurchaseOrderHeaderEntity();
        entity.setId(id);
        entity.setStatus(PurchaseOrderStatus.DRAFT.name());

        when(poHeaderRepository.findById(id)).thenReturn(Optional.of(entity));

        purchaseOrderService.deletePurchaseOrder(id);

        assertTrue(entity.getDeleted());
        assertNotNull(entity.getDeletedAt());
        verify(poHeaderRepository).save(entity);
    }

    @Test
    void testCancelPurchaseOrderSuccess() throws SystemGlobalException {
        UUID id = UUID.randomUUID();
        PurchaseOrderHeaderEntity entity = new PurchaseOrderHeaderEntity();
        entity.setId(id);
        entity.setStatus(PurchaseOrderStatus.SENT.name());

        when(poHeaderRepository.findById(id)).thenReturn(Optional.of(entity));
        when(poHeaderRepository.save(any(PurchaseOrderHeaderEntity.class))).thenAnswer(i -> i.getArgument(0));

        PurchaseOrderResponseDTO response = purchaseOrderService.cancelPurchaseOrder(id, "Supplier out of stock");

        assertNotNull(response);
        assertEquals(PurchaseOrderStatus.CANCELLED.name(), response.getStatus());
        verify(statusHistoryRepository).save(any(PurchaseOrderStatusHistoryEntity.class));
    }

    @Test
    void testCancelAlreadyReceivedThrows() {
        UUID id = UUID.randomUUID();
        PurchaseOrderHeaderEntity entity = new PurchaseOrderHeaderEntity();
        entity.setId(id);
        entity.setStatus(PurchaseOrderStatus.RECEIVED.name());

        when(poHeaderRepository.findById(id)).thenReturn(Optional.of(entity));

        assertThrows(FailedRequestException.class, () -> purchaseOrderService.cancelPurchaseOrder(id, "reason"));
    }
}
