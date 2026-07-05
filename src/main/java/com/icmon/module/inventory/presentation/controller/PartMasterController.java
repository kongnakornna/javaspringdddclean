package com.icmon.module.inventory.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.inventory.application.usecase.*;
import com.icmon.module.inventory.presentation.dto.request.PartMasterCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.request.PartMasterUpdateRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.PartMasterResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/parts")
@RequiredArgsConstructor
@Tag(name = "Part Master Management", description = "APIs for managing part master data")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class PartMasterController {

    private final CreatePartUseCase createPartUseCase;
    private final GetPartUseCase getPartUseCase;
    private final GetPartByCodeUseCase getPartByCodeUseCase;
    private final SearchPartsUseCase searchPartsUseCase;
    private final UpdatePartUseCase updatePartUseCase;
    private final DeletePartUseCase deletePartUseCase;
    private final UpdateStockQuantityUseCase updateStockQuantityUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "สร้างข้อมูลอะไหล่ / Create new part")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    public ResponseEntity<PartMasterResponseDTO> createPart(@Valid @RequestBody PartMasterCreateRequestDTO request) throws SystemGlobalException {
        PartMasterResponseDTO result = createPartUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "ดึงข้อมูลอะไหล่ / Get part by ID")
    @RateLimit(limit = 100, duration = 60, keyType = "USER_ID")
    public ResponseEntity<PartMasterResponseDTO> getPart(@PathVariable UUID id) throws SystemGlobalException {
        PartMasterResponseDTO result = getPartUseCase.execute(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/by-code/{partCode}")
    @Operation(summary = "ดึงข้อมูลอะไหล่ตามรหัส / Get part by code")
    @RateLimit(limit = 80, duration = 60, keyType = "USER_ID")
    public ResponseEntity<PartMasterResponseDTO> getPartByCode(@PathVariable String partCode) throws SystemGlobalException {
        PartMasterResponseDTO result = getPartByCodeUseCase.execute(partCode);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    @Operation(summary = "ค้นหาอะไหล่ / Search parts with pagination")
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    public ResponseEntity<Page<PartMasterResponseDTO>> searchParts(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) throws SystemGlobalException {
        Page<PartMasterResponseDTO> result = searchPartsUseCase.execute(search, categoryId, status, PageRequest.of(page, size));
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @Operation(summary = "อัปเดตข้อมูลอะไหล่ / Update part")
    @RateLimit(limit = 30, duration = 60, keyType = "USER_ID")
    public ResponseEntity<PartMasterResponseDTO> updatePart(@PathVariable UUID id, @Valid @RequestBody PartMasterUpdateRequestDTO request) throws SystemGlobalException {
        PartMasterResponseDTO result = updatePartUseCase.execute(id, request);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "ลบข้อมูลอะไหล่ / Delete part (soft delete)")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    public ResponseEntity<Void> deletePart(@PathVariable UUID id) throws SystemGlobalException {
        deletePartUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/stock")
    @Operation(summary = "อัปเดตจำนวนสต็อก / Update stock quantity")
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    public ResponseEntity<PartMasterResponseDTO> updateStockQuantity(@PathVariable UUID id, @RequestParam int quantity) throws SystemGlobalException {
        PartMasterResponseDTO result = updateStockQuantityUseCase.execute(id, quantity);
        return ResponseEntity.ok(result);
    }
}
