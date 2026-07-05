package com.icmon.module.quotation.presentation.validator;

import com.icmon.exception.models.FailedRequestException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class QuotationCalculationValidator {

    public void validateDiscount(String discountType, BigDecimal discountValue) throws FailedRequestException {
        if (discountType != null && discountValue != null) {
            if (!"PERCENTAGE".equalsIgnoreCase(discountType) && !"FIXED".equalsIgnoreCase(discountType)) {
                throw new FailedRequestException("Discount type must be PERCENTAGE or FIXED", null);
            }
            if ("PERCENTAGE".equalsIgnoreCase(discountType) && (discountValue.compareTo(BigDecimal.ZERO) < 0 || discountValue.compareTo(new BigDecimal("100")) > 0)) {
                throw new FailedRequestException("Percentage discount must be between 0 and 100", null);
            }
            if ("FIXED".equalsIgnoreCase(discountType) && discountValue.compareTo(BigDecimal.ZERO) < 0) {
                throw new FailedRequestException("Fixed discount cannot be negative", null);
            }
        }
    }
}
