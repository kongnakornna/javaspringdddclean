package com.icmon.module.quotation.presentation.validator;

import com.icmon.module.quotation.presentation.dto.request.QuotationCreateRequestDTO;
import com.icmon.exception.models.FailedRequestException;
import org.springframework.stereotype.Component;

@Component
public class QuotationValidator {

    public void validateCreate(QuotationCreateRequestDTO request) throws FailedRequestException {
        if (request.getJobId() == null) {
            throw new FailedRequestException("Job ID is required", null);
        }
        if (request.getCustomerId() == null) {
            throw new FailedRequestException("Customer ID is required", null);
        }
    }
}
