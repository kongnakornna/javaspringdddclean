package com.icmon.module.purchase.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.purchase.application.interfaces.PurchaseOrderService;
import com.icmon.module.purchase.presentation.dto.request.PurchaseOrderSendRequestDTO;
import com.icmon.module.purchase.presentation.dto.response.PurchaseOrderResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SendPurchaseOrderUseCase {
    private final PurchaseOrderService purchaseOrderService;
    public PurchaseOrderResponseDTO execute(UUID id, PurchaseOrderSendRequestDTO request) throws SystemGlobalException {
        return purchaseOrderService.sendPurchaseOrder(id, request);
    }
}
