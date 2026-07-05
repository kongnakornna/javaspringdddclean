package com.icmon.module.quotation.application.interfaces;

import com.icmon.module.quotation.presentation.dto.request.QuotationServiceRequestDTO;
import com.icmon.module.quotation.presentation.dto.response.QuotationServiceResponseDTO;
import com.icmon.exception.SystemGlobalException;

import java.util.List;
import java.util.UUID;

public interface QuotationServiceItemService {
    QuotationServiceResponseDTO addService(QuotationServiceRequestDTO request) throws SystemGlobalException;
    QuotationServiceResponseDTO updateService(UUID id, QuotationServiceRequestDTO request) throws SystemGlobalException;
    void removeService(UUID id) throws SystemGlobalException;
    List<QuotationServiceResponseDTO> getServicesByQuotation(UUID quotationId) throws SystemGlobalException;
}
