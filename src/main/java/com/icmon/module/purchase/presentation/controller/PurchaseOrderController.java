package com.icmon.module.purchase.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.purchase.application.usecase.*;
import com.icmon.module.purchase.presentation.dto.request.PurchaseOrderCreateRequestDTO;
import com.icmon.module.purchase.presentation.dto.request.PurchaseOrderReceiveRequestDTO;
import com.icmon.module.purchase.presentation.dto.request.PurchaseOrderSendRequestDTO;
import com.icmon.module.purchase.presentation.dto.request.PurchaseOrderUpdateRequestDTO;
import com.icmon.module.purchase.presentation.dto.response.PurchaseOrderResponseDTO;
import com.icmon.module.purchase.presentation.dto.response.PurchaseOrderStatusHistoryDTO;
import com.icmon.module.purchase.presentation.validator.CreatePurchaseOrderValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/purchase-orders")
@RequiredArgsConstructor
@Tag(name = "Purchase Order Management", description = "APIs for managing purchase orders")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class PurchaseOrderController {

    private final CreatePurchaseOrderUseCase createPurchaseOrderUseCase;
    private final GetPurchaseOrderUseCase getPurchaseOrderUseCase;
    private final ListPurchaseOrdersUseCase listPurchaseOrdersUseCase;
    private final UpdatePurchaseOrderUseCase updatePurchaseOrderUseCase;
    private final DeletePurchaseOrderUseCase deletePurchaseOrderUseCase;
    private final SendPurchaseOrderUseCase sendPurchaseOrderUseCase;
    private final ReceivePurchaseOrderUseCase receivePurchaseOrderUseCase;
    private final CancelPurchaseOrderUseCase cancelPurchaseOrderUseCase;
    private final GetPurchaseOrderByPoNoUseCase getPurchaseOrderByPoNoUseCase;
    private final GetPurchaseOrderHistoryUseCase getPurchaseOrderHistoryUseCase;
    private final CreatePurchaseOrderValidator createValidator;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "สร้างใบสั่งซื้อใหม่ / Create new purchase order")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    public ResponseEntity<PurchaseOrderResponseDTO> createPurchaseOrder(@Valid @RequestBody PurchaseOrderCreateRequestDTO request) throws SystemGlobalException {
        createValidator.validate(request);
        PurchaseOrderResponseDTO result = createPurchaseOrderUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "ดึงข้อมูลใบสั่งซื้อ / Get purchase order by ID")
    @RateLimit(limit = 100, duration = 60, keyType = "USER_ID")
    public ResponseEntity<PurchaseOrderResponseDTO> getPurchaseOrder(@PathVariable UUID id) throws SystemGlobalException {
        PurchaseOrderResponseDTO result = getPurchaseOrderUseCase.execute(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/by-po-no/{poNo}")
    @Operation(summary = "ดึงข้อมูลใบสั่งซื้อตามเลขที่ / Get purchase order by PO number")
    @RateLimit(limit = 80, duration = 60, keyType = "USER_ID")
    public ResponseEntity<PurchaseOrderResponseDTO> getPurchaseOrderByPoNo(@PathVariable String poNo) throws SystemGlobalException {
        PurchaseOrderResponseDTO result = getPurchaseOrderByPoNoUseCase.execute(poNo);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    @Operation(summary = "รายการใบสั่งซื้อ / List purchase orders with pagination")
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    public ResponseEntity<Page<PurchaseOrderResponseDTO>> listPurchaseOrders(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) UUID supplierId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) throws SystemGlobalException {
        Page<PurchaseOrderResponseDTO> result = listPurchaseOrdersUseCase.execute(status, supplierId, startDate, endDate, PageRequest.of(page, size));
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @Operation(summary = "แก้ไขใบสั่งซื้อ / Update purchase order")
    @RateLimit(limit = 15, duration = 60, keyType = "USER_ID")
    public ResponseEntity<PurchaseOrderResponseDTO> updatePurchaseOrder(
            @PathVariable UUID id,
            @Valid @RequestBody PurchaseOrderUpdateRequestDTO request) throws SystemGlobalException {
        PurchaseOrderResponseDTO result = updatePurchaseOrderUseCase.execute(id, request);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "ลบใบสั่งซื้อ / Delete purchase order (soft delete)")
    @RateLimit(limit = 10, duration = 3600, keyType = "USER_ID")
    public ResponseEntity<Void> deletePurchaseOrder(@PathVariable UUID id) throws SystemGlobalException {
        deletePurchaseOrderUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/send")
    @Operation(summary = "ส่งใบสั่งซื้อให้ซัพพลายเออร์ / Send purchase order to supplier")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    public ResponseEntity<PurchaseOrderResponseDTO> sendPurchaseOrder(
            @PathVariable UUID id,
            @Valid @RequestBody PurchaseOrderSendRequestDTO request) throws SystemGlobalException {
        PurchaseOrderResponseDTO result = sendPurchaseOrderUseCase.execute(id, request);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}/receive")
    @Operation(summary = "บันทึกรับสินค้า / Record goods receipt")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    public ResponseEntity<PurchaseOrderResponseDTO> receivePurchaseOrder(
            @PathVariable UUID id,
            @Valid @RequestBody PurchaseOrderReceiveRequestDTO request) throws SystemGlobalException {
        PurchaseOrderResponseDTO result = receivePurchaseOrderUseCase.execute(id, request);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "ยกเลิกใบสั่งซื้อ / Cancel purchase order")
    @RateLimit(limit = 10, duration = 60, keyType = "USER_ID")
    public ResponseEntity<PurchaseOrderResponseDTO> cancelPurchaseOrder(
            @PathVariable UUID id,
            @RequestParam(required = false) String reason) throws SystemGlobalException {
        PurchaseOrderResponseDTO result = cancelPurchaseOrderUseCase.execute(id, reason);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/history")
    @Operation(summary = "ประวัติการเปลี่ยนสถานะ / Get purchase order status history")
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    public ResponseEntity<List<PurchaseOrderStatusHistoryDTO>> getPurchaseOrderHistory(@PathVariable UUID id) throws SystemGlobalException {
        List<PurchaseOrderStatusHistoryDTO> history = getPurchaseOrderHistoryUseCase.execute(id);
        return ResponseEntity.ok(history);
    }
}
