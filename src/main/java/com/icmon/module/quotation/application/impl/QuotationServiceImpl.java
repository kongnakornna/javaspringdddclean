package com.icmon.module.quotation.application.impl;

import com.icmon._shared.application.GenericAuthDomainServiceImpl;
import com.icmon.module.quotation.application.interfaces.QuotationService;
import com.icmon.module.quotation.domain.TQuotation;
import com.icmon.module.quotation.domain.TQuotationStatusHistory;
import com.icmon.module.quotation.domain.enums.QuotationStatus;
import com.icmon.module.quotation.infrastructure.cache.QuotationCacheService;
import com.icmon.module.quotation.infrastructure.entity.QuotationEntity;
import com.icmon.module.quotation.infrastructure.entity.QuotationStatusHistoryEntity;
import com.icmon.module.quotation.infrastructure.mapper.QuotationMapper;
import com.icmon.module.quotation.infrastructure.repository.QuotationRepository;
import com.icmon.module.quotation.infrastructure.repository.QuotationStatusHistoryRepository;
import com.icmon.module.quotation.presentation.dto.request.QuotationApproveRequestDTO;
import com.icmon.module.quotation.presentation.dto.request.QuotationCreateRequestDTO;
import com.icmon.module.quotation.presentation.dto.request.QuotationUpdateRequestDTO;
import com.icmon.module.quotation.presentation.dto.response.QuotationDetailResponseDTO;
import com.icmon.module.quotation.presentation.dto.response.QuotationResponseDTO;
import com.icmon.module.quotation.presentation.dto.response.QuotationStatusHistoryDTO;
import com.icmon.exception.SystemGlobalException;
import com.icmon.exception.models.FailedRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuotationServiceImpl extends GenericAuthDomainServiceImpl implements QuotationService {

    private final QuotationRepository quotationRepository;
    private final QuotationStatusHistoryRepository statusHistoryRepository;
    private final QuotationMapper quotationMapper;
    private final QuotationCacheService cacheService;

    @Override
    @Transactional
    public QuotationResponseDTO createQuotation(QuotationCreateRequestDTO request) throws SystemGlobalException {
        QuotationEntity entity = new QuotationEntity();
        entity.setJobId(request.getJobId());
        entity.setCustomerId(request.getCustomerId());
        entity.setQuotationDate(LocalDateTime.now());
        entity.setExpiryDate(request.getExpiryDate() != null ? request.getExpiryDate() : LocalDateTime.now().plusDays(7));
        entity.setStatus(QuotationStatus.DRAFT);
        entity.setTaxRate(request.getTaxRate() != null ? request.getTaxRate() : new BigDecimal("7.00"));
        entity.setCurrency(request.getCurrency() != null ? request.getCurrency() : "THB");
        entity.setExchangeRate(request.getExchangeRate() != null ? request.getExchangeRate() : BigDecimal.ONE);
        entity.setNotes(request.getNotes());
        entity.setTermsAndConditions(request.getTermsAndConditions());
        entity.setSubtotal(BigDecimal.ZERO);
        entity.setTaxAmount(BigDecimal.ZERO);
        entity.setDiscountValue(BigDecimal.ZERO);
        entity.setTotal(BigDecimal.ZERO);
        entity.setConvertedToPo(false);
        entity.setUserId(getUserId());
        entity.setWhitelabelId(getWhitelabelId());

        QuotationEntity saved = quotationRepository.save(entity);
        TQuotation domain = quotationMapper.toDomain(saved);
        cacheService.saveQuotation(domain);
        return toResponseDTO(domain);
    }

    @Override
    @Transactional(readOnly = true)
    public QuotationDetailResponseDTO getQuotation(UUID id) throws SystemGlobalException {
        QuotationEntity entity = quotationRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Quotation not found with id: " + id, null));
        TQuotation domain = quotationMapper.toDomain(entity);
        return toDetailResponseDTO(domain);
    }

    @Override
    @Transactional(readOnly = true)
    public QuotationDetailResponseDTO getQuotationByJobId(UUID jobId) throws SystemGlobalException {
        QuotationEntity entity = quotationRepository.findTopByJobIdAndDeletedFalseOrderByCreatedAtDesc(jobId)
                .orElseThrow(() -> new FailedRequestException("Quotation not found for job id: " + jobId, null));
        TQuotation domain = quotationMapper.toDomain(entity);
        return toDetailResponseDTO(domain);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuotationResponseDTO> listQuotations(String status, String startDate, String endDate, UUID customerId, Pageable pageable) throws SystemGlobalException {
        Page<QuotationEntity> page = quotationRepository.searchQuotations(status, customerId, startDate, endDate, pageable);
        return page.map(e -> toResponseDTO(quotationMapper.toDomain(e)));
    }

    @Override
    @Transactional
    public QuotationResponseDTO updateQuotation(UUID id, QuotationUpdateRequestDTO request) throws SystemGlobalException {
        QuotationEntity entity = quotationRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Quotation not found with id: " + id, null));

        if (request.getExpiryDate() != null) entity.setExpiryDate(request.getExpiryDate());
        if (request.getTaxRate() != null) entity.setTaxRate(request.getTaxRate());
        if (request.getDiscountType() != null) entity.setDiscountType(request.getDiscountType());
        if (request.getDiscountValue() != null) entity.setDiscountValue(request.getDiscountValue());
        if (request.getCurrency() != null) entity.setCurrency(request.getCurrency());
        if (request.getExchangeRate() != null) entity.setExchangeRate(request.getExchangeRate());
        if (request.getNotes() != null) entity.setNotes(request.getNotes());
        if (request.getTermsAndConditions() != null) entity.setTermsAndConditions(request.getTermsAndConditions());

        QuotationEntity saved = quotationRepository.save(entity);
        TQuotation domain = quotationMapper.toDomain(saved);
        domain.calculateTotals();
        cacheService.saveQuotation(domain);
        return toResponseDTO(domain);
    }

    @Override
    @Transactional
    public void deleteQuotation(UUID id) throws SystemGlobalException {
        QuotationEntity entity = quotationRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Quotation not found with id: " + id, null));
        if (entity.getStatus() == QuotationStatus.APPROVED || entity.getStatus() == QuotationStatus.CONVERTED) {
            throw new FailedRequestException("Cannot delete approved or converted quotation.", null);
        }
        entity.setDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        quotationRepository.save(entity);
        cacheService.evictQuotation(id);
    }

    @Override
    @Transactional
    public QuotationResponseDTO approveQuotation(UUID id, QuotationApproveRequestDTO request) throws SystemGlobalException {
        QuotationEntity entity = quotationRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Quotation not found with id: " + id, null));

        if (entity.getStatus() != QuotationStatus.PENDING && entity.getStatus() != QuotationStatus.DRAFT) {
            throw new FailedRequestException("Quotation cannot be approved in status: " + entity.getStatus(), null);
        }

        QuotationStatus previousStatus = entity.getStatus();
        entity.setStatus(QuotationStatus.APPROVED);
        entity.setApprovedBy(getUserId());
        entity.setApprovedAt(LocalDateTime.now());

        if (request.getAutoCreatePo() != null && request.getAutoCreatePo()) {
            entity.setConvertedToPo(true);
        }

        QuotationEntity saved = quotationRepository.save(entity);
        recordStatusHistory(saved.getId(), previousStatus, QuotationStatus.APPROVED, "Approved by " + getUserId());

        TQuotation domain = quotationMapper.toDomain(saved);
        cacheService.evictQuotation(id);
        cacheService.saveQuotation(domain);
        return toResponseDTO(domain);
    }

    @Override
    @Transactional
    public QuotationResponseDTO rejectQuotation(UUID id, String reason) throws SystemGlobalException {
        QuotationEntity entity = quotationRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Quotation not found with id: " + id, null));

        if (entity.getStatus() == QuotationStatus.APPROVED || entity.getStatus() == QuotationStatus.CONVERTED) {
            throw new FailedRequestException("Cannot reject already approved or converted quotation.", null);
        }

        QuotationStatus previousStatus = entity.getStatus();
        entity.setStatus(QuotationStatus.REJECTED);
        entity.setRejectedReason(reason);

        QuotationEntity saved = quotationRepository.save(entity);
        recordStatusHistory(saved.getId(), previousStatus, QuotationStatus.REJECTED, reason);

        TQuotation domain = quotationMapper.toDomain(saved);
        cacheService.evictQuotation(id);
        return toResponseDTO(domain);
    }

    @Override
    public byte[] generateQuotationPDF(UUID id) throws SystemGlobalException {
        return new byte[0];
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuotationStatusHistoryDTO> getQuotationHistory(UUID id) throws SystemGlobalException {
        quotationRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Quotation not found with id: " + id, null));

        List<QuotationStatusHistoryEntity> history = statusHistoryRepository.findByQuotationIdOrderByChangedAtAsc(id);
        return history.stream()
                .map(h -> QuotationStatusHistoryDTO.builder()
                        .id(h.getId())
                        .quotationId(h.getQuotationId())
                        .fromStatus(h.getFromStatus())
                        .toStatus(h.getToStatus())
                        .changedBy(h.getChangedBy())
                        .changedAt(h.getChangedAt())
                        .reason(h.getReason())
                        .build())
                .collect(Collectors.toList());
    }

    private void recordStatusHistory(UUID quotationId, QuotationStatus fromStatus, QuotationStatus toStatus, String reason) throws SystemGlobalException {
        QuotationStatusHistoryEntity history = new QuotationStatusHistoryEntity();
        history.setQuotationId(quotationId);
        history.setFromStatus(fromStatus);
        history.setToStatus(toStatus);
        history.setChangedBy(getUserId());
        history.setChangedAt(LocalDateTime.now());
        history.setReason(reason);
        history.setWhitelabelId(getWhitelabelId());
        statusHistoryRepository.save(history);
    }

    private QuotationResponseDTO toResponseDTO(TQuotation domain) {
        return QuotationResponseDTO.builder()
                .id(domain.getId())
                .quotationNo(domain.getQuotationNo())
                .jobId(domain.getJobId())
                .customerId(domain.getCustomerId())
                .quotationDate(domain.getQuotationDate())
                .expiryDate(domain.getExpiryDate())
                .status(domain.getStatus())
                .subtotal(domain.getSubtotal())
                .taxRate(domain.getTaxRate())
                .taxAmount(domain.getTaxAmount())
                .discountType(domain.getDiscountType())
                .discountValue(domain.getDiscountValue())
                .total(domain.getTotal())
                .currency(domain.getCurrency())
                .notes(domain.getNotes())
                .createdAt(domain.getCreatedAt())
                .build();
    }

    private QuotationDetailResponseDTO toDetailResponseDTO(TQuotation domain) {
        return QuotationDetailResponseDTO.builder()
                .id(domain.getId())
                .quotationNo(domain.getQuotationNo())
                .jobId(domain.getJobId())
                .customerId(domain.getCustomerId())
                .quotationDate(domain.getQuotationDate())
                .expiryDate(domain.getExpiryDate())
                .status(domain.getStatus())
                .subtotal(domain.getSubtotal())
                .taxRate(domain.getTaxRate())
                .taxAmount(domain.getTaxAmount())
                .discountType(domain.getDiscountType())
                .discountValue(domain.getDiscountValue())
                .total(domain.getTotal())
                .amountInWordsTh(domain.getAmountInWordsTh())
                .amountInWordsEn(domain.getAmountInWordsEn())
                .currency(domain.getCurrency())
                .exchangeRate(domain.getExchangeRate())
                .notes(domain.getNotes())
                .termsAndConditions(domain.getTermsAndConditions())
                .approvedBy(domain.getApprovedBy())
                .approvedAt(domain.getApprovedAt())
                .rejectedReason(domain.getRejectedReason())
                .convertedToPo(domain.getConvertedToPo())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
