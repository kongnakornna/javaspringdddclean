package com.icmon.module.weborder.application.usecase;

import com.icmon.module.weborder.application.interfaces.OrderService;
import com.icmon.module.weborder.presentation.dto.response.OrderResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListOrdersUseCase {
    private final OrderService orderService;

    public Page<OrderResponseDTO> execute(String status, String startDate, String endDate, Pageable pageable) throws SystemGlobalException {
        return orderService.listOrders(status, startDate, endDate, pageable);
    }
}
