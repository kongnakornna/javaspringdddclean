package com.icmon.module.purchase.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.purchase.application.interfaces.PurchaseOrderDetailService;
import com.icmon.module.purchase.presentation.dto.request.PurchaseOrderDetailRequestDTO;
import com.icmon.module.purchase.presentation.dto.response.PurchaseOrderDetailResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateDetailUseCase {
    private final PurchaseOrderDetailService purchaseOrderDetailService;
    public PurchaseOrderDetailResponseDTO execute(UUID poHeaderId, UUID detailId, PurchaseOrderDetailRequestDTO request) throws SystemGlobalException {
        return purchaseOrderDetailService.updateDetail(poHeaderId, detailId, request);
    }
}
