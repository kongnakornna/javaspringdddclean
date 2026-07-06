package com.icmon.module.weborder.application.impl;

import com.icmon.module.weborder.infrastructure.entity.WebOrderEntity;
import com.icmon.module.weborder.infrastructure.repository.OrderRepository;
import com.icmon.module.weborder.presentation.dto.response.OrderTrackingResponseDTO;
import com.icmon.exception.SystemGlobalException;
import com.icmon.exception.models.FailedRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderTrackingServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    private OrderTrackingServiceImpl trackingService;

    @BeforeEach
    void setUp() {
        trackingService = new OrderTrackingServiceImpl(orderRepository);
    }

    @Test
    void testGetTracking() throws SystemGlobalException {
        UUID id = UUID.randomUUID();
        WebOrderEntity entity = new WebOrderEntity();
        entity.setId(id);
        entity.setOrderNo("WO-2026-0001");

        when(orderRepository.findById(id)).thenReturn(Optional.of(entity));

        OrderTrackingResponseDTO result = trackingService.getTracking(id);
        assertEquals("WO-2026-0001", result.getOrderNo());
    }

    @Test
    void testGetTrackingNotFound() {
        UUID id = UUID.randomUUID();
        when(orderRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(FailedRequestException.class, () -> trackingService.getTracking(id));
    }

    @Test
    void testUpdateTrackingNumber() throws SystemGlobalException {
        UUID id = UUID.randomUUID();
        WebOrderEntity entity = new WebOrderEntity();
        entity.setId(id);

        when(orderRepository.findById(id)).thenReturn(Optional.of(entity));

        trackingService.updateTrackingNumber(id, "TH1234567890", "Kerry Express");
        assertEquals("TH1234567890", entity.getTrackingNumber());
        assertEquals("Kerry Express", entity.getCourier());
        verify(orderRepository).save(entity);
    }
}
