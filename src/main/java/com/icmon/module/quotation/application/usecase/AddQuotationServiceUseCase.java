package com.icmon.module.quotation.application.usecase;

import com.icmon.module.quotation.application.interfaces.QuotationServiceItemService;
import com.icmon.module.quotation.presentation.dto.request.QuotationServiceRequestDTO;
import com.icmon.module.quotation.presentation.dto.response.QuotationServiceResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddQuotationServiceUseCase {
    private final QuotationServiceItemService quotationServiceItemService;
    public QuotationServiceResponseDTO execute(QuotationServiceRequestDTO request) throws SystemGlobalException {
        return quotationServiceItemService.addService(request);
    }
}
