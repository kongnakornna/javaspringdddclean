package com.icmon.module.quotation.application.usecase;

import com.icmon.module.quotation.application.interfaces.QuotationPartService;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RemoveQuotationPartUseCase {
    private final QuotationPartService quotationPartService;
    public void execute(UUID id) throws SystemGlobalException {
        quotationPartService.removePart(id);
    }
}
