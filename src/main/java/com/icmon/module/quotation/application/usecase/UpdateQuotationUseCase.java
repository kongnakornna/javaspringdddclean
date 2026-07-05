package com.icmon.module.quotation.application.usecase;

import com.icmon.module.quotation.application.interfaces.QuotationService;
import com.icmon.module.quotation.presentation.dto.request.QuotationUpdateRequestDTO;
import com.icmon.module.quotation.presentation.dto.response.QuotationResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateQuotationUseCase {
    private final QuotationService quotationService;
    public QuotationResponseDTO execute(UUID id, QuotationUpdateRequestDTO request) throws SystemGlobalException {
        return quotationService.updateQuotation(id, request);
    }
}
