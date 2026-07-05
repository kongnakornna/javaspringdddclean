package com.icmon.module.inventory.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.inventory.application.usecase.*;
import com.icmon.module.inventory.presentation.dto.request.StocktakeCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.request.StocktakeDetailRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.StocktakeResponseDTO;
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
@RequestMapping("/api/v1/stocktakes")
@RequiredArgsConstructor
@Tag(name = "Stocktake Management", description = "APIs for managing stock takes")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class StocktakeController {

    private final CreateStocktakeUseCase createStocktakeUseCase;
    private final GetStocktakeUseCase getStocktakeUseCase;
    private final GetStocktakeByNoUseCase getStocktakeByNoUseCase;
    private final ListStocktakesUseCase listStocktakesUseCase;
    private final AddStocktakeDetailUseCase addStocktakeDetailUseCase;
    private final CompleteStocktakeUseCase completeStocktakeUseCase;
    private final DeleteStocktakeUseCase deleteStocktakeUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "สร้างการตรวจนับสต็อก / Create stocktake")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    public ResponseEntity<StocktakeResponseDTO> createStocktake(@Valid @RequestBody StocktakeCreateRequestDTO request) throws SystemGlobalException {
        StocktakeResponseDTO result = createStocktakeUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "ดึงข้อมูลการตรวจนับ / Get stocktake by ID")
    @RateLimit(limit = 100, duration = 60, keyType = "USER_ID")
    public ResponseEntity<StocktakeResponseDTO> getStocktake(@PathVariable UUID id) throws SystemGlobalException {
        StocktakeResponseDTO result = getStocktakeUseCase.execute(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/by-no/{stocktakeNo}")
    @Operation(summary = "ดึงข้อมูลการตรวจนับตามเลขที่ / Get stocktake by number")
    @RateLimit(limit = 80, duration = 60, keyType = "USER_ID")
    public ResponseEntity<StocktakeResponseDTO> getStocktakeByNo(@PathVariable String stocktakeNo) throws SystemGlobalException {
        StocktakeResponseDTO result = getStocktakeByNoUseCase.execute(stocktakeNo);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    @Operation(summary = "รายการตรวจนับสต็อก / List stocktakes")
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    public ResponseEntity<Page<StocktakeResponseDTO>> listStocktakes(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) throws SystemGlobalException {
        Page<StocktakeResponseDTO> result = listStocktakesUseCase.execute(status, PageRequest.of(page, size));
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{stocktakeId}/details")
    @Operation(summary = "เพิ่มรายการตรวจนับ / Add stocktake detail")
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    public ResponseEntity<StocktakeResponseDTO> addDetail(@PathVariable UUID stocktakeId, @Valid @RequestBody StocktakeDetailRequestDTO request) throws SystemGlobalException {
        StocktakeResponseDTO result = addStocktakeDetailUseCase.execute(stocktakeId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}/complete")
    @Operation(summary = "ยืนยันการตรวจนับ / Complete stocktake")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    public ResponseEntity<StocktakeResponseDTO> completeStocktake(@PathVariable UUID id) throws SystemGlobalException {
        StocktakeResponseDTO result = completeStocktakeUseCase.execute(id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "ลบการตรวจนับ / Delete stocktake (soft delete)")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    public ResponseEntity<Void> deleteStocktake(@PathVariable UUID id) throws SystemGlobalException {
        deleteStocktakeUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
