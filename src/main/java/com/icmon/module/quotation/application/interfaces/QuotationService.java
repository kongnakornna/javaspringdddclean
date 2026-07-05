package com.icmon.module.quotation.application.interfaces;

import com.icmon.module.quotation.presentation.dto.request.QuotationApproveRequestDTO;
import com.icmon.module.quotation.presentation.dto.request.QuotationCreateRequestDTO;
import com.icmon.module.quotation.presentation.dto.request.QuotationUpdateRequestDTO;
import com.icmon.module.quotation.presentation.dto.response.QuotationDetailResponseDTO;
import com.icmon.module.quotation.presentation.dto.response.QuotationResponseDTO;
import com.icmon.module.quotation.presentation.dto.response.QuotationStatusHistoryDTO;
import com.icmon.exception.SystemGlobalException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface QuotationService {
    QuotationResponseDTO createQuotation(QuotationCreateRequestDTO request) throws SystemGlobalException;
    QuotationDetailResponseDTO getQuotation(UUID id) throws SystemGlobalException;
    QuotationDetailResponseDTO getQuotationByJobId(UUID jobId) throws SystemGlobalException;
    Page<QuotationResponseDTO> listQuotations(String status, String startDate, String endDate, UUID customerId, Pageable pageable) throws SystemGlobalException;
    QuotationResponseDTO updateQuotation(UUID id, QuotationUpdateRequestDTO request) throws SystemGlobalException;
    void deleteQuotation(UUID id) throws SystemGlobalException;
    QuotationResponseDTO approveQuotation(UUID id, QuotationApproveRequestDTO request) throws SystemGlobalException;
    QuotationResponseDTO rejectQuotation(UUID id, String reason) throws SystemGlobalException;
    byte[] generateQuotationPDF(UUID id) throws SystemGlobalException;
    List<QuotationStatusHistoryDTO> getQuotationHistory(UUID id) throws SystemGlobalException;
}
