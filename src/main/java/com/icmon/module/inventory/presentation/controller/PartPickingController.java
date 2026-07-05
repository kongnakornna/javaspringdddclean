package com.icmon.module.inventory.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.inventory.application.usecase.*;
import com.icmon.module.inventory.presentation.dto.request.PickingCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.PickingResponseDTO;
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
@RequestMapping("/api/v1/picking-requests")
@RequiredArgsConstructor
@Tag(name = "Part Picking", description = "APIs for managing part picking requests")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class PartPickingController {

    private final CreatePickingRequestUseCase createPickingRequestUseCase;
    private final GetPickingRequestUseCase getPickingRequestUseCase;
    private final GetPickingRequestByNoUseCase getPickingRequestByNoUseCase;
    private final ListPickingRequestsUseCase listPickingRequestsUseCase;
    private final PickItemsUseCase pickItemsUseCase;
    private final ConfirmPickingUseCase confirmPickingUseCase;
    private final DeletePickingRequestUseCase deletePickingRequestUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "สร้างคำขอเบิกอะไหล่ / Create picking request")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    public ResponseEntity<PickingResponseDTO> createPickingRequest(@Valid @RequestBody PickingCreateRequestDTO request) throws SystemGlobalException {
        PickingResponseDTO result = createPickingRequestUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "ดึงข้อมูลคำขอเบิก / Get picking request by ID")
    @RateLimit(limit = 100, duration = 60, keyType = "USER_ID")
    public ResponseEntity<PickingResponseDTO> getPickingRequest(@PathVariable UUID id) throws SystemGlobalException {
        PickingResponseDTO result = getPickingRequestUseCase.execute(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/by-no/{pickingNo}")
    @Operation(summary = "ดึงข้อมูลคำขอเบิกตามเลขที่ / Get picking request by number")
    @RateLimit(limit = 80, duration = 60, keyType = "USER_ID")
    public ResponseEntity<PickingResponseDTO> getPickingRequestByNo(@PathVariable String pickingNo) throws SystemGlobalException {
        PickingResponseDTO result = getPickingRequestByNoUseCase.execute(pickingNo);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    @Operation(summary = "รายการคำขอเบิก / List picking requests")
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    public ResponseEntity<Page<PickingResponseDTO>> listPickingRequests(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) UUID jobId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) throws SystemGlobalException {
        Page<PickingResponseDTO> result = listPickingRequestsUseCase.execute(status, jobId, PageRequest.of(page, size));
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}/pick")
    @Operation(summary = "เริ่มเบิกอะไหล่ / Start picking items")
    @RateLimit(limit = 30, duration = 60, keyType = "USER_ID")
    public ResponseEntity<PickingResponseDTO> pickItems(@PathVariable UUID id) throws SystemGlobalException {
        PickingResponseDTO result = pickItemsUseCase.execute(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}/confirm")
    @Operation(summary = "ยืนยันการเบิกอะไหล่ / Confirm picking")
    @RateLimit(limit = 30, duration = 60, keyType = "USER_ID")
    public ResponseEntity<PickingResponseDTO> confirmPicking(@PathVariable UUID id) throws SystemGlobalException {
        PickingResponseDTO result = confirmPickingUseCase.execute(id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "ลบคำขอเบิก / Delete picking request (soft delete)")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    public ResponseEntity<Void> deletePickingRequest(@PathVariable UUID id) throws SystemGlobalException {
        deletePickingRequestUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
