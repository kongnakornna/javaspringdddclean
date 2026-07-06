package com.icmon.module.weborder.application.impl;

import com.icmon.module.weborder.domain.enums.OrderStatus;
import com.icmon.module.weborder.infrastructure.entity.WebOrderEntity;
import com.icmon.module.weborder.infrastructure.entity.WebOrderStatusHistoryEntity;
import com.icmon.module.weborder.infrastructure.repository.*;
import com.icmon.module.weborder.presentation.dto.request.OrderCreateRequestDTO;
import com.icmon.module.weborder.presentation.dto.request.OrderStatusUpdateRequestDTO;
import com.icmon.module.weborder.presentation.dto.response.OrderResponseDTO;
import com.icmon.exception.SystemGlobalException;
import com.icmon.exception.models.FailedRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private OrderStatusHistoryRepository statusHistoryRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private CatalogueItemRepository catalogueItemRepository;

    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderServiceImpl(orderRepository, orderItemRepository, statusHistoryRepository,
                cartRepository, cartItemRepository, catalogueItemRepository, null, null);
    }

    @Test
    void testGetOrderNotFound() {
        UUID id = UUID.randomUUID();
        when(orderRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(FailedRequestException.class, () -> orderService.getOrder(id));
    }

    @Test
    void testGetOrder() throws SystemGlobalException {
        UUID id = UUID.randomUUID();
        WebOrderEntity entity = new WebOrderEntity();
        entity.setId(id);
        entity.setOrderNo("WO-2026-0001");
        entity.setStatus(OrderStatus.PENDING);

        when(orderRepository.findById(id)).thenReturn(Optional.of(entity));
        when(orderItemRepository.findByOrderId(id)).thenReturn(List.of());

        var result = orderService.getOrder(id);
        assertEquals("WO-2026-0001", result.getOrderNo());
    }

    @Test
    void testGetOrderByNumber() throws SystemGlobalException {
        WebOrderEntity entity = new WebOrderEntity();
        entity.setId(UUID.randomUUID());
        entity.setOrderNo("WO-2026-0001");

        when(orderRepository.findByOrderNo("WO-2026-0001")).thenReturn(Optional.of(entity));
        when(orderItemRepository.findByOrderId(any())).thenReturn(List.of());

        var result = orderService.getOrderByNumber("WO-2026-0001");
        assertEquals("WO-2026-0001", result.getOrderNo());
    }

    @Test
    void testCancelOrder() throws SystemGlobalException {
        UUID id = UUID.randomUUID();
        WebOrderEntity entity = new WebOrderEntity();
        entity.setId(id);
        entity.setStatus(OrderStatus.PENDING);

        when(orderRepository.findById(id)).thenReturn(Optional.of(entity));
        when(orderRepository.save(any())).thenReturn(entity);

        OrderResponseDTO result = orderService.cancelOrder(id, "ลูกค้าขอยกเลิก");
        assertNotNull(result);
    }

    @Test
    void testCancelOrderThrowsWhenShipped() {
        UUID id = UUID.randomUUID();
        WebOrderEntity entity = new WebOrderEntity();
        entity.setId(id);
        entity.setStatus(OrderStatus.SHIPPED);

        when(orderRepository.findById(id)).thenReturn(Optional.of(entity));
        assertThrows(FailedRequestException.class, () -> orderService.cancelOrder(id, "test"));
    }

    @Test
    void testListOrders() throws SystemGlobalException {
        WebOrderEntity entity = new WebOrderEntity();
        entity.setId(UUID.randomUUID());
        entity.setOrderNo("WO-2026-0001");
        entity.setStatus(OrderStatus.PENDING);

        when(orderRepository.searchOrders(any(), any(), any(), any()))
                .thenReturn(new PageImpl<>(List.of(entity)));

        Page<OrderResponseDTO> result = orderService.listOrders(null, null, null, Pageable.unpaged());
        assertEquals(1, result.getContent().size());
    }

    @Test
    void testGetOrderHistory() throws SystemGlobalException {
        UUID id = UUID.randomUUID();
        WebOrderEntity entity = new WebOrderEntity();
        entity.setId(id);

        when(orderRepository.findById(id)).thenReturn(Optional.of(entity));
        when(statusHistoryRepository.findByOrderIdOrderByChangedAtAsc(id)).thenReturn(List.of());

        var result = orderService.getOrderHistory(id);
        assertTrue(result.isEmpty());
    }
}
