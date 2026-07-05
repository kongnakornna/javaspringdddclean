package com.icmon.module.purchase.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.purchase.application.interfaces.PurchaseOrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RemoveDetailUseCase {
    private final PurchaseOrderDetailService purchaseOrderDetailService;
    public void execute(UUID poHeaderId, UUID detailId) throws SystemGlobalException {
        purchaseOrderDetailService.removeDetail(poHeaderId, detailId);
    }
}
