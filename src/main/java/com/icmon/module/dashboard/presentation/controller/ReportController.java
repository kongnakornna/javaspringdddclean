package com.icmon.module.dashboard.presentation.controller;

import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.dashboard.application.interfaces.ReportService;
import com.icmon.module.dashboard.presentation.dto.request.ReportRequestDTO;
import com.icmon.module.dashboard.presentation.dto.response.ReportResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reports")
@Tag(name = "Reports", description = "Report Generation APIs")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/daily")
    @RateLimit(limit = 10, duration = 300, keyType = "USER_ID")
    @Operation(summary = "Generate daily report")
    public ResponseEntity<ReportResponseDTO> generateDailyReport(@Valid @RequestBody ReportRequestDTO request)
            throws FailedRequestException {
        ReportResponseDTO response = reportService.generateDailyReport(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/monthly")
    @RateLimit(limit = 10, duration = 300, keyType = "USER_ID")
    @Operation(summary = "Generate monthly report")
    public ResponseEntity<ReportResponseDTO> generateMonthlyReport(@Valid @RequestBody ReportRequestDTO request)
            throws FailedRequestException {
        ReportResponseDTO response = reportService.generateMonthlyReport(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/yearly")
    @RateLimit(limit = 5, duration = 3600, keyType = "USER_ID")
    @Operation(summary = "Generate yearly report")
    public ResponseEntity<ReportResponseDTO> generateYearlyReport(@Valid @RequestBody ReportRequestDTO request)
            throws FailedRequestException {
        ReportResponseDTO response = reportService.generateYearlyReport(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{reportId}")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Get report generation status")
    public ResponseEntity<ReportResponseDTO> getReportStatus(@PathVariable String reportId)
            throws FailedRequestException {
        ReportResponseDTO response = reportService.getReportStatus(reportId);
        return ResponseEntity.ok(response);
    }
}
