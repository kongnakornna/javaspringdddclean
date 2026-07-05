package com.icmon.module.purchase.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.purchase.application.interfaces.PurchaseOrderService;
import com.icmon.module.purchase.presentation.dto.response.PurchaseOrderResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ListPurchaseOrdersUseCase {
    private final PurchaseOrderService purchaseOrderService;
    public Page<PurchaseOrderResponseDTO> execute(String status, UUID supplierId, String startDate, String endDate, Pageable pageable) throws SystemGlobalException {
        return purchaseOrderService.listPurchaseOrders(status, supplierId, startDate, endDate, pageable);
    }
}
