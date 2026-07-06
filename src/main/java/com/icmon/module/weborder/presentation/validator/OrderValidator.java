package com.icmon.module.weborder.presentation.validator;

import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.weborder.presentation.dto.request.OrderCreateRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class OrderValidator {

    public void validateCreateOrder(OrderCreateRequestDTO request) throws FailedRequestException {
        if (request.getCartId() == null || request.getCartId().isBlank()) {
            throw new FailedRequestException("Cart ID is required.", null);
        }
        if (request.getCustomerId() == null) {
            throw new FailedRequestException("Customer ID is required.", null);
        }
        if (request.getShippingAddress() == null || request.getShippingAddress().isBlank()) {
            throw new FailedRequestException("Shipping address is required.", null);
        }
    }
}
