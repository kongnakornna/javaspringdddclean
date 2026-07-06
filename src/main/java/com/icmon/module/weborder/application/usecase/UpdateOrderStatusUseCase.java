package com.icmon.module.weborder.application.usecase;

import com.icmon.module.weborder.application.interfaces.OrderService;
import com.icmon.module.weborder.presentation.dto.request.OrderStatusUpdateRequestDTO;
import com.icmon.module.weborder.presentation.dto.response.OrderResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateOrderStatusUseCase {
    private final OrderService orderService;

    public OrderResponseDTO execute(UUID id, OrderStatusUpdateRequestDTO request) throws SystemGlobalException {
        return orderService.updateOrderStatus(id, request);
    }
}
