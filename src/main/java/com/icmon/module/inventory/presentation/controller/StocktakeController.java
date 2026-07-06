package com.icmon.module.inventory.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.inventory.application.interfaces.StockTakeService;
import com.icmon.module.inventory.presentation.dto.request.StockTakeRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.StockTakeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/stock-takes")
@RequiredArgsConstructor
@Tag(name = "Inventory - Stock Take", description = "การตรวจนับสต็อก // Stock Take APIs")
public class StockTakeController {

    private final StockTakeService stockTakeService;

    @PostMapping
    @RateLimit(limit = 5, duration = 3600)
    @Operation(summary = "เริ่มตรวจนับสต็อก", description = "Create a stock take")
    public ResponseEntity<StockTakeResponseDTO> createStockTake(@Valid @RequestBody StockTakeRequestDTO request) throws SystemGlobalException {
        return ResponseEntity.status(HttpStatus.CREATED).body(stockTakeService.createStockTake(request));
    }

    @PutMapping("/{id}/complete")
    @RateLimit(limit = 5, duration = 3600)
    @Operation(summary = "สรุปการตรวจนับ", description = "Complete a stock take")
    public ResponseEntity<StockTakeResponseDTO> completeStockTake(@PathVariable UUID id) throws SystemGlobalException {
        return ResponseEntity.ok(stockTakeService.completeStockTake(id));
    }
}
