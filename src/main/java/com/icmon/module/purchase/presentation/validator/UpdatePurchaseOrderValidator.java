package com.icmon.module.purchase.presentation.validator;

import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.purchase.presentation.dto.request.PurchaseOrderUpdateRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class UpdatePurchaseOrderValidator {

    public void validate(PurchaseOrderUpdateRequestDTO request) throws FailedRequestException {
        // Basic validation if needed
    }
}
