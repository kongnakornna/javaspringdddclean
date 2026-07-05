package com.icmon.module.payment.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.payment.application.interfaces.PaymentService;
import com.icmon.module.payment.presentation.dto.response.PaymentResponseDTO;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CancelPaymentUseCase {

    private final PaymentService paymentService;

    public CancelPaymentUseCase(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public PaymentResponseDTO execute(UUID id, String reason) throws SystemGlobalException {
        return paymentService.cancelPayment(id, reason);
    }
}
