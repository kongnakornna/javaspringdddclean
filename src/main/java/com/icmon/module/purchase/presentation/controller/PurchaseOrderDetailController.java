package com.icmon.module.purchase.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.purchase.application.usecase.AddDetailUseCase;
import com.icmon.module.purchase.application.usecase.RemoveDetailUseCase;
import com.icmon.module.purchase.application.usecase.UpdateDetailUseCase;
import com.icmon.module.purchase.presentation.dto.request.PurchaseOrderDetailRequestDTO;
import com.icmon.module.purchase.presentation.dto.response.PurchaseOrderDetailResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/purchase-orders/{poHeaderId}/details")
@RequiredArgsConstructor
@Tag(name = "Purchase Order Detail Management", description = "APIs for managing purchase order line items")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class PurchaseOrderDetailController {

    private final AddDetailUseCase addDetailUseCase;
    private final UpdateDetailUseCase updateDetailUseCase;
    private final RemoveDetailUseCase removeDetailUseCase;

    @PostMapping
    @Operation(summary = "เพิ่มรายการในใบสั่งซื้อ / Add detail to purchase order")
    @RateLimit(limit = 30, duration = 60, keyType = "USER_ID")
    public ResponseEntity<PurchaseOrderDetailResponseDTO> addDetail(
            @PathVariable UUID poHeaderId,
            @Valid @RequestBody PurchaseOrderDetailRequestDTO request) throws SystemGlobalException {
        PurchaseOrderDetailResponseDTO result = addDetailUseCase.execute(poHeaderId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{detailId}")
    @Operation(summary = "แก้ไขรายการในใบสั่งซื้อ / Update purchase order detail")
    @RateLimit(limit = 30, duration = 60, keyType = "USER_ID")
    public ResponseEntity<PurchaseOrderDetailResponseDTO> updateDetail(
            @PathVariable UUID poHeaderId,
            @PathVariable UUID detailId,
            @Valid @RequestBody PurchaseOrderDetailRequestDTO request) throws SystemGlobalException {
        PurchaseOrderDetailResponseDTO result = updateDetailUseCase.execute(poHeaderId, detailId, request);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{detailId}")
    @Operation(summary = "ลบรายการในใบสั่งซื้อ / Remove detail from purchase order")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    public ResponseEntity<Void> removeDetail(
            @PathVariable UUID poHeaderId,
            @PathVariable UUID detailId) throws SystemGlobalException {
        removeDetailUseCase.execute(poHeaderId, detailId);
        return ResponseEntity.noContent().build();
    }
}
