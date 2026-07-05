package com.icmon.module.payment.presentation.validator;

import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.payment.presentation.dto.request.PaymentRecordRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class PaymentValidator {

    public void validateRecordPayment(PaymentRecordRequestDTO request) throws FailedRequestException {
        if (request.getInvoiceId() == null) {
            throw new FailedRequestException("Invoice ID is required", null);
        }
        if (request.getPaymentMethodId() == null) {
            throw new FailedRequestException("Payment method ID is required", null);
        }
        if (request.getAmount() == null || request.getAmount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new FailedRequestException("Amount must be greater than zero", null);
        }
    }
}
