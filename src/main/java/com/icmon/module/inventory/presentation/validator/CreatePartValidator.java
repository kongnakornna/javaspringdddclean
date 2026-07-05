package com.icmon.module.inventory.presentation.validator;

import com.icmon.module.inventory.presentation.dto.request.PartMasterCreateRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class CreatePartValidator {

    public void validate(PartMasterCreateRequestDTO request) {
        if (request.getPartCode() == null || request.getPartCode().isBlank()) {
            throw new IllegalArgumentException("Part code is required");
        }
        if (request.getPartName() == null || request.getPartName().isBlank()) {
            throw new IllegalArgumentException("Part name is required");
        }
        if (request.getPartCode().length() > 50) {
            throw new IllegalArgumentException("Part code must not exceed 50 characters");
        }
        if (request.getUnit() != null && request.getUnit().length() > 20) {
            throw new IllegalArgumentException("Unit must not exceed 20 characters");
        }
    }
}
