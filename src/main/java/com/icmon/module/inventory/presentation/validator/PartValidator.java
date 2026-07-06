package com.icmon.module.inventory.presentation.validator;

import com.icmon.module.inventory.presentation.dto.request.PartCreateRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class PartValidator {
    public void validateCreate(PartCreateRequestDTO request) {
        if (request.getPartCode() == null || request.getPartCode().isBlank()) {
            throw new IllegalArgumentException("Part code is required");
        }
        if (request.getPartName() == null || request.getPartName().isBlank()) {
            throw new IllegalArgumentException("Part name is required");
        }
    }
}
