package com.icmon.module.purchase.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.purchase.application.interfaces.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeletePurchaseOrderUseCase {
    private final PurchaseOrderService purchaseOrderService;
    public void execute(UUID id) throws SystemGlobalException {
        purchaseOrderService.deletePurchaseOrder(id);
    }
}
