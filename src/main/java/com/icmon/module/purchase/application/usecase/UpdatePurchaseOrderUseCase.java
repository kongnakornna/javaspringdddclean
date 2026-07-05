package com.icmon.module.purchase.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.purchase.application.interfaces.PurchaseOrderService;
import com.icmon.module.purchase.presentation.dto.request.PurchaseOrderUpdateRequestDTO;
import com.icmon.module.purchase.presentation.dto.response.PurchaseOrderResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdatePurchaseOrderUseCase {
    private final PurchaseOrderService purchaseOrderService;
    public PurchaseOrderResponseDTO execute(UUID id, PurchaseOrderUpdateRequestDTO request) throws SystemGlobalException {
        return purchaseOrderService.updatePurchaseOrder(id, request);
    }
}
