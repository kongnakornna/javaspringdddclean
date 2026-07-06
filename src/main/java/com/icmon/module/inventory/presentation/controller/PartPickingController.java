package com.icmon.module.inventory.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.inventory.application.interfaces.PartPickingService;
import com.icmon.module.inventory.presentation.dto.request.PickingCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.PickingResponseDTO;
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
@RequestMapping("/api/v1/part-picking")
@RequiredArgsConstructor
@Tag(name = "Inventory - Part Picking", description = "การเบิกอะไหล่ // Part Picking APIs")
public class PartPickingController {

    private final PartPickingService partPickingService;

    @PostMapping
    @RateLimit(limit = 30, duration = 60)
    @Operation(summary = "สร้างคำขอเบิกอะไหล่", description = "Create a part picking request")
    public ResponseEntity<PickingResponseDTO> createPicking(@Valid @RequestBody PickingCreateRequestDTO request) throws SystemGlobalException {
        return ResponseEntity.status(HttpStatus.CREATED).body(partPickingService.createPicking(request));
    }

    @GetMapping("/{id}")
    @RateLimit(limit = 60, duration = 60)
    @Operation(summary = "ดูคำขอเบิกอะไหล่", description = "Get picking request by ID")
    public ResponseEntity<PickingResponseDTO> getPickingById(@PathVariable UUID id) throws SystemGlobalException {
        return ResponseEntity.ok(partPickingService.getPickingById(id));
    }

    @PutMapping("/{id}/confirm")
    @RateLimit(limit = 20, duration = 60)
    @Operation(summary = "ยืนยันการเบิกอะไหล่", description = "Confirm a picking request")
    public ResponseEntity<PickingResponseDTO> confirmPicking(@PathVariable UUID id) throws SystemGlobalException {
        return ResponseEntity.ok(partPickingService.confirmPicking(id));
    }

    @GetMapping("/job/{jobId}")
    @RateLimit(limit = 60, duration = 60)
    @Operation(summary = "ดึงคำขอเบิกตาม Job", description = "Get picking requests by job ID")
    public ResponseEntity<List<PickingResponseDTO>> getPickingByJobId(@PathVariable UUID jobId) throws SystemGlobalException {
        return ResponseEntity.ok(partPickingService.getPickingByJobId(jobId));
    }
}
