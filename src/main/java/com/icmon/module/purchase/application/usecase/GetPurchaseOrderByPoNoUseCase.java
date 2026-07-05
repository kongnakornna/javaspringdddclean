package com.icmon.module.purchase.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.purchase.application.interfaces.PurchaseOrderService;
import com.icmon.module.purchase.presentation.dto.response.PurchaseOrderResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetPurchaseOrderByPoNoUseCase {
    private final PurchaseOrderService purchaseOrderService;
    public PurchaseOrderResponseDTO execute(String poNo) throws SystemGlobalException {
        return purchaseOrderService.getPurchaseOrderByPoNo(poNo);
    }
}
