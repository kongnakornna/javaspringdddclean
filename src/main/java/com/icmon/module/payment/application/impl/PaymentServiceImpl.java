package com.icmon.module.payment.application.impl;

import com.icmon._shared.application.GenericAuthDomainServiceImpl;
import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.payment.application.interfaces.PaymentService;
import com.icmon.module.payment.domain.TPayment;
import com.icmon.module.payment.domain.enums.PaymentStatus;
import com.icmon.module.payment.infrastructure.cache.PaymentCacheService;
import com.icmon.module.payment.infrastructure.entity.PaymentEntity;
import com.icmon.module.payment.infrastructure.mapper.PaymentMapper;
import com.icmon.module.payment.infrastructure.repository.PaymentHistoryRepository;
import com.icmon.module.payment.infrastructure.repository.PaymentMethodRepository;
import com.icmon.module.payment.infrastructure.repository.PaymentRepository;
import com.icmon.module.payment.infrastructure.repository.ReceiptRepository;
import com.icmon.module.payment.presentation.dto.request.PaymentRecordRequestDTO;
import com.icmon.module.payment.presentation.dto.request.PaymentSearchRequestDTO;
import com.icmon.module.payment.presentation.dto.request.RefundRequestDTO;
import com.icmon.module.payment.presentation.dto.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentServiceImpl extends GenericAuthDomainServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReceiptRepository receiptRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final PaymentMapper paymentMapper;
    private final PaymentCacheService paymentCacheService;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              ReceiptRepository receiptRepository,
                              PaymentMethodRepository paymentMethodRepository,
                              PaymentHistoryRepository paymentHistoryRepository,
                              PaymentMapper paymentMapper,
                              PaymentCacheService paymentCacheService) {
        this.paymentRepository = paymentRepository;
        this.receiptRepository = receiptRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.paymentHistoryRepository = paymentHistoryRepository;
        this.paymentMapper = paymentMapper;
        this.paymentCacheService = paymentCacheService;
    }

    @Override
    public PaymentResponseDTO recordPayment(PaymentRecordRequestDTO request) throws FailedRequestException {
        TPayment domain = new TPayment();
        domain.setPaymentNo(generatePaymentNo());
        domain.setInvoiceId(request.getInvoiceId());
        domain.setPaymentDate(LocalDateTime.now());
        domain.setPaymentMethodId(request.getPaymentMethodId());
        domain.setAmount(request.getAmount());
        domain.setAmountReceived(request.getAmountReceived());
        domain.setChangeAmount(request.getChangeAmount());
        domain.setCurrency(request.getCurrency() != null ? request.getCurrency() : "THB");
        domain.setExchangeRate(request.getExchangeRate() != null ? request.getExchangeRate() : java.math.BigDecimal.ONE);
        domain.setStatus(PaymentStatus.COMPLETED);
        domain.setReferenceNumber(request.getReferenceNumber());
        domain.setBankName(request.getBankName());
        domain.setChequeNumber(request.getChequeNumber());
        domain.setChequeBank(request.getChequeBank());
        domain.setChequeDate(request.getChequeDate());
        domain.setNotes(request.getNotes());
        domain.setReceivedBy(request.getReceivedBy());

        PaymentEntity entity = paymentMapper.toEntity(domain);
        entity = paymentRepository.save(entity);
        paymentCacheService.savePayment(entity);

        return toResponse(entity);
    }

    @Override
    public PaymentResponseDTO getPayment(UUID id) throws FailedRequestException {
        PaymentEntity entity = paymentRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Payment not found: " + id, null));
        return toResponse(entity);
    }

    @Override
    public PaymentResponseDTO getPaymentByInvoiceId(UUID invoiceId) throws FailedRequestException {
        PaymentEntity entity = paymentRepository.findByInvoiceId(invoiceId)
                .orElseThrow(() -> new FailedRequestException("Payment not found for invoice: " + invoiceId, null));
        return toResponse(entity);
    }

    @Override
    public Page<PaymentResponseDTO> searchPayments(PaymentSearchRequestDTO request, Pageable pageable) throws FailedRequestException {
        Page<PaymentEntity> entities = paymentRepository.searchPayments(
                request.getCustomerId(), request.getStatus(),
                request.getStartDate(), request.getEndDate(), pageable);
        return entities.map(this::toResponse);
    }

    @Override
    public OutstandingBalanceResponseDTO getOutstandingBalance(UUID customerId) throws FailedRequestException {
        List<PaymentEntity> payments = paymentRepository.findByCustomerId(customerId);
        java.math.BigDecimal totalOutstanding = java.math.BigDecimal.ZERO;
        int unpaidCount = 0;
        for (PaymentEntity p : payments) {
            if ("PENDING".equals(p.getStatus()) || "PARTIAL".equals(p.getStatus())) {
                totalOutstanding = totalOutstanding.add(p.getAmount());
                unpaidCount++;
            }
        }
        OutstandingBalanceResponseDTO dto = new OutstandingBalanceResponseDTO();
        dto.setCustomerId(customerId);
        dto.setTotalOutstanding(totalOutstanding);
        dto.setUnpaidInvoiceCount(unpaidCount);
        return dto;
    }

    @Override
    public List<PaymentHistoryResponseDTO> getPaymentHistory(UUID customerId) throws FailedRequestException {
        List<PaymentEntity> payments = paymentRepository.findByCustomerId(customerId);
        return payments.stream()
                .flatMap(p -> paymentHistoryRepository.findByPaymentIdOrderByChangedAtDesc(p.getId()).stream())
                .map(h -> {
                    PaymentHistoryResponseDTO dto = new PaymentHistoryResponseDTO();
                    dto.setId(h.getId());
                    dto.setPaymentId(h.getPaymentId());
                    dto.setFromStatus(h.getFromStatus());
                    dto.setToStatus(h.getToStatus());
                    dto.setChangedBy(h.getChangedBy());
                    dto.setChangedAt(h.getChangedAt());
                    dto.setReason(h.getReason());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public PaymentResponseDTO processRefund(UUID id, RefundRequestDTO request) throws FailedRequestException {
        PaymentEntity entity = paymentRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Payment not found: " + id, null));
        entity.setStatus("REFUNDED");
        entity.setRefundedAmount(request.getAmount());
        entity.setRefundedAt(LocalDateTime.now());
        entity = paymentRepository.save(entity);
        paymentCacheService.savePayment(entity);
        return toResponse(entity);
    }

    @Override
    public PaymentResponseDTO cancelPayment(UUID id, String reason) throws FailedRequestException {
        PaymentEntity entity = paymentRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Payment not found: " + id, null));
        entity.setStatus("CANCELLED");
        entity.setNotes(reason);
        entity = paymentRepository.save(entity);
        paymentCacheService.savePayment(entity);
        return toResponse(entity);
    }

    private PaymentResponseDTO toResponse(PaymentEntity entity) {
        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.setId(entity.getId());
        dto.setPaymentNo(entity.getPaymentNo());
        dto.setInvoiceId(entity.getInvoiceId());
        dto.setJobId(entity.getJobId());
        dto.setCustomerId(entity.getCustomerId());
        dto.setPaymentDate(entity.getPaymentDate());
        dto.setPaymentMethodId(entity.getPaymentMethodId());
        dto.setAmount(entity.getAmount());
        dto.setAmountReceived(entity.getAmountReceived());
        dto.setChangeAmount(entity.getChangeAmount());
        dto.setCurrency(entity.getCurrency());
        dto.setExchangeRate(entity.getExchangeRate());
        dto.setStatus(entity.getStatus());
        dto.setReferenceNumber(entity.getReferenceNumber());
        dto.setBankName(entity.getBankName());
        dto.setChequeNumber(entity.getChequeNumber());
        dto.setChequeBank(entity.getChequeBank());
        dto.setChequeDate(entity.getChequeDate());
        dto.setNotes(entity.getNotes());
        dto.setReceivedBy(entity.getReceivedBy());
        dto.setApprovedBy(entity.getApprovedBy());
        dto.setApprovedAt(entity.getApprovedAt());
        dto.setRefundedAmount(entity.getRefundedAmount());
        dto.setRefundedAt(entity.getRefundedAt());
        dto.setCreatedAt(entity.getCreatedAt());

        Optional.ofNullable(entity.getId())
                .flatMap(id -> receiptRepository.findByPaymentId(id))
                .ifPresent(r -> {
                    ReceiptResponseDTO rdto = new ReceiptResponseDTO();
                    rdto.setId(r.getId());
                    rdto.setReceiptNo(r.getReceiptNo());
                    rdto.setPaymentId(r.getPaymentId());
                    rdto.setInvoiceId(r.getInvoiceId());
                    rdto.setCustomerId(r.getCustomerId());
                    rdto.setReceiptDate(r.getReceiptDate());
                    rdto.setReceiptType(r.getReceiptType());
                    rdto.setAmount(r.getAmount());
                    rdto.setAmountInWordsTh(r.getAmountInWordsTh());
                    rdto.setAmountInWordsEn(r.getAmountInWordsEn());
                    rdto.setCurrency(r.getCurrency());
                    rdto.setStatus(r.getStatus());
                    rdto.setNotes(r.getNotes());
                    rdto.setIssuedBy(r.getIssuedBy());
                    rdto.setCreatedAt(r.getCreatedAt());
                    dto.setReceipt(rdto);
                });
        return dto;
    }

    private String generatePaymentNo() {
        return "PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
