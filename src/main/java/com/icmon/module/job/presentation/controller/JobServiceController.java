package com.icmon.module.job.presentation.controller;

import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.job.application.interfaces.JobService;
import com.icmon.module.job.presentation.dto.request.JobServiceRequestDTO;
import com.icmon.module.job.presentation.dto.response.JobServiceResponseDTO;
import com.icmon.exception.SystemGlobalException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/jobs/{jobId}/services")
@Tag(name = "Job Service Items", description = "Manage service items within a job card")
@RequiredArgsConstructor
public class JobServiceController {

    private final JobService jobService;

    /*
        API: POST /api/v1/jobs/{jobId}/services
        ฟังก์ชันนี้เพิ่มรายการบริการเข้าใบงาน
        This function adds a service item to the job card.
        Rate Limit: อนุญาต 30 ครั้งต่อ 1 นาที / Allows 30 requests per minute.
    */
    @PostMapping
    @RateLimit(limit = 30, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Add service item to job")
    public ResponseEntity<JobServiceResponseDTO> addService(@PathVariable UUID jobId,
                                                            @Valid @RequestBody JobServiceRequestDTO request)
            throws SystemGlobalException {
        return ResponseEntity.ok(jobService.addService(jobId, request));
    }
}
