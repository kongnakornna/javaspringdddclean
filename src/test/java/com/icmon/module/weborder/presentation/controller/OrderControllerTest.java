package com.icmon.module.weborder.presentation.controller;

import com.icmon.module.weborder.application.interfaces.OrderService;
import com.icmon.module.weborder.presentation.dto.response.OrderResponseDTO;
import com.icmon.exception.SystemGlobalException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void testListOrders() throws Exception {
        Page<OrderResponseDTO> page = new PageImpl<>(List.of());
        when(orderService.listOrders(any(), any(), any(), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/wos/orders"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetOrder() throws Exception {
        when(orderService.getOrder(any(UUID.class))).thenThrow(new SystemGlobalException("Not found", null));

        mockMvc.perform(get("/api/v1/wos/orders/{id}", UUID.randomUUID()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testGetOrderByNumber() throws Exception {
        when(orderService.getOrderByNumber(any(String.class)))
                .thenThrow(new SystemGlobalException("Not found", null));

        mockMvc.perform(get("/api/v1/wos/orders/number/{orderNo}", "WO-2026-0001"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testGetOrderHistory() throws Exception {
        when(orderService.getOrderHistory(any(UUID.class))).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/wos/orders/{id}/history", UUID.randomUUID()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetOrderTracking() throws Exception {
        when(orderService.getOrderTracking(any(UUID.class)))
                .thenThrow(new SystemGlobalException("Not found", null));

        mockMvc.perform(get("/api/v1/wos/orders/{id}/tracking", UUID.randomUUID()))
                .andExpect(status().isInternalServerError());
    }
}
