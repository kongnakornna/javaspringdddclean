package com.icmon.module.payment.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.payment.application.interfaces.ReceiptService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GenerateReceiptPDFUseCase {

    private final ReceiptService receiptService;

    public GenerateReceiptPDFUseCase(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    public byte[] execute(UUID id) throws SystemGlobalException {
        return receiptService.generateReceiptPDF(id);
    }
}
