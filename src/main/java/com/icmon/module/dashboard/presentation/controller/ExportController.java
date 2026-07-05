package com.icmon.module.dashboard.presentation.controller;

import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.dashboard.application.interfaces.ExportService;
import com.icmon.module.dashboard.presentation.dto.request.ExportRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/export")
@Tag(name = "Export", description = "Data Export APIs")
@RequiredArgsConstructor
public class ExportController {

    private final ExportService exportService;

    @PostMapping("/excel")
    @RateLimit(limit = 10, duration = 300, keyType = "USER_ID")
    @Operation(summary = "Export data to Excel")
    public ResponseEntity<byte[]> exportToExcel(@Valid @RequestBody ExportRequestDTO request)
            throws FailedRequestException {
        byte[] excelData = exportService.exportToExcel(request);
        return ResponseEntity.ok()
                .header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .header("Content-Disposition", "attachment; filename=report_" + request.getReportType() + ".xlsx")
                .body(excelData);
    }

    @PostMapping("/pdf")
    @RateLimit(limit = 10, duration = 300, keyType = "USER_ID")
    @Operation(summary = "Export data to PDF")
    public ResponseEntity<byte[]> exportToPDF(@Valid @RequestBody ExportRequestDTO request)
            throws FailedRequestException {
        byte[] pdfData = exportService.exportToPDF(request);
        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "attachment; filename=report_" + request.getReportType() + ".pdf")
                .body(pdfData);
    }

    @PostMapping("/csv")
    @RateLimit(limit = 15, duration = 300, keyType = "USER_ID")
    @Operation(summary = "Export data to CSV")
    public ResponseEntity<byte[]> exportToCSV(@Valid @RequestBody ExportRequestDTO request)
            throws FailedRequestException {
        byte[] csvData = exportService.exportToCSV(request);
        return ResponseEntity.ok()
                .header("Content-Type", "text/csv")
                .header("Content-Disposition", "attachment; filename=report_" + request.getReportType() + ".csv")
                .body(csvData);
    }
}
