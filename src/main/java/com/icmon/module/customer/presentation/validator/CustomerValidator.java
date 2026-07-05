package com.icmon.module.customer.presentation.validator;

import com.icmon.module.customer.presentation.dto.request.CustomerCreateRequestDTO;
import com.icmon.module.customer.presentation.dto.request.CustomerUpdateRequestDTO;
import com.icmon.exception.models.FailedRequestException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerValidator {

    public void validateCreate(CustomerCreateRequestDTO request) throws FailedRequestException {
        if (request.getPhoneNumber() == null || request.getPhoneNumber().isBlank()) {
            throw new FailedRequestException("Phone number is required", null);
        }
        if (request.getFullName() == null || request.getFullName().isBlank()) {
            throw new FailedRequestException("Full name is required", null);
        }
    }

    public void validateUpdate(UUID id, CustomerUpdateRequestDTO request) throws FailedRequestException {
        if (id == null) {
            throw new FailedRequestException("Customer ID is required", null);
        }
    }
}
