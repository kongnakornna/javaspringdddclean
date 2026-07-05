package com.icmon.module.payment.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.payment.application.interfaces.PaymentService;
import com.icmon.module.payment.presentation.dto.response.PaymentResponseDTO;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GetPaymentUseCase {

    private final PaymentService paymentService;

    public GetPaymentUseCase(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public PaymentResponseDTO execute(UUID id) throws SystemGlobalException {
        return paymentService.getPayment(id);
    }
}
