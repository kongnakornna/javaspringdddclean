package com.icmon.module.quotation.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.quotation.application.usecase.*;
import com.icmon.module.quotation.presentation.dto.request.QuotationApproveRequestDTO;
import com.icmon.module.quotation.presentation.dto.request.QuotationCreateRequestDTO;
import com.icmon.module.quotation.presentation.dto.request.QuotationUpdateRequestDTO;
import com.icmon.module.quotation.presentation.dto.response.QuotationDetailResponseDTO;
import com.icmon.module.quotation.presentation.dto.response.QuotationResponseDTO;
import com.icmon.module.quotation.presentation.dto.response.QuotationStatusHistoryDTO;
import com.icmon.module.quotation.presentation.validator.QuotationValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/quotations")
@RequiredArgsConstructor
@Tag(name = "Quotation Management", description = "APIs for managing quotations")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class QuotationController {

    private final CreateQuotationUseCase createQuotationUseCase;
    private final UpdateQuotationUseCase updateQuotationUseCase;
    private final GetQuotationUseCase getQuotationUseCase;
    private final GetQuotationByJobIdUseCase getQuotationByJobIdUseCase;
    private final ListQuotationsUseCase listQuotationsUseCase;
    private final DeleteQuotationUseCase deleteQuotationUseCase;
    private final ApproveQuotationUseCase approveQuotationUseCase;
    private final RejectQuotationUseCase rejectQuotationUseCase;
    private final GenerateQuotationPDFUseCase generateQuotationPDFUseCase;
    private final GetQuotationHistoryUseCase getQuotationHistoryUseCase;
    private final QuotationValidator validator;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "สร้างใบเสนอราคาใหม่ / Create new quotation")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    public ResponseEntity<QuotationResponseDTO> createQuotation(@Valid @RequestBody QuotationCreateRequestDTO request) throws SystemGlobalException {
        validator.validateCreate(request);
        QuotationResponseDTO result = createQuotationUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "ดึงข้อมูลใบเสนอราคา / Get quotation by ID")
    @RateLimit(limit = 100, duration = 60, keyType = "USER_ID")
    public ResponseEntity<QuotationDetailResponseDTO> getQuotation(@PathVariable UUID id) throws SystemGlobalException {
        QuotationDetailResponseDTO result = getQuotationUseCase.execute(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/job/{jobId}")
    @Operation(summary = "ดึงใบเสนอราคาตาม Job ID / Get quotation by Job ID")
    @RateLimit(limit = 80, duration = 60, keyType = "USER_ID")
    public ResponseEntity<QuotationDetailResponseDTO> getQuotationByJobId(@PathVariable UUID jobId) throws SystemGlobalException {
        QuotationDetailResponseDTO result = getQuotationByJobIdUseCase.execute(jobId);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    @Operation(summary = "รายการใบเสนอราคา / List quotations with pagination")
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    public ResponseEntity<Page<QuotationResponseDTO>> listQuotations(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) UUID customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) throws SystemGlobalException {
        Page<QuotationResponseDTO> result = listQuotationsUseCase.execute(status, startDate, endDate, customerId, PageRequest.of(page, size));
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @Operation(summary = "แก้ไขใบเสนอราคา / Update quotation")
    @RateLimit(limit = 15, duration = 60, keyType = "USER_ID")
    public ResponseEntity<QuotationResponseDTO> updateQuotation(
            @PathVariable UUID id,
            @Valid @RequestBody QuotationUpdateRequestDTO request) throws SystemGlobalException {
        QuotationResponseDTO result = updateQuotationUseCase.execute(id, request);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "ลบใบเสนอราคา / Delete quotation (soft delete)")
    @RateLimit(limit = 10, duration = 3600, keyType = "USER_ID")
    public ResponseEntity<Void> deleteQuotation(@PathVariable UUID id) throws SystemGlobalException {
        deleteQuotationUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/approve")
    @Operation(summary = "อนุมัติใบเสนอราคา / Approve quotation")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    public ResponseEntity<QuotationResponseDTO> approveQuotation(
            @PathVariable UUID id,
            @Valid @RequestBody QuotationApproveRequestDTO request) throws SystemGlobalException {
        QuotationResponseDTO result = approveQuotationUseCase.execute(id, request);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}/reject")
    @Operation(summary = "ปฏิเสธใบเสนอราคา / Reject quotation")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    public ResponseEntity<QuotationResponseDTO> rejectQuotation(
            @PathVariable UUID id,
            @RequestParam String reason) throws SystemGlobalException {
        QuotationResponseDTO result = rejectQuotationUseCase.execute(id, reason);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/pdf")
    @Operation(summary = "สร้างไฟล์ PDF ใบเสนอราคา / Generate quotation PDF")
    @RateLimit(limit = 15, duration = 300, keyType = "USER_ID")
    public ResponseEntity<byte[]> generateQuotationPDF(@PathVariable UUID id) throws SystemGlobalException {
        byte[] pdf = generateQuotationPDFUseCase.execute(id);
        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "inline; filename=quotation_" + id + ".pdf")
                .body(pdf);
    }

    @GetMapping("/{id}/history")
    @Operation(summary = "ประวัติการเปลี่ยนสถานะ / Get quotation status history")
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    public ResponseEntity<List<QuotationStatusHistoryDTO>> getQuotationHistory(@PathVariable UUID id) throws SystemGlobalException {
        List<QuotationStatusHistoryDTO> history = getQuotationHistoryUseCase.execute(id);
        return ResponseEntity.ok(history);
    }
}
