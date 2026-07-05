package com.icmon.module.quotation.application.usecase;

import com.icmon.module.quotation.application.interfaces.QuotationService;
import com.icmon.module.quotation.presentation.dto.response.QuotationDetailResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetQuotationUseCase {
    private final QuotationService quotationService;
    public QuotationDetailResponseDTO execute(UUID id) throws SystemGlobalException {
        return quotationService.getQuotation(id);
    }
}
