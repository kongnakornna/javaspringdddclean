package com.icmon.module.payment.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.payment.application.usecase.GenerateReceiptPDFUseCase;
import com.icmon.module.payment.application.usecase.GetReceiptUseCase;
import com.icmon.module.payment.presentation.dto.response.ReceiptResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/receipts")
@RequiredArgsConstructor
@Tag(name = "Receipt Management", description = "APIs for managing receipts")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
public class ReceiptController {

    private final GetReceiptUseCase getReceiptUseCase;
    private final GenerateReceiptPDFUseCase generateReceiptPDFUseCase;

    @GetMapping("/{id}")
    @Operation(summary = "ดึงใบเสร็จ / Get receipt by ID")
    @RateLimit(limit = 100, duration = 60, keyType = "USER_ID")
    public ResponseEntity<ReceiptResponseDTO> getReceipt(@PathVariable UUID id) throws SystemGlobalException {
        ReceiptResponseDTO result = getReceiptUseCase.execute(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/by-payment/{paymentId}")
    @Operation(summary = "ดึงใบเสร็จตาม Payment / Get receipt by payment ID")
    @RateLimit(limit = 100, duration = 60, keyType = "USER_ID")
    public ResponseEntity<ReceiptResponseDTO> getReceiptByPayment(@PathVariable UUID paymentId) throws SystemGlobalException {
        ReceiptResponseDTO result = getReceiptUseCase.execute(paymentId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/pdf")
    @Operation(summary = "ดาวน์โหลดใบเสร็จ PDF / Download receipt PDF")
    @RateLimit(limit = 30, duration = 60, keyType = "USER_ID")
    public ResponseEntity<byte[]> downloadReceiptPDF(@PathVariable UUID id) throws SystemGlobalException {
        byte[] pdf = generateReceiptPDFUseCase.execute(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "receipt-" + id + ".pdf");
        return ResponseEntity.ok().headers(headers).body(pdf);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "ยกเลิกใบเสร็จ / Cancel receipt")
    @RateLimit(limit = 30, duration = 60, keyType = "USER_ID")
    public ResponseEntity<Void> cancelReceipt(@PathVariable UUID id, @RequestParam String reason) throws SystemGlobalException {
        return ResponseEntity.noContent().build();
    }
}
