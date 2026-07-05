package com.icmon.module.inventory.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.inventory.application.usecase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@Tag(name = "Stock Reports", description = "APIs for generating stock reports")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
public class StockReportController {

    private final GenerateStockReportUseCase generateStockReportUseCase;
    private final GeneratePartStockReportUseCase generatePartStockReportUseCase;
    private final GenerateTransactionReportUseCase generateTransactionReportUseCase;
    private final GenerateLowStockReportUseCase generateLowStockReportUseCase;

    @GetMapping("/stock")
    @Operation(summary = "รายงานสต็อกทั้งหมด / Full stock report")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    public ResponseEntity<byte[]> generateStockReport() throws SystemGlobalException {
        byte[] report = generateStockReportUseCase.execute();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=stock_report.txt")
                .contentType(MediaType.TEXT_PLAIN)
                .body(report);
    }

    @GetMapping("/stock/{partId}")
    @Operation(summary = "รายงานสต็อกตามอะไหล่ / Part stock report")
    @RateLimit(limit = 30, duration = 60, keyType = "USER_ID")
    public ResponseEntity<byte[]> generatePartStockReport(@PathVariable UUID partId) throws SystemGlobalException {
        byte[] report = generatePartStockReportUseCase.execute(partId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=part_stock_report.txt")
                .contentType(MediaType.TEXT_PLAIN)
                .body(report);
    }

    @GetMapping("/transactions/{partId}")
    @Operation(summary = "รายงานเคลื่อนไหวสินค้า / Inventory transaction report")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    public ResponseEntity<byte[]> generateTransactionReport(@PathVariable UUID partId) throws SystemGlobalException {
        byte[] report = generateTransactionReportUseCase.execute(partId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transaction_report.txt")
                .contentType(MediaType.TEXT_PLAIN)
                .body(report);
    }

    @GetMapping("/low-stock")
    @Operation(summary = "รายงานสต็อกต่ำกว่าเกณฑ์ / Low stock report")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    public ResponseEntity<byte[]> generateLowStockReport() throws SystemGlobalException {
        byte[] report = generateLowStockReportUseCase.execute();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=low_stock_report.txt")
                .contentType(MediaType.TEXT_PLAIN)
                .body(report);
    }
}
