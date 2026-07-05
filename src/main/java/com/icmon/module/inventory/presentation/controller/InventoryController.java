package com.icmon.module.inventory.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.inventory.application.usecase.*;
import com.icmon.module.inventory.presentation.dto.request.InventoryTransactionRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.InventoryResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
@Tag(name = "Inventory Management", description = "APIs for managing inventory transactions")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class InventoryController {

    private final RecordTransactionUseCase recordTransactionUseCase;
    private final GetTransactionUseCase getTransactionUseCase;
    private final SearchTransactionsUseCase searchTransactionsUseCase;

    @PostMapping("/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "บันทึกการเคลื่อนไหวสินค้า / Record inventory transaction")
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    public ResponseEntity<InventoryResponseDTO> recordTransaction(@Valid @RequestBody InventoryTransactionRequestDTO request) throws SystemGlobalException {
        InventoryResponseDTO result = recordTransactionUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/transactions/{id}")
    @Operation(summary = "ดึงข้อมูลการเคลื่อนไหว / Get transaction by ID")
    @RateLimit(limit = 100, duration = 60, keyType = "USER_ID")
    public ResponseEntity<InventoryResponseDTO> getTransaction(@PathVariable UUID id) throws SystemGlobalException {
        InventoryResponseDTO result = getTransactionUseCase.execute(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/transactions")
    @Operation(summary = "ค้นหาการเคลื่อนไหวสินค้า / Search inventory transactions")
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    public ResponseEntity<Page<InventoryResponseDTO>> searchTransactions(
            @RequestParam(required = false) UUID partId,
            @RequestParam(required = false) String transactionType,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) throws SystemGlobalException {
        Page<InventoryResponseDTO> result = searchTransactionsUseCase.execute(partId, transactionType, startDate, endDate, PageRequest.of(page, size));
        return ResponseEntity.ok(result);
    }
}
