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
public class AddDetailUseCase {
    private final PurchaseOrderDetailService purchaseOrderDetailService;
    public PurchaseOrderDetailResponseDTO execute(UUID poHeaderId, PurchaseOrderDetailRequestDTO request) throws SystemGlobalException {
        return purchaseOrderDetailService.addDetail(poHeaderId, request);
    }
}
