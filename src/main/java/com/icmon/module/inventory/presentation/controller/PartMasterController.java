package com.icmon.module.inventory.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.inventory.application.interfaces.PartMasterService;
import com.icmon.module.inventory.presentation.dto.request.PartCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.request.PartUpdateRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.PartResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/parts")
@RequiredArgsConstructor
@Tag(name = "Inventory - Part Master", description = "จัดการข้อมูลอะไหล่หลัก // Part Master Management APIs")
public class PartMasterController {

    private final PartMasterService partMasterService;

    @PostMapping
    @RateLimit(limit = 20, duration = 60)
    @Operation(summary = "เพิ่มอะไหล่ใหม่", description = "Create a new part master record")
    public ResponseEntity<PartResponseDTO> createPart(@Valid @RequestBody PartCreateRequestDTO request) throws SystemGlobalException {
        return ResponseEntity.status(HttpStatus.CREATED).body(partMasterService.createPart(request));
    }

    @GetMapping
    @RateLimit(limit = 100, duration = 60)
    @Operation(summary = "รายการอะไหล่ทั้งหมด", description = "Get paginated list of all parts")
    public ResponseEntity<Page<PartResponseDTO>> getAllParts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) throws SystemGlobalException {
        return ResponseEntity.ok(partMasterService.getAllParts(page, size));
    }

    @GetMapping("/{id}")
    @RateLimit(limit = 100, duration = 60)
    @Operation(summary = "ดูอะไหล่ตาม ID", description = "Get part by ID")
    public ResponseEntity<PartResponseDTO> getPartById(@PathVariable UUID id) throws SystemGlobalException {
        return ResponseEntity.ok(partMasterService.getPartById(id));
    }

    @GetMapping("/code/{code}")
    @RateLimit(limit = 80, duration = 60)
    @Operation(summary = "ค้นหาอะไหล่ด้วยรหัส", description = "Get part by part code")
    public ResponseEntity<PartResponseDTO> getPartByCode(@PathVariable String code) throws SystemGlobalException {
        return ResponseEntity.ok(partMasterService.getPartByCode(code));
    }

    @PutMapping("/{id}")
    @RateLimit(limit = 15, duration = 60)
    @Operation(summary = "แก้ไขอะไหล่", description = "Update part information")
    public ResponseEntity<PartResponseDTO> updatePart(@PathVariable UUID id, @Valid @RequestBody PartUpdateRequestDTO request) throws SystemGlobalException {
        return ResponseEntity.ok(partMasterService.updatePart(id, request));
    }

    @DeleteMapping("/{id}")
    @RateLimit(limit = 10, duration = 3600)
    @Operation(summary = "ลบอะไหล่", description = "Soft delete a part record")
    public ResponseEntity<Void> deletePart(@PathVariable UUID id) throws SystemGlobalException {
        partMasterService.deletePart(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/low-stock")
    @RateLimit(limit = 30, duration = 60)
    @Operation(summary = "สินค้าต่ำกว่าเกณฑ์", description = "Get list of low stock parts")
    public ResponseEntity<Page<PartResponseDTO>> getLowStockParts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) throws SystemGlobalException {
        return ResponseEntity.ok(partMasterService.getLowStockParts(page, size));
    }
}
