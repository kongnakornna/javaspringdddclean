package com.icmon.module.quotation.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.quotation.application.usecase.AddQuotationPartUseCase;
import com.icmon.module.quotation.application.usecase.UpdateQuotationPartUseCase;
import com.icmon.module.quotation.application.usecase.RemoveQuotationPartUseCase;
import com.icmon.module.quotation.application.usecase.GetQuotationPartUseCase;
import com.icmon.module.quotation.presentation.dto.request.QuotationPartRequestDTO;
import com.icmon.module.quotation.presentation.dto.response.QuotationPartResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/quotations/parts")
@RequiredArgsConstructor
@Tag(name = "Quotation Parts", description = "APIs for managing quotation parts")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class QuotationPartController {

    private final AddQuotationPartUseCase addQuotationPartUseCase;
    private final UpdateQuotationPartUseCase updateQuotationPartUseCase;
    private final RemoveQuotationPartUseCase removeQuotationPartUseCase;
    private final GetQuotationPartUseCase getQuotationPartUseCase;

    @PostMapping
    @Operation(summary = "เพิ่มอะไหล่ในใบเสนอราคา / Add part to quotation")
    @RateLimit(limit = 30, duration = 60, keyType = "USER_ID")
    public ResponseEntity<QuotationPartResponseDTO> addPart(@Valid @RequestBody QuotationPartRequestDTO request) throws SystemGlobalException {
        QuotationPartResponseDTO result = addQuotationPartUseCase.execute(request);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @Operation(summary = "แก้ไขรายการอะไหล่ / Update part in quotation")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    public ResponseEntity<QuotationPartResponseDTO> updatePart(
            @PathVariable UUID id,
            @Valid @RequestBody QuotationPartRequestDTO request) throws SystemGlobalException {
        QuotationPartResponseDTO result = updateQuotationPartUseCase.execute(id, request);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "ลบรายการอะไหล่ / Remove part from quotation")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    public ResponseEntity<Void> removePart(@PathVariable UUID id) throws SystemGlobalException {
        removeQuotationPartUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/quotation/{quotationId}")
    @Operation(summary = "รายการอะไหล่ในใบเสนอราคา / List all parts in quotation")
    @RateLimit(limit = 60, duration = 60, keyType = "USER_ID")
    public ResponseEntity<List<QuotationPartResponseDTO>> getPartsByQuotation(@PathVariable UUID quotationId) throws SystemGlobalException {
        List<QuotationPartResponseDTO> parts = getQuotationPartUseCase.execute(quotationId);
        return ResponseEntity.ok(parts);
    }
}
