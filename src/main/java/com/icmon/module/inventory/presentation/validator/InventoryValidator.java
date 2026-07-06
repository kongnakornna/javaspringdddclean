package com.icmon.module.inventory.presentation.validator;

import com.icmon.module.inventory.presentation.dto.request.InventoryReceiveRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class InventoryValidator {
    public void validateReceive(InventoryReceiveRequestDTO request) {
        if (request.getPartId() == null) {
            throw new IllegalArgumentException("Part ID is required");
        }
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (request.getUnitCost() == null || request.getUnitCost().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Unit cost must be positive");
        }
    }
}
