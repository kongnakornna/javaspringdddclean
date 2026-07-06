package com.icmon.module.inventory.application.impl;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.domain.MPartMaster;
import com.icmon.module.inventory.infrastructure.cache.PartCacheService;
import com.icmon.module.inventory.infrastructure.entity.InventoryEntity;
import com.icmon.module.inventory.infrastructure.entity.InventoryLayerEntity;
import com.icmon.module.inventory.infrastructure.entity.PartMasterEntity;
import com.icmon.module.inventory.infrastructure.mapper.InventoryLayerMapper;
import com.icmon.module.inventory.infrastructure.mapper.InventoryMapper;
import com.icmon.module.inventory.infrastructure.mapper.PartMasterMapper;
import com.icmon.module.inventory.infrastructure.repository.InventoryLayerRepository;
import com.icmon.module.inventory.infrastructure.repository.InventoryRepository;
import com.icmon.module.inventory.infrastructure.repository.PartMasterRepository;
import com.icmon.module.inventory.presentation.dto.request.InventoryIssueRequestDTO;
import com.icmon.module.inventory.presentation.dto.request.InventoryReceiveRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.InventoryResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Inventory Service Tests")
class InventoryServiceImplTest {

    @Mock private PartMasterRepository partMasterRepository;
    @Mock private InventoryRepository inventoryRepository;
    @Mock private InventoryLayerRepository inventoryLayerRepository;
    @Mock private PartCacheService partCacheService;
    @Mock private PartMasterMapper partMasterMapper;
    @Mock private InventoryMapper inventoryMapper;
    @Mock private InventoryLayerMapper inventoryLayerMapper;
    @InjectMocks private InventoryServiceImpl inventoryService;

    @Test
    @DisplayName("should receive goods successfully")
    void shouldReceiveGoods() throws SystemGlobalException {
        UUID partId = UUID.randomUUID();
        InventoryReceiveRequestDTO request = new InventoryReceiveRequestDTO();
        request.setPartId(partId);
        request.setQuantity(10);
        request.setUnitCost(new BigDecimal("100.00"));

        PartMasterEntity entity = new PartMasterEntity();
        entity.setId(partId);
        entity.setPartCode("TEST-001");
        entity.setStockQuantity(5);

        MPartMaster domain = new MPartMaster();
        domain.setId(partId);
        domain.setPartCode("TEST-001");
        domain.setStockQuantity(5);

        when(partMasterRepository.findById(partId)).thenReturn(Optional.of(entity));
        when(partMasterMapper.toDomain(entity)).thenReturn(domain);
        when(partMasterMapper.toEntity(domain)).thenReturn(entity);
        when(inventoryRepository.save(any())).thenReturn(new InventoryEntity());

        InventoryResponseDTO result = inventoryService.receiveGoods(request);

        assertThat(result).isNotNull();
        verify(inventoryRepository, times(1)).save(any());
        verify(inventoryLayerRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("should throw exception when issuing with insufficient stock")
    void shouldThrowExceptionWhenIssuingInsufficientStock() {
        UUID partId = UUID.randomUUID();
        InventoryIssueRequestDTO request = new InventoryIssueRequestDTO();
        request.setPartId(partId);
        request.setQuantity(100);

        PartMasterEntity entity = new PartMasterEntity();
        entity.setId(partId);
        entity.setStockQuantity(50);

        MPartMaster domain = new MPartMaster();
        domain.setId(partId);
        domain.setStockQuantity(50);

        when(partMasterRepository.findById(partId)).thenReturn(Optional.of(entity));
        when(partMasterMapper.toDomain(entity)).thenReturn(domain);

        assertThatThrownBy(() -> inventoryService.issueGoods(request))
                .isInstanceOf(SystemGlobalException.class)
                .hasMessageContaining("Insufficient stock");
    }
}
