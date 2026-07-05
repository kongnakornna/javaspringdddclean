package com.icmon.module.quotation.application.usecase;

import com.icmon.module.quotation.application.interfaces.QuotationService;
import com.icmon.module.quotation.presentation.dto.request.QuotationCreateRequestDTO;
import com.icmon.module.quotation.presentation.dto.response.QuotationResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateQuotationUseCase {
    private final QuotationService quotationService;
    public QuotationResponseDTO execute(QuotationCreateRequestDTO request) throws SystemGlobalException {
        return quotationService.createQuotation(request);
    }
}
