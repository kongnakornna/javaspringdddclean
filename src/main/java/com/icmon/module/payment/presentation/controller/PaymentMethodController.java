package com.icmon.module.payment.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.payment.application.usecase.ListPaymentMethodsUseCase;
import com.icmon.module.payment.presentation.dto.response.PaymentMethodResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment-methods")
@RequiredArgsConstructor
@Tag(name = "Payment Method", description = "APIs for managing payment methods")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
public class PaymentMethodController {

    private final ListPaymentMethodsUseCase listPaymentMethodsUseCase;

    @GetMapping
    @Operation(summary = "รายการวิธีการชำระเงิน / List active payment methods")
    @RateLimit(limit = 100, duration = 60, keyType = "USER_ID")
    public ResponseEntity<List<PaymentMethodResponseDTO>> listPaymentMethods() throws SystemGlobalException {
        List<PaymentMethodResponseDTO> result = listPaymentMethodsUseCase.execute();
        return ResponseEntity.ok(result);
    }
}
