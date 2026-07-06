package com.icmon.module.weborder.application.usecase;

import com.icmon.module.weborder.application.interfaces.OrderService;
import com.icmon.module.weborder.presentation.dto.request.OrderCreateRequestDTO;
import com.icmon.module.weborder.presentation.dto.response.OrderResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateOrderUseCase {
    private final OrderService orderService;

    public OrderResponseDTO execute(OrderCreateRequestDTO request) throws SystemGlobalException {
        return orderService.createOrder(request);
    }
}
