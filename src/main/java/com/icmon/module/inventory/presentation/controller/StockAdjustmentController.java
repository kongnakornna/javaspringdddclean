package com.icmon.module.inventory.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.inventory.application.interfaces.StockAdjustmentService;
import com.icmon.module.inventory.presentation.dto.request.AdjustmentRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.AdjustmentResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/stock-adjustments")
@RequiredArgsConstructor
@Tag(name = "Inventory - Stock Adjustment", description = "การปรับปรุงสต็อก // Stock Adjustment APIs")
public class StockAdjustmentController {

    private final StockAdjustmentService adjustmentService;

    @PostMapping
    @RateLimit(limit = 10, duration = 60)
    @Operation(summary = "สร้างปรับปรุงสต็อก", description = "Create a stock adjustment")
    public ResponseEntity<AdjustmentResponseDTO> createAdjustment(@Valid @RequestBody AdjustmentRequestDTO request) throws SystemGlobalException {
        return ResponseEntity.status(HttpStatus.CREATED).body(adjustmentService.createAdjustment(request));
    }

    @PutMapping("/{id}/approve")
    @RateLimit(limit = 10, duration = 60)
    @Operation(summary = "อนุมัติปรับปรุงสต็อก", description = "Approve a stock adjustment")
    public ResponseEntity<AdjustmentResponseDTO> approveAdjustment(@PathVariable UUID id) throws SystemGlobalException {
        return ResponseEntity.ok(adjustmentService.approveAdjustment(id));
    }
}
