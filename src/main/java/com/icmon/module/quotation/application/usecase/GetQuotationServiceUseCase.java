package com.icmon.module.quotation.application.usecase;

import com.icmon.module.quotation.application.interfaces.QuotationServiceItemService;
import com.icmon.module.quotation.presentation.dto.response.QuotationServiceResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetQuotationServiceUseCase {
    private final QuotationServiceItemService quotationServiceItemService;
    public List<QuotationServiceResponseDTO> execute(UUID quotationId) throws SystemGlobalException {
        return quotationServiceItemService.getServicesByQuotation(quotationId);
    }
}
