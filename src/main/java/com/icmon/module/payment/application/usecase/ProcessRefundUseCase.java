package com.icmon.module.payment.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.payment.application.interfaces.PaymentService;
import com.icmon.module.payment.presentation.dto.request.RefundRequestDTO;
import com.icmon.module.payment.presentation.dto.response.PaymentResponseDTO;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProcessRefundUseCase {

    private final PaymentService paymentService;

    public ProcessRefundUseCase(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public PaymentResponseDTO execute(UUID id, RefundRequestDTO request) throws SystemGlobalException {
        return paymentService.processRefund(id, request);
    }
}
