package com.icmon.module.quotation.application.interfaces;

import com.icmon.module.quotation.presentation.dto.request.QuotationPartRequestDTO;
import com.icmon.module.quotation.presentation.dto.response.QuotationPartResponseDTO;
import com.icmon.exception.SystemGlobalException;

import java.util.List;
import java.util.UUID;

public interface QuotationPartService {
    QuotationPartResponseDTO addPart(QuotationPartRequestDTO request) throws SystemGlobalException;
    QuotationPartResponseDTO updatePart(UUID id, QuotationPartRequestDTO request) throws SystemGlobalException;
    void removePart(UUID id) throws SystemGlobalException;
    List<QuotationPartResponseDTO> getPartsByQuotation(UUID quotationId) throws SystemGlobalException;
}
