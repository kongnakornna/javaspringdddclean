package com.icmon.module.payment.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.payment.application.interfaces.PaymentService;
import com.icmon.module.payment.presentation.dto.request.PaymentSearchRequestDTO;
import com.icmon.module.payment.presentation.dto.response.PaymentResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class SearchPaymentsUseCase {

    private final PaymentService paymentService;

    public SearchPaymentsUseCase(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public Page<PaymentResponseDTO> execute(PaymentSearchRequestDTO request, Pageable pageable) throws SystemGlobalException {
        return paymentService.searchPayments(request, pageable);
    }
}
