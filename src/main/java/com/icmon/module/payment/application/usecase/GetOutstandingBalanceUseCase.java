package com.icmon.module.payment.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.payment.application.interfaces.PaymentService;
import com.icmon.module.payment.presentation.dto.response.OutstandingBalanceResponseDTO;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GetOutstandingBalanceUseCase {

    private final PaymentService paymentService;

    public GetOutstandingBalanceUseCase(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public OutstandingBalanceResponseDTO execute(UUID customerId) throws SystemGlobalException {
        return paymentService.getOutstandingBalance(customerId);
    }
}
