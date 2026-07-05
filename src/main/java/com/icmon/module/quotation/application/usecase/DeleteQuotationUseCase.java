package com.icmon.module.quotation.application.usecase;

import com.icmon.module.quotation.application.interfaces.QuotationService;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteQuotationUseCase {
    private final QuotationService quotationService;
    public void execute(UUID id) throws SystemGlobalException {
        quotationService.deleteQuotation(id);
    }
}
