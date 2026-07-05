package com.icmon.module.purchase.presentation.validator;

import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.purchase.presentation.dto.request.PurchaseOrderCreateRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class CreatePurchaseOrderValidator {

    public void validate(PurchaseOrderCreateRequestDTO request) throws FailedRequestException {
        if (request.getSupplierId() == null) {
            throw new FailedRequestException("รหัสซัพพลายเออร์ต้องระบุ | Supplier ID is required", null);
        }
    }
}
