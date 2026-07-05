package com.icmon.module.payment.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.payment.application.interfaces.PaymentMethodService;
import com.icmon.module.payment.presentation.dto.response.PaymentMethodResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListPaymentMethodsUseCase {

    private final PaymentMethodService paymentMethodService;

    public ListPaymentMethodsUseCase(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    public List<PaymentMethodResponseDTO> execute() throws SystemGlobalException {
        return paymentMethodService.getAllActiveMethods();
    }
}
