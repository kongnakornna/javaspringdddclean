package com.icmon.module.quotation.application.usecase;

import com.icmon.module.quotation.application.interfaces.QuotationPartService;
import com.icmon.module.quotation.presentation.dto.response.QuotationPartResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetQuotationPartUseCase {
    private final QuotationPartService quotationPartService;
    public List<QuotationPartResponseDTO> execute(UUID quotationId) throws SystemGlobalException {
        return quotationPartService.getPartsByQuotation(quotationId);
    }
}
