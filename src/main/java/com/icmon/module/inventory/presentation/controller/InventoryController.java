package com.icmon.module.inventory.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.inventory.application.interfaces.InventoryService;
import com.icmon.module.inventory.presentation.dto.request.InventoryIssueRequestDTO;
import com.icmon.module.inventory.presentation.dto.request.InventoryReceiveRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.InventoryResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
@Tag(name = "Inventory - Movement", description = "การเคลื่อนไหวสินค้า // Inventory Movement APIs")
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/receive")
    @RateLimit(limit = 20, duration = 60)
    @Operation(summary = "รับสินค้าเข้า", description = "Receive goods into inventory")
    public ResponseEntity<InventoryResponseDTO> receiveGoods(@Valid @RequestBody InventoryReceiveRequestDTO request) throws SystemGlobalException {
        log.info("RECEIVE Part: {}, Qty: {}", request.getPartId(), request.getQuantity());
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.receiveGoods(request));
    }

    @PostMapping("/issue")
    @RateLimit(limit = 30, duration = 60)
    @Operation(summary = "เบิกจ่ายสินค้า", description = "Issue goods from inventory")
    public ResponseEntity<InventoryResponseDTO> issueGoods(@Valid @RequestBody InventoryIssueRequestDTO request) throws SystemGlobalException {
        log.info("ISSUE Part: {}, Qty: {}", request.getPartId(), request.getQuantity());
        return ResponseEntity.ok(inventoryService.issueGoods(request));
    }

    @GetMapping("/part/{partId}")
    @RateLimit(limit = 50, duration = 60)
    @Operation(summary = "ประวัติการเคลื่อนไหว", description = "Get inventory movement history by part ID")
    public ResponseEntity<List<InventoryResponseDTO>> getInventoryByPartId(@PathVariable UUID partId) throws SystemGlobalException {
        return ResponseEntity.ok(inventoryService.getInventoryByPartId(partId));
    }
}
