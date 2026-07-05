package com.icmon.module.purchase.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.purchase.application.interfaces.PurchaseOrderService;
import com.icmon.module.purchase.presentation.dto.response.PurchaseOrderStatusHistoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetPurchaseOrderHistoryUseCase {
    private final PurchaseOrderService purchaseOrderService;
    public List<PurchaseOrderStatusHistoryDTO> execute(UUID id) throws SystemGlobalException {
        return purchaseOrderService.getPurchaseOrderHistory(id);
    }
}
