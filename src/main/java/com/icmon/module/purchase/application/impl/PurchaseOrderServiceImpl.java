package com.icmon.module.purchase.application.impl;

import com.icmon._shared.application.GenericAuthDomainServiceImpl;
import com.icmon.exception.SystemGlobalException;
import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.purchase.application.interfaces.PurchaseOrderService;
import com.icmon.module.purchase.domain.enums.PurchaseOrderStatus;
import com.icmon.module.purchase.infrastructure.cache.PurchaseOrderCacheService;
import com.icmon.module.purchase.infrastructure.email.PurchaseOrderEmailService;
import com.icmon.module.purchase.infrastructure.entity.PurchaseOrderHeaderEntity;
import com.icmon.module.purchase.infrastructure.entity.PurchaseOrderStatusHistoryEntity;
import com.icmon.module.purchase.infrastructure.entity.PurchaseOrderDetailEntity;
import com.icmon.module.purchase.infrastructure.mapper.PurchaseOrderHeaderMapper;
import com.icmon.module.purchase.infrastructure.repository.PurchaseOrderDetailRepository;
import com.icmon.module.purchase.infrastructure.repository.PurchaseOrderHeaderRepository;
import com.icmon.module.purchase.infrastructure.repository.PurchaseOrderStatusHistoryRepository;
import com.icmon.module.purchase.infrastructure.report.PurchaseOrderReportService;
import com.icmon.module.purchase.presentation.dto.request.PurchaseOrderCreateRequestDTO;
import com.icmon.module.purchase.presentation.dto.request.PurchaseOrderReceiveRequestDTO;
import com.icmon.module.purchase.presentation.dto.request.PurchaseOrderSendRequestDTO;
import com.icmon.module.purchase.presentation.dto.request.PurchaseOrderUpdateRequestDTO;
import com.icmon.module.purchase.presentation.dto.response.PurchaseOrderResponseDTO;
import com.icmon.module.purchase.presentation.dto.response.PurchaseOrderStatusHistoryDTO;
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
public class PurchaseOrderServiceImpl extends GenericAuthDomainServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderHeaderRepository poHeaderRepository;
    private final PurchaseOrderStatusHistoryRepository statusHistoryRepository;
    private final PurchaseOrderDetailRepository detailRepository;
    private final PurchaseOrderHeaderMapper headerMapper;
    private final PurchaseOrderCacheService cacheService;
    private final PurchaseOrderEmailService emailService;
    private final PurchaseOrderReportService reportService;

    @Override
    @Transactional
    public PurchaseOrderResponseDTO createPurchaseOrder(PurchaseOrderCreateRequestDTO request) throws SystemGlobalException {
        PurchaseOrderHeaderEntity entity = new PurchaseOrderHeaderEntity();
        entity.setQuotationId(request.getQuotationId());
        entity.setJobId(request.getJobId());
        entity.setSupplierId(request.getSupplierId());
        entity.setPoDate(LocalDateTime.now());
        entity.setStatus(PurchaseOrderStatus.DRAFT.name());
        entity.setTaxRate(request.getTaxRate() != null ? request.getTaxRate() : new BigDecimal("7.00"));
        entity.setCurrency(request.getCurrency() != null ? request.getCurrency() : "THB");
        entity.setExchangeRate(request.getExchangeRate() != null ? request.getExchangeRate() : BigDecimal.ONE);
        entity.setExpectedDeliveryDate(request.getExpectedDeliveryDate());
        entity.setPaymentTerms(request.getPaymentTerms());
        entity.setDeliveryAddress(request.getDeliveryAddress());
        entity.setNotes(request.getNotes());
        entity.setTermsAndConditions(request.getTermsAndConditions());
        entity.setSubtotal(BigDecimal.ZERO);
        entity.setTaxAmount(BigDecimal.ZERO);
        entity.setDiscountValue(BigDecimal.ZERO);
        entity.setShippingCost(BigDecimal.ZERO);
        entity.setTotal(BigDecimal.ZERO);
        entity.setUserId(getUserId());
        entity.setWhitelabelId(getWhitelabelId());

        PurchaseOrderHeaderEntity saved = poHeaderRepository.save(entity);
        recordStatusHistory(saved.getId(), null, PurchaseOrderStatus.DRAFT, "Created");
        cacheService.cache(saved);
        return toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PurchaseOrderResponseDTO getPurchaseOrder(UUID id) throws SystemGlobalException {
        PurchaseOrderHeaderEntity entity = poHeaderRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Purchase order not found with id: " + id, null));
        return toResponseDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public PurchaseOrderResponseDTO getPurchaseOrderByPoNo(String poNo) throws SystemGlobalException {
        PurchaseOrderHeaderEntity entity = poHeaderRepository.findByPoNo(poNo)
                .orElseThrow(() -> new FailedRequestException("Purchase order not found with PO no: " + poNo, null));
        return toResponseDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PurchaseOrderResponseDTO> listPurchaseOrders(String status, UUID supplierId, String startDate, String endDate, Pageable pageable) throws SystemGlobalException {
        Page<PurchaseOrderHeaderEntity> page = poHeaderRepository.searchPurchaseOrders(status, supplierId, startDate, endDate, pageable);
        return page.map(this::toResponseDTO);
    }

    @Override
    @Transactional
    public PurchaseOrderResponseDTO updatePurchaseOrder(UUID id, PurchaseOrderUpdateRequestDTO request) throws SystemGlobalException {
        PurchaseOrderHeaderEntity entity = poHeaderRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Purchase order not found with id: " + id, null));

        if (!entity.getStatus().equals(PurchaseOrderStatus.DRAFT.name())) {
            throw new FailedRequestException("Only DRAFT purchase orders can be updated.", null);
        }

        if (request.getSupplierId() != null) entity.setSupplierId(request.getSupplierId());
        if (request.getExpectedDeliveryDate() != null) entity.setExpectedDeliveryDate(request.getExpectedDeliveryDate());
        if (request.getTaxRate() != null) entity.setTaxRate(request.getTaxRate());
        if (request.getDiscountType() != null) entity.setDiscountType(request.getDiscountType());
        if (request.getDiscountValue() != null) entity.setDiscountValue(request.getDiscountValue());
        if (request.getCurrency() != null) entity.setCurrency(request.getCurrency());
        if (request.getExchangeRate() != null) entity.setExchangeRate(request.getExchangeRate());
        if (request.getShippingCost() != null) entity.setShippingCost(request.getShippingCost());
        if (request.getPaymentTerms() != null) entity.setPaymentTerms(request.getPaymentTerms());
        if (request.getDeliveryAddress() != null) entity.setDeliveryAddress(request.getDeliveryAddress());
        if (request.getNotes() != null) entity.setNotes(request.getNotes());
        if (request.getTermsAndConditions() != null) entity.setTermsAndConditions(request.getTermsAndConditions());

        PurchaseOrderHeaderEntity saved = poHeaderRepository.save(entity);
        cacheService.evict(id);
        cacheService.cache(saved);
        return toResponseDTO(saved);
    }

    @Override
    @Transactional
    public void deletePurchaseOrder(UUID id) throws SystemGlobalException {
        PurchaseOrderHeaderEntity entity = poHeaderRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Purchase order not found with id: " + id, null));
        entity.setDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        poHeaderRepository.save(entity);
        cacheService.evict(id);
    }

    @Override
    @Transactional
    public PurchaseOrderResponseDTO sendPurchaseOrder(UUID id, PurchaseOrderSendRequestDTO request) throws SystemGlobalException {
        PurchaseOrderHeaderEntity entity = poHeaderRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Purchase order not found with id: " + id, null));

        if (!entity.getStatus().equals(PurchaseOrderStatus.DRAFT.name()) && !entity.getStatus().equals(PurchaseOrderStatus.CONFIRMED.name())) {
            throw new FailedRequestException("Purchase order cannot be sent in status: " + entity.getStatus(), null);
        }

        PurchaseOrderStatus previousStatus = PurchaseOrderStatus.valueOf(entity.getStatus());
        entity.setStatus(PurchaseOrderStatus.SENT.name());
        entity.setSentAt(LocalDateTime.now());

        PurchaseOrderHeaderEntity saved = poHeaderRepository.save(entity);
        recordStatusHistory(saved.getId(), previousStatus, PurchaseOrderStatus.SENT, "Sent to supplier");

        if (request.getSendEmail() != null && request.getSendEmail()) {
            byte[] pdf = reportService.generatePurchaseOrderPdf(saved);
            emailService.sendPurchaseOrderEmail(request.getSupplierEmail(), saved.getPoNo(), pdf);
        }

        cacheService.evict(id);
        return toResponseDTO(saved);
    }

    @Override
    @Transactional
    public PurchaseOrderResponseDTO receivePurchaseOrder(UUID id, PurchaseOrderReceiveRequestDTO request) throws SystemGlobalException {
        PurchaseOrderHeaderEntity entity = poHeaderRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Purchase order not found with id: " + id, null));

        if (!entity.getStatus().equals(PurchaseOrderStatus.SENT.name()) && !entity.getStatus().equals(PurchaseOrderStatus.PARTIALLY_RECEIVED.name())) {
            throw new FailedRequestException("Purchase order cannot be received in status: " + entity.getStatus(), null);
        }

        PurchaseOrderStatus previousStatus = PurchaseOrderStatus.valueOf(entity.getStatus());
        entity.setStatus(PurchaseOrderStatus.RECEIVED.name());
        entity.setActualDeliveryDate(request.getActualDeliveryDate() != null ? request.getActualDeliveryDate() : LocalDateTime.now());
        entity.setReceivedBy(getUserId());

        PurchaseOrderHeaderEntity saved = poHeaderRepository.save(entity);
        recordStatusHistory(saved.getId(), previousStatus, PurchaseOrderStatus.RECEIVED, request.getNotes());

        cacheService.evict(id);
        return toResponseDTO(saved);
    }

    @Override
    @Transactional
    public PurchaseOrderResponseDTO cancelPurchaseOrder(UUID id, String reason) throws SystemGlobalException {
        PurchaseOrderHeaderEntity entity = poHeaderRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Purchase order not found with id: " + id, null));

        if (entity.getStatus().equals(PurchaseOrderStatus.RECEIVED.name())) {
            throw new FailedRequestException("Cannot cancel a received purchase order.", null);
        }

        PurchaseOrderStatus previousStatus = PurchaseOrderStatus.valueOf(entity.getStatus());
        entity.setStatus(PurchaseOrderStatus.CANCELLED.name());

        PurchaseOrderHeaderEntity saved = poHeaderRepository.save(entity);
        recordStatusHistory(saved.getId(), previousStatus, PurchaseOrderStatus.CANCELLED, reason);

        cacheService.evict(id);
        return toResponseDTO(saved);
    }

    @Override
    public byte[] generatePurchaseOrderPDF(UUID id) throws SystemGlobalException {
        PurchaseOrderHeaderEntity entity = poHeaderRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Purchase order not found with id: " + id, null));
        return reportService.generatePurchaseOrderPdf(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseOrderStatusHistoryDTO> getPurchaseOrderHistory(UUID id) throws SystemGlobalException {
        poHeaderRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Purchase order not found with id: " + id, null));

        List<PurchaseOrderStatusHistoryEntity> history = statusHistoryRepository.findByPoHeaderIdOrderByChangedAtAsc(id);
        return history.stream()
                .map(h -> PurchaseOrderStatusHistoryDTO.builder()
                        .id(h.getId())
                        .poHeaderId(h.getPoHeaderId())
                        .fromStatus(h.getFromStatus())
                        .toStatus(h.getToStatus())
                        .changedBy(h.getChangedBy())
                        .changedAt(h.getChangedAt())
                        .reason(h.getReason())
                        .build())
                .collect(Collectors.toList());
    }

    private void recordStatusHistory(UUID poHeaderId, PurchaseOrderStatus fromStatus, PurchaseOrderStatus toStatus, String reason) throws SystemGlobalException {
        PurchaseOrderStatusHistoryEntity history = new PurchaseOrderStatusHistoryEntity();
        history.setPoHeaderId(poHeaderId);
        history.setFromStatus(fromStatus != null ? fromStatus.name() : null);
        history.setToStatus(toStatus.name());
        history.setChangedBy(getUserId());
        history.setChangedAt(LocalDateTime.now());
        history.setReason(reason);
        history.setWhitelabelId(getWhitelabelId());
        statusHistoryRepository.save(history);
    }

    private PurchaseOrderResponseDTO toResponseDTO(PurchaseOrderHeaderEntity entity) {
        return PurchaseOrderResponseDTO.builder()
                .id(entity.getId())
                .poNo(entity.getPoNo())
                .quotationId(entity.getQuotationId())
                .jobId(entity.getJobId())
                .supplierId(entity.getSupplierId())
                .poDate(entity.getPoDate())
                .expectedDeliveryDate(entity.getExpectedDeliveryDate())
                .actualDeliveryDate(entity.getActualDeliveryDate())
                .status(entity.getStatus())
                .subtotal(entity.getSubtotal())
                .taxRate(entity.getTaxRate())
                .taxAmount(entity.getTaxAmount())
                .discountType(entity.getDiscountType())
                .discountValue(entity.getDiscountValue())
                .total(entity.getTotal())
                .currency(entity.getCurrency())
                .exchangeRate(entity.getExchangeRate())
                .shippingCost(entity.getShippingCost())
                .paymentTerms(entity.getPaymentTerms())
                .deliveryAddress(entity.getDeliveryAddress())
                .notes(entity.getNotes())
                .termsAndConditions(entity.getTermsAndConditions())
                .sentAt(entity.getSentAt())
                .confirmedAt(entity.getConfirmedAt())
                .receivedBy(entity.getReceivedBy())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
