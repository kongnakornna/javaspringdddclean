package com.icmon.module.quotation.application.usecase;

import com.icmon.module.quotation.application.interfaces.QuotationPartService;
import com.icmon.module.quotation.presentation.dto.request.QuotationPartRequestDTO;
import com.icmon.module.quotation.presentation.dto.response.QuotationPartResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddQuotationPartUseCase {
    private final QuotationPartService quotationPartService;
    public QuotationPartResponseDTO execute(QuotationPartRequestDTO request) throws SystemGlobalException {
        return quotationPartService.addPart(request);
    }
}
