package com.icmon.module.purchase.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.purchase.application.interfaces.PurchaseOrderService;
import com.icmon.module.purchase.presentation.dto.request.PurchaseOrderCreateRequestDTO;
import com.icmon.module.purchase.presentation.dto.response.PurchaseOrderResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreatePurchaseOrderUseCase {
    private final PurchaseOrderService purchaseOrderService;
    public PurchaseOrderResponseDTO execute(PurchaseOrderCreateRequestDTO request) throws SystemGlobalException {
        return purchaseOrderService.createPurchaseOrder(request);
    }
}
