package com.icmon.module.payment.application.impl;

import com.icmon._shared.application.GenericAuthDomainServiceImpl;
import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.payment.application.interfaces.ReceiptService;
import com.icmon.module.payment.infrastructure.cache.ReceiptCacheService;
import com.icmon.module.payment.infrastructure.entity.ReceiptEntity;
import com.icmon.module.payment.infrastructure.report.ReceiptReportGenerator;
import com.icmon.module.payment.infrastructure.repository.ReceiptRepository;
import com.icmon.module.payment.presentation.dto.response.ReceiptResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ReceiptServiceImpl extends GenericAuthDomainServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final ReceiptCacheService receiptCacheService;
    private final ReceiptReportGenerator receiptReportGenerator;

    public ReceiptServiceImpl(ReceiptRepository receiptRepository,
                              ReceiptCacheService receiptCacheService,
                              ReceiptReportGenerator receiptReportGenerator) {
        this.receiptRepository = receiptRepository;
        this.receiptCacheService = receiptCacheService;
        this.receiptReportGenerator = receiptReportGenerator;
    }

    @Override
    public ReceiptResponseDTO getReceipt(UUID id) throws FailedRequestException {
        ReceiptEntity entity = receiptRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Receipt not found: " + id, null));
        return toResponse(entity);
    }

    @Override
    public ReceiptResponseDTO getReceiptByPaymentId(UUID paymentId) throws FailedRequestException {
        ReceiptEntity entity = receiptRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new FailedRequestException("Receipt not found for payment: " + paymentId, null));
        return toResponse(entity);
    }

    @Override
    public byte[] generateReceiptPDF(UUID id) throws FailedRequestException {
        ReceiptEntity entity = receiptRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Receipt not found: " + id, null));
        return receiptReportGenerator.generateReceiptPdf(entity);
    }

    @Override
    public void cancelReceipt(UUID id, String reason) throws FailedRequestException {
        ReceiptEntity entity = receiptRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Receipt not found: " + id, null));
        entity.setStatus("CANCELLED");
        entity.setNotes(reason);
        receiptRepository.save(entity);
        receiptCacheService.saveReceipt(entity);
    }

    private ReceiptResponseDTO toResponse(ReceiptEntity entity) {
        ReceiptResponseDTO dto = new ReceiptResponseDTO();
        dto.setId(entity.getId());
        dto.setReceiptNo(entity.getReceiptNo());
        dto.setPaymentId(entity.getPaymentId());
        dto.setInvoiceId(entity.getInvoiceId());
        dto.setCustomerId(entity.getCustomerId());
        dto.setReceiptDate(entity.getReceiptDate());
        dto.setReceiptType(entity.getReceiptType());
        dto.setAmount(entity.getAmount());
        dto.setAmountInWordsTh(entity.getAmountInWordsTh());
        dto.setAmountInWordsEn(entity.getAmountInWordsEn());
        dto.setCurrency(entity.getCurrency());
        dto.setStatus(entity.getStatus());
        dto.setNotes(entity.getNotes());
        dto.setIssuedBy(entity.getIssuedBy());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
