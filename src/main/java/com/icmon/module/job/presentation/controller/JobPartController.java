package com.icmon.module.job.presentation.controller;

import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.job.application.interfaces.JobPartSaleService;
import com.icmon.module.job.presentation.dto.request.JobPartRequestDTO;
import com.icmon.module.job.presentation.dto.response.JobPartResponseDTO;
import com.icmon.exception.SystemGlobalException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/jobs/{jobId}/parts")
@Tag(name = "Job Part Sales", description = "Manage parts used in a job card")
@RequiredArgsConstructor
public class JobPartController {

    private final JobPartSaleService jobPartSaleService;

    /*
        API: POST /api/v1/jobs/{jobId}/parts
        ฟังก์ชันนี้เพิ่มอะไหล่เข้าใบงาน
        This function adds a part to the job card.
        Rate Limit: อนุญาต 30 ครั้งต่อ 1 นาที / Allows 30 requests per minute.
    */
    @PostMapping
    @RateLimit(limit = 30, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Add part to job")
    public ResponseEntity<JobPartResponseDTO> addPart(@PathVariable UUID jobId,
                                                      @Valid @RequestBody JobPartRequestDTO request)
            throws SystemGlobalException {
        return ResponseEntity.ok(jobPartSaleService.addPart(jobId, request));
    }

    /*
        API: GET /api/v1/jobs/{jobId}/parts
        ฟังก์ชันนี้ดึงรายการอะไหล่ทั้งหมดของใบงาน
        This function retrieves all parts for a job card.
        Rate Limit: อนุญาต 50 ครั้งต่อ 1 นาที / Allows 50 requests per minute.
    */
    @GetMapping
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Get all parts of job")
    public ResponseEntity<List<JobPartResponseDTO>> getParts(@PathVariable UUID jobId)
            throws SystemGlobalException {
        return ResponseEntity.ok(jobPartSaleService.getPartsByJobId(jobId));
    }

    /*
        API: DELETE /api/v1/jobs/{jobId}/parts/{partSaleId}
        ฟังก์ชันนี้ลบรายการอะไหล่ออกจากใบงาน
        This function removes a part from the job card.
        Rate Limit: อนุญาต 20 ครั้งต่อ 1 นาที / Allows 20 requests per minute.
    */
    @DeleteMapping("/{partSaleId}")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Remove part from job")
    public ResponseEntity<Void> removePart(@PathVariable UUID jobId,
                                           @PathVariable UUID partSaleId)
            throws SystemGlobalException {
        jobPartSaleService.removePart(partSaleId);
        return ResponseEntity.noContent().build();
    }
}
