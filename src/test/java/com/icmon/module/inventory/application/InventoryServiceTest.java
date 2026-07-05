package com.icmon.module.inventory.application;

import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.inventory.application.impl.InventoryServiceImpl;
import com.icmon.module.inventory.infrastructure.cache.InventoryCacheService;
import com.icmon.module.inventory.infrastructure.cache.PartMasterCacheService;
import com.icmon.module.inventory.infrastructure.entity.InventoryEntity;
import com.icmon.module.inventory.infrastructure.entity.PartMasterEntity;
import com.icmon.module.inventory.infrastructure.repository.InventoryRepository;
import com.icmon.module.inventory.infrastructure.repository.PartMasterRepository;
import com.icmon.module.inventory.presentation.dto.request.InventoryTransactionRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private InventoryRepository repository;
    @Mock
    private PartMasterRepository partMasterRepository;
    @Mock
    private InventoryCacheService cacheService;
    @Mock
    private PartMasterCacheService partCacheService;

    private InventoryServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new InventoryServiceImpl(repository, partMasterRepository, cacheService, partCacheService);
    }

    @Test
    void shouldRecordReceiptTransaction() throws Exception {
        UUID partId = UUID.randomUUID();
        PartMasterEntity part = new PartMasterEntity();
        part.setId(partId);
        part.setStockQuantity(10);

        InventoryTransactionRequestDTO request = new InventoryTransactionRequestDTO();
        request.setPartId(partId);
        request.setTransactionType("RECEIPT");
        request.setQuantity(5);
        request.setPerformedBy(UUID.randomUUID());

        when(partMasterRepository.findById(partId)).thenReturn(java.util.Optional.of(part));
        when(repository.save(any(InventoryEntity.class))).thenAnswer(invocation -> {
            InventoryEntity entity = invocation.getArgument(0);
            entity.setId(UUID.randomUUID());
            return entity;
        });

        assertDoesNotThrow(() -> service.recordTransaction(request));
        assertEquals(15, part.getStockQuantity());
        verify(partMasterRepository).save(part);
        verify(cacheService).cache(any(InventoryEntity.class));
    }

    @Test
    void shouldRecordIssueTransaction() throws Exception {
        UUID partId = UUID.randomUUID();
        PartMasterEntity part = new PartMasterEntity();
        part.setId(partId);
        part.setStockQuantity(10);

        InventoryTransactionRequestDTO request = new InventoryTransactionRequestDTO();
        request.setPartId(partId);
        request.setTransactionType("ISSUE");
        request.setQuantity(3);
        request.setPerformedBy(UUID.randomUUID());

        when(partMasterRepository.findById(partId)).thenReturn(java.util.Optional.of(part));
        when(repository.save(any(InventoryEntity.class))).thenAnswer(invocation -> {
            InventoryEntity entity = invocation.getArgument(0);
            entity.setId(UUID.randomUUID());
            return entity;
        });

        assertDoesNotThrow(() -> service.recordTransaction(request));
        assertEquals(7, part.getStockQuantity());
    }

    @Test
    void shouldThrowWhenInsufficientStock() {
        UUID partId = UUID.randomUUID();
        PartMasterEntity part = new PartMasterEntity();
        part.setId(partId);
        part.setStockQuantity(2);

        InventoryTransactionRequestDTO request = new InventoryTransactionRequestDTO();
        request.setPartId(partId);
        request.setTransactionType("ISSUE");
        request.setQuantity(10);
        request.setPerformedBy(UUID.randomUUID());

        when(partMasterRepository.findById(partId)).thenReturn(java.util.Optional.of(part));

        assertThrows(FailedRequestException.class, () -> service.recordTransaction(request));
    }
}
