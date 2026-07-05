package com.icmon.module.payment.application.impl;

import com.icmon._shared.application.GenericAuthDomainServiceImpl;
import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.payment.application.interfaces.PaymentHistoryService;
import com.icmon.module.payment.infrastructure.entity.PaymentHistoryEntity;
import com.icmon.module.payment.infrastructure.repository.PaymentHistoryRepository;
import com.icmon.module.payment.presentation.dto.response.PaymentHistoryResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentHistoryServiceImpl extends GenericAuthDomainServiceImpl implements PaymentHistoryService {

    private final PaymentHistoryRepository paymentHistoryRepository;

    public PaymentHistoryServiceImpl(PaymentHistoryRepository paymentHistoryRepository) {
        this.paymentHistoryRepository = paymentHistoryRepository;
    }

    @Override
    public List<PaymentHistoryResponseDTO> getPaymentHistoryByPaymentId(UUID paymentId) throws FailedRequestException {
        return paymentHistoryRepository.findByPaymentIdOrderByChangedAtDesc(paymentId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentHistoryResponseDTO recordStatusChange(UUID paymentId, String fromStatus, String toStatus, UUID changedBy, String reason) throws FailedRequestException {
        PaymentHistoryEntity entity = new PaymentHistoryEntity();
        entity.setPaymentId(paymentId);
        entity.setFromStatus(fromStatus);
        entity.setToStatus(toStatus);
        entity.setChangedBy(changedBy);
        entity.setChangedAt(LocalDateTime.now());
        entity.setReason(reason);
        entity = paymentHistoryRepository.save(entity);
        return toResponse(entity);
    }

    private PaymentHistoryResponseDTO toResponse(PaymentHistoryEntity entity) {
        PaymentHistoryResponseDTO dto = new PaymentHistoryResponseDTO();
        dto.setId(entity.getId());
        dto.setPaymentId(entity.getPaymentId());
        dto.setFromStatus(entity.getFromStatus());
        dto.setToStatus(entity.getToStatus());
        dto.setChangedBy(entity.getChangedBy());
        dto.setChangedAt(entity.getChangedAt());
        dto.setReason(entity.getReason());
        return dto;
    }
}
