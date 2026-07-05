package com.icmon.module.quotation.application.usecase;

import com.icmon.module.quotation.application.interfaces.QuotationService;
import com.icmon.module.quotation.presentation.dto.response.QuotationResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ListQuotationsUseCase {
    private final QuotationService quotationService;
    public Page<QuotationResponseDTO> execute(String status, String startDate, String endDate, UUID customerId, Pageable pageable) throws SystemGlobalException {
        return quotationService.listQuotations(status, startDate, endDate, customerId, pageable);
    }
}
