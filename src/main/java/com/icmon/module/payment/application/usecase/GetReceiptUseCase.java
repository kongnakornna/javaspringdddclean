package com.icmon.module.payment.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.payment.application.interfaces.ReceiptService;
import com.icmon.module.payment.presentation.dto.response.ReceiptResponseDTO;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GetReceiptUseCase {

    private final ReceiptService receiptService;

    public GetReceiptUseCase(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    public ReceiptResponseDTO execute(UUID id) throws SystemGlobalException {
        return receiptService.getReceipt(id);
    }
}
