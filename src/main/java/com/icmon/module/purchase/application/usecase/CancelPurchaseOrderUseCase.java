package com.icmon.module.purchase.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.purchase.application.interfaces.PurchaseOrderService;
import com.icmon.module.purchase.presentation.dto.response.PurchaseOrderResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CancelPurchaseOrderUseCase {
    private final PurchaseOrderService purchaseOrderService;
    public PurchaseOrderResponseDTO execute(UUID id, String reason) throws SystemGlobalException {
        return purchaseOrderService.cancelPurchaseOrder(id, reason);
    }
}
