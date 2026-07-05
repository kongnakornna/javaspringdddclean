package com.icmon.module.quotation.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.quotation.application.usecase.AddQuotationServiceUseCase;
import com.icmon.module.quotation.application.usecase.GetQuotationServiceUseCase;
import com.icmon.module.quotation.application.usecase.RemoveQuotationServiceUseCase;
import com.icmon.module.quotation.application.usecase.UpdateQuotationServiceUseCase;
import com.icmon.module.quotation.presentation.dto.request.QuotationServiceRequestDTO;
import com.icmon.module.quotation.presentation.dto.response.QuotationServiceResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/quotations/services")
@RequiredArgsConstructor
@Tag(name = "Quotation Services", description = "APIs for managing quotation services")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class QuotationServiceController {

    private final AddQuotationServiceUseCase addQuotationServiceUseCase;
    private final UpdateQuotationServiceUseCase updateQuotationServiceUseCase;
    private final RemoveQuotationServiceUseCase removeQuotationServiceUseCase;
    private final GetQuotationServiceUseCase getQuotationServiceUseCase;

    @PostMapping
    @Operation(summary = "เพิ่มบริการในใบเสนอราคา / Add service to quotation")
    @RateLimit(limit = 30, duration = 60, keyType = "USER_ID")
    public ResponseEntity<QuotationServiceResponseDTO> addService(@Valid @RequestBody QuotationServiceRequestDTO request) throws SystemGlobalException {
        QuotationServiceResponseDTO result = addQuotationServiceUseCase.execute(request);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @Operation(summary = "แก้ไขรายการบริการ / Update service in quotation")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    public ResponseEntity<QuotationServiceResponseDTO> updateService(
            @PathVariable UUID id,
            @Valid @RequestBody QuotationServiceRequestDTO request) throws SystemGlobalException {
        QuotationServiceResponseDTO result = updateQuotationServiceUseCase.execute(id, request);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "ลบรายการบริการ / Remove service from quotation")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    public ResponseEntity<Void> removeService(@PathVariable UUID id) throws SystemGlobalException {
        removeQuotationServiceUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/quotation/{quotationId}")
    @Operation(summary = "รายการบริการในใบเสนอราคา / List all services in quotation")
    @RateLimit(limit = 60, duration = 60, keyType = "USER_ID")
    public ResponseEntity<List<QuotationServiceResponseDTO>> getServicesByQuotation(@PathVariable UUID quotationId) throws SystemGlobalException {
        List<QuotationServiceResponseDTO> services = getQuotationServiceUseCase.execute(quotationId);
        return ResponseEntity.ok(services);
    }
}
