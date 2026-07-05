package com.icmon.module.inventory.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.inventory.application.usecase.*;
import com.icmon.module.inventory.presentation.dto.request.AdjustmentApproveRequestDTO;
import com.icmon.module.inventory.presentation.dto.request.AdjustmentCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.AdjustmentResponseDTO;
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
@RequestMapping("/api/v1/adjustments")
@RequiredArgsConstructor
@Tag(name = "Inventory Adjustment", description = "APIs for managing stock adjustments")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AdjustmentController {

    private final CreateAdjustmentUseCase createAdjustmentUseCase;
    private final GetAdjustmentUseCase getAdjustmentUseCase;
    private final GetAdjustmentByNoUseCase getAdjustmentByNoUseCase;
    private final ListAdjustmentsUseCase listAdjustmentsUseCase;
    private final ApproveAdjustmentUseCase approveAdjustmentUseCase;
    private final DeleteAdjustmentUseCase deleteAdjustmentUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "สร้างการปรับปรุงสต็อก / Create stock adjustment")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    public ResponseEntity<AdjustmentResponseDTO> createAdjustment(@Valid @RequestBody AdjustmentCreateRequestDTO request) throws SystemGlobalException {
        AdjustmentResponseDTO result = createAdjustmentUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "ดึงข้อมูลการปรับปรุงสต็อก / Get adjustment by ID")
    @RateLimit(limit = 100, duration = 60, keyType = "USER_ID")
    public ResponseEntity<AdjustmentResponseDTO> getAdjustment(@PathVariable UUID id) throws SystemGlobalException {
        AdjustmentResponseDTO result = getAdjustmentUseCase.execute(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/by-no/{adjustmentNo}")
    @Operation(summary = "ดึงข้อมูลการปรับปรุงสต็อกตามเลขที่ / Get adjustment by number")
    @RateLimit(limit = 80, duration = 60, keyType = "USER_ID")
    public ResponseEntity<AdjustmentResponseDTO> getAdjustmentByNo(@PathVariable String adjustmentNo) throws SystemGlobalException {
        AdjustmentResponseDTO result = getAdjustmentByNoUseCase.execute(adjustmentNo);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    @Operation(summary = "รายการปรับปรุงสต็อก / List adjustments")
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    public ResponseEntity<Page<AdjustmentResponseDTO>> listAdjustments(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) throws SystemGlobalException {
        Page<AdjustmentResponseDTO> result = listAdjustmentsUseCase.execute(status, type, PageRequest.of(page, size));
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}/approve")
    @Operation(summary = "อนุมัติการปรับปรุงสต็อก / Approve adjustment")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    public ResponseEntity<AdjustmentResponseDTO> approveAdjustment(@PathVariable UUID id, @Valid @RequestBody AdjustmentApproveRequestDTO request) throws SystemGlobalException {
        AdjustmentResponseDTO result = approveAdjustmentUseCase.execute(id, request);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "ลบการปรับปรุงสต็อก / Delete adjustment (soft delete)")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    public ResponseEntity<Void> deleteAdjustment(@PathVariable UUID id) throws SystemGlobalException {
        deleteAdjustmentUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
