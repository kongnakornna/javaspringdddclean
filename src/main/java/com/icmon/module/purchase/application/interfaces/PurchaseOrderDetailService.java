package com.icmon.module.purchase.application.interfaces;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.purchase.presentation.dto.request.PurchaseOrderDetailRequestDTO;
import com.icmon.module.purchase.presentation.dto.response.PurchaseOrderDetailResponseDTO;

import java.util.List;
import java.util.UUID;

public interface PurchaseOrderDetailService {
    PurchaseOrderDetailResponseDTO addDetail(UUID poHeaderId, PurchaseOrderDetailRequestDTO request) throws SystemGlobalException;
    PurchaseOrderDetailResponseDTO updateDetail(UUID poHeaderId, UUID detailId, PurchaseOrderDetailRequestDTO request) throws SystemGlobalException;
    void removeDetail(UUID poHeaderId, UUID detailId) throws SystemGlobalException;
    List<PurchaseOrderDetailResponseDTO> getDetailsByPoHeaderId(UUID poHeaderId) throws SystemGlobalException;
}
