package com.icmon.module.weborder.application.impl;

import com.icmon.module.weborder.infrastructure.entity.SalesPriceEntity;
import com.icmon.module.weborder.infrastructure.repository.SalesPriceRepository;
import com.icmon.module.weborder.presentation.dto.response.SalesPriceResponseDTO;
import com.icmon.exception.SystemGlobalException;
import com.icmon.exception.models.FailedRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalesPriceServiceImplTest {

    @Mock
    private SalesPriceRepository salesPriceRepository;

    private SalesPriceServiceImpl salesPriceService;

    @BeforeEach
    void setUp() {
        salesPriceService = new SalesPriceServiceImpl(salesPriceRepository, null);
    }

    @Test
    void testGetPricesByItem() throws SystemGlobalException {
        UUID itemId = UUID.randomUUID();
        SalesPriceEntity entity = new SalesPriceEntity();
        entity.setId(UUID.randomUUID());
        entity.setItemId(itemId);
        entity.setUnitPrice(new BigDecimal("1500.00"));
        entity.setPriceTier("DEFAULT");

        when(salesPriceRepository.findByItemIdAndIsActiveTrue(itemId)).thenReturn(List.of(entity));

        List<SalesPriceResponseDTO> result = salesPriceService.getPricesByItem(itemId);
        assertEquals(1, result.size());
        assertEquals(new BigDecimal("1500.00"), result.get(0).getUnitPrice());
    }

    @Test
    void testDeletePrice() throws SystemGlobalException {
        UUID id = UUID.randomUUID();
        SalesPriceEntity entity = new SalesPriceEntity();
        entity.setId(id);
        entity.setIsActive(true);

        when(salesPriceRepository.findById(id)).thenReturn(Optional.of(entity));

        salesPriceService.deletePrice(id);
        assertFalse(entity.getIsActive());
        verify(salesPriceRepository).save(entity);
    }

    @Test
    void testDeletePriceNotFound() {
        UUID id = UUID.randomUUID();
        when(salesPriceRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(FailedRequestException.class, () -> salesPriceService.deletePrice(id));
    }
}
