package com.icmon.module.quotation.application.usecase;

import com.icmon.module.quotation.application.interfaces.QuotationService;
import com.icmon.module.quotation.presentation.dto.response.QuotationResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RejectQuotationUseCase {
    private final QuotationService quotationService;
    public QuotationResponseDTO execute(UUID id, String reason) throws SystemGlobalException {
        return quotationService.rejectQuotation(id, reason);
    }
}
