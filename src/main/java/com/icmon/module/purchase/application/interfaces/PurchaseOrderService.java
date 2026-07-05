package com.icmon.module.purchase.application.interfaces;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.purchase.presentation.dto.request.PurchaseOrderCreateRequestDTO;
import com.icmon.module.purchase.presentation.dto.request.PurchaseOrderReceiveRequestDTO;
import com.icmon.module.purchase.presentation.dto.request.PurchaseOrderSendRequestDTO;
import com.icmon.module.purchase.presentation.dto.request.PurchaseOrderUpdateRequestDTO;
import com.icmon.module.purchase.presentation.dto.response.PurchaseOrderResponseDTO;
import com.icmon.module.purchase.presentation.dto.response.PurchaseOrderStatusHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PurchaseOrderService {
    PurchaseOrderResponseDTO createPurchaseOrder(PurchaseOrderCreateRequestDTO request) throws SystemGlobalException;
    PurchaseOrderResponseDTO getPurchaseOrder(UUID id) throws SystemGlobalException;
    PurchaseOrderResponseDTO getPurchaseOrderByPoNo(String poNo) throws SystemGlobalException;
    Page<PurchaseOrderResponseDTO> listPurchaseOrders(String status, UUID supplierId, String startDate, String endDate, Pageable pageable) throws SystemGlobalException;
    PurchaseOrderResponseDTO updatePurchaseOrder(UUID id, PurchaseOrderUpdateRequestDTO request) throws SystemGlobalException;
    void deletePurchaseOrder(UUID id) throws SystemGlobalException;
    PurchaseOrderResponseDTO sendPurchaseOrder(UUID id, PurchaseOrderSendRequestDTO request) throws SystemGlobalException;
    PurchaseOrderResponseDTO receivePurchaseOrder(UUID id, PurchaseOrderReceiveRequestDTO request) throws SystemGlobalException;
    PurchaseOrderResponseDTO cancelPurchaseOrder(UUID id, String reason) throws SystemGlobalException;
    byte[] generatePurchaseOrderPDF(UUID id) throws SystemGlobalException;
    List<PurchaseOrderStatusHistoryDTO> getPurchaseOrderHistory(UUID id) throws SystemGlobalException;
}
