package com.icmon.module.weborder.application.usecase;

import com.icmon.module.weborder.application.interfaces.OrderService;
import com.icmon.module.weborder.presentation.dto.response.OrderDetailResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetOrderUseCase {
    private final OrderService orderService;

    public OrderDetailResponseDTO execute(UUID id) throws SystemGlobalException {
        return orderService.getOrder(id);
    }
}
