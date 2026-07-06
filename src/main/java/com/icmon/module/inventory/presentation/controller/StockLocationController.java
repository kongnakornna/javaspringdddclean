package com.icmon.module.inventory.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.inventory.application.interfaces.StockLocationService;
import com.icmon.module.inventory.presentation.dto.request.StockLocationRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.StockLocationResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/stock-locations")
@RequiredArgsConstructor
@Tag(name = "Inventory - Stock Location", description = "ตำแหน่งจัดเก็บสินค้า // Stock Location APIs")
public class StockLocationController {

    private final StockLocationService locationService;

    @GetMapping
    @RateLimit(limit = 30, duration = 60)
    @Operation(summary = "รายการตำแหน่งจัดเก็บ", description = "Get all stock locations")
    public ResponseEntity<List<StockLocationResponseDTO>> getAllLocations() throws SystemGlobalException {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    @PostMapping
    @RateLimit(limit = 10, duration = 60)
    @Operation(summary = "เพิ่มตำแหน่งจัดเก็บ", description = "Create a new stock location")
    public ResponseEntity<StockLocationResponseDTO> createLocation(@Valid @RequestBody StockLocationRequestDTO request) throws SystemGlobalException {
        return ResponseEntity.status(HttpStatus.CREATED).body(locationService.createLocation(request));
    }
}
