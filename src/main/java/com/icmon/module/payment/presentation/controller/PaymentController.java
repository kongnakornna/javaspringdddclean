package com.icmon.module.payment.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.payment.application.usecase.*;
import com.icmon.module.payment.presentation.dto.request.PaymentRecordRequestDTO;
import com.icmon.module.payment.presentation.dto.request.PaymentSearchRequestDTO;
import com.icmon.module.payment.presentation.dto.request.RefundRequestDTO;
import com.icmon.module.payment.presentation.dto.response.OutstandingBalanceResponseDTO;
import com.icmon.module.payment.presentation.dto.response.PaymentHistoryResponseDTO;
import com.icmon.module.payment.presentation.dto.response.PaymentResponseDTO;
import com.icmon.module.payment.presentation.validator.PaymentValidator;
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
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Tag(name = "Payment Management", description = "APIs for managing payments and receipts")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE})
public class PaymentController {

    private final RecordPaymentUseCase recordPaymentUseCase;
    private final GetPaymentUseCase getPaymentUseCase;
    private final SearchPaymentsUseCase searchPaymentsUseCase;
    private final ProcessRefundUseCase processRefundUseCase;
    private final CancelPaymentUseCase cancelPaymentUseCase;
    private final GetOutstandingBalanceUseCase getOutstandingBalanceUseCase;
    private final PaymentValidator paymentValidator;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "บันทึกการชำระเงิน / Record payment")
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    public ResponseEntity<PaymentResponseDTO> recordPayment(@Valid @RequestBody PaymentRecordRequestDTO request) throws SystemGlobalException {
        paymentValidator.validateRecordPayment(request);
        PaymentResponseDTO result = recordPaymentUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "ดึงข้อมูลการชำระเงิน / Get payment by ID")
    @RateLimit(limit = 100, duration = 60, keyType = "USER_ID")
    public ResponseEntity<PaymentResponseDTO> getPayment(@PathVariable UUID id) throws SystemGlobalException {
        PaymentResponseDTO result = getPaymentUseCase.execute(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/by-invoice/{invoiceId}")
    @Operation(summary = "ดึงข้อมูลการชำระเงินตาม Invoice / Get payment by invoice ID")
    @RateLimit(limit = 100, duration = 60, keyType = "USER_ID")
    public ResponseEntity<PaymentResponseDTO> getPaymentByInvoice(@PathVariable UUID invoiceId) throws SystemGlobalException {
        PaymentResponseDTO result = getPaymentUseCase.execute(invoiceId);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    @Operation(summary = "ค้นหาการชำระเงิน / Search payments")
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    public ResponseEntity<Page<PaymentResponseDTO>> searchPayments(
            @RequestParam(required = false) UUID customerId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) throws SystemGlobalException {
        PaymentSearchRequestDTO request = new PaymentSearchRequestDTO();
        request.setCustomerId(customerId);
        request.setStatus(status);
        request.setStartDate(startDate != null ? java.time.LocalDateTime.parse(startDate) : null);
        request.setEndDate(endDate != null ? java.time.LocalDateTime.parse(endDate) : null);
        Page<PaymentResponseDTO> result = searchPaymentsUseCase.execute(request, PageRequest.of(page, size));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/outstanding/{customerId}")
    @Operation(summary = "ดึงยอดค้างชำระ / Get outstanding balance")
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    public ResponseEntity<OutstandingBalanceResponseDTO> getOutstandingBalance(@PathVariable UUID customerId) throws SystemGlobalException {
        OutstandingBalanceResponseDTO result = getOutstandingBalanceUseCase.execute(customerId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/history/{customerId}")
    @Operation(summary = "ดึงประวัติการชำระเงิน / Get payment history")
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    public ResponseEntity<List<PaymentHistoryResponseDTO>> getPaymentHistory(@PathVariable UUID customerId) throws SystemGlobalException {
        List<PaymentHistoryResponseDTO> result = getPaymentUseCase.execute(customerId) != null ? null : List.of();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/refund")
    @Operation(summary = "ดำเนินการคืนเงิน / Process refund")
    @RateLimit(limit = 30, duration = 60, keyType = "USER_ID")
    public ResponseEntity<PaymentResponseDTO> processRefund(@PathVariable UUID id, @Valid @RequestBody RefundRequestDTO request) throws SystemGlobalException {
        PaymentResponseDTO result = processRefundUseCase.execute(id, request);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "ยกเลิกการชำระเงิน / Cancel payment")
    @RateLimit(limit = 30, duration = 60, keyType = "USER_ID")
    public ResponseEntity<PaymentResponseDTO> cancelPayment(@PathVariable UUID id, @RequestParam String reason) throws SystemGlobalException {
        PaymentResponseDTO result = cancelPaymentUseCase.execute(id, reason);
        return ResponseEntity.ok(result);
    }
}
