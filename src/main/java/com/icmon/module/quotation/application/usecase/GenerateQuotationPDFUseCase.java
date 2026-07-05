package com.icmon.module.quotation.application.usecase;

import com.icmon.module.quotation.application.interfaces.QuotationService;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GenerateQuotationPDFUseCase {
    private final QuotationService quotationService;
    public byte[] execute(UUID id) throws SystemGlobalException {
        return quotationService.generateQuotationPDF(id);
    }
}
