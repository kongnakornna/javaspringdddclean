package com.icmon.module.weborder.presentation.controller;

import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.weborder.application.interfaces.SalesPriceService;
import com.icmon.module.weborder.presentation.dto.response.SalesPriceResponseDTO;
import com.icmon.exception.SystemGlobalException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wos/sales-prices")
@Tag(name = "Web Order - Sales Price", description = "Sales Price Management APIs (Admin)")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class SalesPriceController {

    private final SalesPriceService salesPriceService;

    @GetMapping("/item/{itemId}")
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Get sales prices by item")
    public ResponseEntity<List<SalesPriceResponseDTO>> getPricesByItem(@PathVariable UUID itemId)
            throws SystemGlobalException {
        List<SalesPriceResponseDTO> prices = salesPriceService.getPricesByItem(itemId);
        return ResponseEntity.ok(prices);
    }

    @PostMapping
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Create sales price")
    public ResponseEntity<SalesPriceResponseDTO> createPrice(@RequestBody SalesPriceResponseDTO request)
            throws SystemGlobalException {
        SalesPriceResponseDTO response = salesPriceService.createPrice(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Update sales price")
    public ResponseEntity<SalesPriceResponseDTO> updatePrice(
            @PathVariable UUID id,
            @RequestBody SalesPriceResponseDTO request) throws SystemGlobalException {
        SalesPriceResponseDTO response = salesPriceService.updatePrice(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @RateLimit(limit = 10, duration = 3600, keyType = "USER_ID")
    @Operation(summary = "Delete sales price")
    public ResponseEntity<Void> deletePrice(@PathVariable UUID id) throws SystemGlobalException {
        salesPriceService.deletePrice(id);
        return ResponseEntity.noContent().build();
    }
}
