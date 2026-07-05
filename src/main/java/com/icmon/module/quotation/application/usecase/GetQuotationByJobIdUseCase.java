package com.icmon.module.quotation.application.usecase;

import com.icmon.module.quotation.application.interfaces.QuotationService;
import com.icmon.module.quotation.presentation.dto.response.QuotationDetailResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetQuotationByJobIdUseCase {
    private final QuotationService quotationService;
    public QuotationDetailResponseDTO execute(UUID jobId) throws SystemGlobalException {
        return quotationService.getQuotationByJobId(jobId);
    }
}
