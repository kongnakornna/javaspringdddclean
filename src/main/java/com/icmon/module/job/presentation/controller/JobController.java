package com.icmon.module.job.presentation.controller;

import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.job.application.interfaces.JobService;
import com.icmon.module.job.presentation.dto.request.JobCreateRequestDTO;
import com.icmon.module.job.presentation.dto.request.JobPartRequestDTO;
import com.icmon.module.job.presentation.dto.request.JobServiceRequestDTO;
import com.icmon.module.job.presentation.dto.request.JobStatusChangeRequestDTO;
import com.icmon.module.job.presentation.dto.request.JobUpdateRequestDTO;
import com.icmon.module.job.presentation.dto.response.JobPartResponseDTO;
import com.icmon.module.job.presentation.dto.response.JobResponseDTO;
import com.icmon.module.job.presentation.dto.response.JobServiceResponseDTO;
import com.icmon.module.job.presentation.dto.response.JobStatusHistoryDTO;
import com.icmon.exception.SystemGlobalException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/jobs")
@Tag(name = "Job Card", description = "Job Card Management APIs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    // ========================================================================
    // 1. CREATE JOB
    // ========================================================================

    /*
        API: POST /api/v1/jobs
        ฟังก์ชันนี้สร้างใบงานใหม่ โดยรับข้อมูลลูกค้า รถยนต์ และช่างที่รับผิดชอบ
        This function creates a new job card by receiving customer, vehicle, and assigned mechanic data.
        Rate Limit: อนุญาต 30 ครั้งต่อ 1 นาที / Allows 30 requests per minute.
    */
    @PostMapping
    @RateLimit(limit = 30, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Create a new job card")
    public ResponseEntity<JobResponseDTO> createJob(@Valid @RequestBody JobCreateRequestDTO request)
            throws SystemGlobalException {
        return ResponseEntity.ok(jobService.createJob(request));
    }

    // ========================================================================
    // 2. GET JOB BY ID
    // ========================================================================

    /*
        API: GET /api/v1/jobs/{id}
        ฟังก์ชันนี้ดึงข้อมูลใบงานตาม ID (ใช้ Cache ช่วยลดภาระฐานข้อมูล)
        This function retrieves a job by its ID (uses caching to reduce database load).
        Rate Limit: อนุญาต 100 ครั้งต่อ 1 นาที / Allows 100 requests per minute.
    */
    @GetMapping("/{id}")
    @RateLimit(limit = 100, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Get job by ID")
    public ResponseEntity<JobResponseDTO> getJob(@PathVariable UUID id) throws SystemGlobalException {
        return ResponseEntity.ok(jobService.getJob(id));
    }

    // ========================================================================
    // 3. LIST JOBS WITH PAGINATION
    // ========================================================================

    /*
        API: GET /api/v1/jobs
        ฟังก์ชันนี้แสดงรายการใบงานแบบแบ่งหน้า พร้อมตัวกรองตามสถานะและช่วงวันที่
        This function lists jobs with pagination, filtering by status and date range.
        Rate Limit: อนุญาต 50 ครั้งต่อ 1 นาที / Allows 50 requests per minute.
    */
    @GetMapping
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    @Operation(summary = "List jobs with pagination")
    public ResponseEntity<Page<JobResponseDTO>> listJobs(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            Pageable pageable) throws SystemGlobalException {
        return ResponseEntity.ok(jobService.listJobs(status, startDate, endDate, pageable));
    }

    // ========================================================================
    // 4. UPDATE JOB
    // ========================================================================

    /*
        API: PUT /api/v1/jobs/{id}
        ฟังก์ชันนี้แก้ไขข้อมูลใบงาน (ยกเว้นสถานะ) เช่น อาการ, หมายเหตุ, ระยะทาง
        This function updates job details (except status) such as symptoms, notes, mileage.
        Rate Limit: อนุญาต 20 ครั้งต่อ 1 นาที / Allows 20 requests per minute.
    */
    @PutMapping("/{id}")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Update job details")
    public ResponseEntity<JobResponseDTO> updateJob(@PathVariable UUID id,
                                                    @Valid @RequestBody JobUpdateRequestDTO request)
            throws SystemGlobalException {
        return ResponseEntity.ok(jobService.updateJob(id, request));
    }

    // ========================================================================
    // 5. CHANGE JOB STATUS
    // ========================================================================

    /*
        API: PUT /api/v1/jobs/{id}/status
        ฟังก์ชันนี้เปลี่ยนสถานะใบงาน และบันทึกประวัติการเปลี่ยนแปลง
        This function changes the job status and records the change history.
        Rate Limit: อนุญาต 60 ครั้งต่อ 1 นาที / Allows 60 requests per minute.
    */
    @PutMapping("/{id}/status")
    @RateLimit(limit = 60, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Change job status")
    public ResponseEntity<JobResponseDTO> changeStatus(@PathVariable UUID id,
                                                       @Valid @RequestBody JobStatusChangeRequestDTO request)
            throws SystemGlobalException {
        return ResponseEntity.ok(jobService.changeStatus(id, request));
    }

    // ========================================================================
    // 6. DELETE JOB (SOFT DELETE)
    // ========================================================================

    /*
        API: DELETE /api/v1/jobs/{id}
        ฟังก์ชันนี้ลบใบงานแบบ Soft Delete
        This function soft-deletes the job.
        Rate Limit: อนุญาต 10 ครั้งต่อ 1 ชั่วโมง / Allows 10 requests per 1 hour.
    */
    @DeleteMapping("/{id}")
    @RateLimit(limit = 10, duration = 3600, keyType = "USER_ID")
    @Operation(summary = "Delete job (soft delete)")
    public ResponseEntity<Void> deleteJob(@PathVariable UUID id) throws SystemGlobalException {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }

    // ========================================================================
    // 7. GENERATE JOB REPORT (PDF)
    // ========================================================================

    /*
        API: GET /api/v1/jobs/report/{id}
        ฟังก์ชันนี้สร้าง PDF รายละเอียดใบงาน (ใช้ JasperReports) สำหรับพิมพ์/ส่งให้ลูกค้า
        This function generates a PDF job report (using JasperReports) for printing or customer delivery.
        Rate Limit: อนุญาต 20 ครั้งต่อ 5 นาที / Allows 20 requests per 5 minutes.
    */
    @GetMapping("/report/{id}")
    @RateLimit(limit = 20, duration = 300, keyType = "USER_ID")
    @Operation(summary = "Generate job report PDF")
    public ResponseEntity<byte[]> generateJobReport(@PathVariable UUID id) throws SystemGlobalException {
        byte[] pdf = jobService.generateJobReport(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=job_" + id + ".pdf")
                .body(pdf);
    }

    // ========================================================================
    // 8. GET JOB STATUS HISTORY
    // ========================================================================

    /*
        API: GET /api/v1/jobs/{id}/history
        ฟังก์ชันนี้ดึงประวัติการเปลี่ยนสถานะของใบงาน
        This function retrieves the status change history of a job.
        Rate Limit: อนุญาต 50 ครั้งต่อ 1 นาที / Allows 50 requests per minute.
    */
    @GetMapping("/{id}/history")
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Get job status history")
    public ResponseEntity<List<JobStatusHistoryDTO>> getStatusHistory(@PathVariable UUID id)
            throws SystemGlobalException {
        return ResponseEntity.ok(jobService.getStatusHistory(id));
    }

    // ========================================================================
    // 9. ADD SERVICE ITEM TO JOB
    // ========================================================================

    /*
        API: POST /api/v1/jobs/{id}/services
        ฟังก์ชันนี้เพิ่มรายการบริการให้กับใบงาน (เช่น เปลี่ยนถ่ายน้ำมัน, เช็คระบบเบรก)
        This function adds a service item to the job (e.g., oil change, brake system check).
        Rate Limit: อนุญาต 30 ครั้งต่อ 1 นาที / Allows 30 requests per minute.
    */
    @PostMapping("/{id}/services")
    @RateLimit(limit = 30, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Add service to job")
    public ResponseEntity<JobServiceResponseDTO> addService(@PathVariable UUID id,
                                                            @Valid @RequestBody JobServiceRequestDTO request)
            throws SystemGlobalException {
        return ResponseEntity.ok(jobService.addService(id, request));
    }

    // ========================================================================
    // 10. ADD PART TO JOB
    // ========================================================================

    /*
        API: POST /api/v1/jobs/{id}/parts
        ฟังก์ชันนี้เพิ่มอะไหล่ที่ใช้ในงานซ่อมให้กับใบงาน (เชื่อมโยงไปยัง Inventory)
        This function adds parts used in the repair to the job (links to Inventory).
        Rate Limit: อนุญาต 30 ครั้งต่อ 1 นาที / Allows 30 requests per minute.
    */
    @PostMapping("/{id}/parts")
    @RateLimit(limit = 30, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Add part to job")
    public ResponseEntity<JobPartResponseDTO> addPart(@PathVariable UUID id,
                                                      @Valid @RequestBody JobPartRequestDTO request)
            throws SystemGlobalException {
        return ResponseEntity.ok(jobService.addPart(id, request));
    }
}
