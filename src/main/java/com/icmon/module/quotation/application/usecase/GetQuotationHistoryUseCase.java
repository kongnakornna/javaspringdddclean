package com.icmon.module.quotation.application.usecase;

import com.icmon.module.quotation.application.interfaces.QuotationService;
import com.icmon.module.quotation.presentation.dto.response.QuotationStatusHistoryDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetQuotationHistoryUseCase {
    private final QuotationService quotationService;
    public List<QuotationStatusHistoryDTO> execute(UUID id) throws SystemGlobalException {
        return quotationService.getQuotationHistory(id);
    }
}
