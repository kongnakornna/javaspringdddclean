package com.icmon.module.weborder.application.interfaces;

import com.icmon.module.weborder.presentation.dto.request.OrderCreateRequestDTO;
import com.icmon.module.weborder.presentation.dto.request.OrderStatusUpdateRequestDTO;
import com.icmon.module.weborder.presentation.dto.response.OrderResponseDTO;
import com.icmon.module.weborder.presentation.dto.response.OrderDetailResponseDTO;
import com.icmon.module.weborder.presentation.dto.response.OrderStatusHistoryDTO;
import com.icmon.module.weborder.presentation.dto.response.OrderTrackingResponseDTO;
import com.icmon.exception.SystemGlobalException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderResponseDTO createOrder(OrderCreateRequestDTO request) throws SystemGlobalException;

    OrderDetailResponseDTO getOrder(UUID id) throws SystemGlobalException;

    OrderDetailResponseDTO getOrderByNumber(String orderNo) throws SystemGlobalException;

    Page<OrderResponseDTO> listOrders(String status, String startDate, String endDate, Pageable pageable) throws SystemGlobalException;

    OrderResponseDTO updateOrderStatus(UUID id, OrderStatusUpdateRequestDTO request) throws SystemGlobalException;

    OrderResponseDTO cancelOrder(UUID id, String reason) throws SystemGlobalException;

    List<OrderStatusHistoryDTO> getOrderHistory(UUID id) throws SystemGlobalException;

    OrderTrackingResponseDTO getOrderTracking(UUID id) throws SystemGlobalException;
}
