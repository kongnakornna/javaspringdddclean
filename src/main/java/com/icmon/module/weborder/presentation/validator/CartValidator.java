package com.icmon.module.weborder.presentation.validator;

import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.weborder.presentation.dto.request.AddToCartRequestDTO;
import com.icmon.module.weborder.presentation.dto.request.UpdateCartRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class CartValidator {

    public void validateAddToCart(AddToCartRequestDTO request) throws FailedRequestException {
        if (request.getItemId() == null) {
            throw new FailedRequestException("Item ID is required.", null);
        }
        if (request.getQuantity() == null || request.getQuantity() < 1) {
            throw new FailedRequestException("Quantity must be at least 1.", null);
        }
    }

    public void validateUpdateCart(UpdateCartRequestDTO request) throws FailedRequestException {
        if (request.getItemId() == null) {
            throw new FailedRequestException("Item ID is required.", null);
        }
        if (request.getQuantity() == null || request.getQuantity() < 0) {
            throw new FailedRequestException("Quantity must be 0 or more.", null);
        }
    }
}
