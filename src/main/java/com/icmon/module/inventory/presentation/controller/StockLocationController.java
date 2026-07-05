package com.icmon.module.inventory.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.inventory.application.usecase.*;
import com.icmon.module.inventory.presentation.dto.request.StockLocationCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.request.StockLocationUpdateRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.StockLocationResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/stock-locations")
@RequiredArgsConstructor
@Tag(name = "Stock Location Management", description = "APIs for managing stock locations")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class StockLocationController {

    private final CreateLocationUseCase createLocationUseCase;
    private final GetLocationUseCase getLocationUseCase;
    private final ListActiveLocationsUseCase listActiveLocationsUseCase;
    private final UpdateLocationUseCase updateLocationUseCase;
    private final DeleteLocationUseCase deleteLocationUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "สร้างตำแหน่งจัดเก็บ / Create stock location")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    public ResponseEntity<StockLocationResponseDTO> createLocation(@Valid @RequestBody StockLocationCreateRequestDTO request) throws SystemGlobalException {
        StockLocationResponseDTO result = createLocationUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "ดึงข้อมูลตำแหน่งจัดเก็บ / Get stock location by ID")
    @RateLimit(limit = 100, duration = 60, keyType = "USER_ID")
    public ResponseEntity<StockLocationResponseDTO> getLocation(@PathVariable UUID id) throws SystemGlobalException {
        StockLocationResponseDTO result = getLocationUseCase.execute(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/active")
    @Operation(summary = "รายการตำแหน่งจัดเก็บที่ใช้งาน / List active locations")
    @RateLimit(limit = 80, duration = 60, keyType = "USER_ID")
    public ResponseEntity<List<StockLocationResponseDTO>> listActiveLocations() throws SystemGlobalException {
        List<StockLocationResponseDTO> result = listActiveLocationsUseCase.execute();
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @Operation(summary = "อัปเดตตำแหน่งจัดเก็บ / Update stock location")
    @RateLimit(limit = 30, duration = 60, keyType = "USER_ID")
    public ResponseEntity<StockLocationResponseDTO> updateLocation(@PathVariable UUID id, @Valid @RequestBody StockLocationUpdateRequestDTO request) throws SystemGlobalException {
        StockLocationResponseDTO result = updateLocationUseCase.execute(id, request);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "ลบตำแหน่งจัดเก็บ / Delete stock location (soft delete)")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    public ResponseEntity<Void> deleteLocation(@PathVariable UUID id) throws SystemGlobalException {
        deleteLocationUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
