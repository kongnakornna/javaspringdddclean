package com.icmon.module.quotation.application.usecase;

import com.icmon.module.quotation.application.interfaces.QuotationServiceItemService;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RemoveQuotationServiceUseCase {
    private final QuotationServiceItemService quotationServiceItemService;
    public void execute(UUID id) throws SystemGlobalException {
        quotationServiceItemService.removeService(id);
    }
}
