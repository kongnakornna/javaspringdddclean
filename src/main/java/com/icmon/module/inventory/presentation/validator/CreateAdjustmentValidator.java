package com.icmon.module.inventory.presentation.validator;

import com.icmon.module.inventory.presentation.dto.request.AdjustmentCreateRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class CreateAdjustmentValidator {

    public void validate(AdjustmentCreateRequestDTO request) {
        if (request.getAdjustmentType() == null || request.getAdjustmentType().isBlank()) {
            throw new IllegalArgumentException("Adjustment type is required");
        }
        if (request.getReason() == null || request.getReason().isBlank()) {
            throw new IllegalArgumentException("Adjustment reason is required");
        }
        if (request.getDetails() == null || request.getDetails().isEmpty()) {
            throw new IllegalArgumentException("At least one adjustment detail is required");
        }
    }
}
