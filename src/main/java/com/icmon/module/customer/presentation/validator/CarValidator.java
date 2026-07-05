package com.icmon.module.customer.presentation.validator;

import com.icmon.module.customer.presentation.dto.request.CarCreateRequestDTO;
import com.icmon.exception.models.FailedRequestException;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class CarValidator {

    public void validateCreate(CarCreateRequestDTO request) throws FailedRequestException {
        if (request.getLicensePlate() == null || request.getLicensePlate().isBlank()) {
            throw new FailedRequestException("License plate is required", null);
        }
        if (request.getCustomerId() == null) {
            throw new FailedRequestException("Customer ID is required", null);
        }
        if (request.getBrand() == null || request.getBrand().isBlank()) {
            throw new FailedRequestException("Brand is required", null);
        }
        if (request.getModel() == null || request.getModel().isBlank()) {
            throw new FailedRequestException("Model is required", null);
        }
    }
}
