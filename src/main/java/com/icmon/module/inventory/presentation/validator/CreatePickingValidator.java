package com.icmon.module.inventory.presentation.validator;

import com.icmon.module.inventory.presentation.dto.request.PickingCreateRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class CreatePickingValidator {

    public void validate(PickingCreateRequestDTO request) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("At least one picking item is required");
        }
        if (request.getRequestedBy() == null) {
            throw new IllegalArgumentException("Requester is required");
        }
        for (PickingCreateRequestDTO.PickingItem item : request.getItems()) {
            if (item.getRequestedQuantity() <= 0) {
                throw new IllegalArgumentException("Requested quantity must be greater than 0");
            }
        }
    }
}
