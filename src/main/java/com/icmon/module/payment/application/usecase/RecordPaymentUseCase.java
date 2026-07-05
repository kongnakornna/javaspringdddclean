package com.icmon.module.payment.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.payment.application.interfaces.PaymentService;
import com.icmon.module.payment.presentation.dto.request.PaymentRecordRequestDTO;
import com.icmon.module.payment.presentation.dto.response.PaymentResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class RecordPaymentUseCase {

    private final PaymentService paymentService;

    public RecordPaymentUseCase(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public PaymentResponseDTO execute(PaymentRecordRequestDTO request) throws SystemGlobalException {
        return paymentService.recordPayment(request);
    }
}
