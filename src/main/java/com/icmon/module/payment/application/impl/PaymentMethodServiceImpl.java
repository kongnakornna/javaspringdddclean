package com.icmon.module.payment.application.impl;

import com.icmon._shared.application.GenericAuthDomainServiceImpl;
import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.payment.application.interfaces.PaymentMethodService;
import com.icmon.module.payment.infrastructure.entity.PaymentMethodEntity;
import com.icmon.module.payment.infrastructure.repository.PaymentMethodRepository;
import com.icmon.module.payment.presentation.dto.response.PaymentMethodResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentMethodServiceImpl extends GenericAuthDomainServiceImpl implements PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodServiceImpl(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Override
    public List<PaymentMethodResponseDTO> getAllActiveMethods() throws FailedRequestException {
        return paymentMethodRepository.findByIsActiveTrue().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentMethodResponseDTO getMethodById(UUID id) throws FailedRequestException {
        PaymentMethodEntity entity = paymentMethodRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Payment method not found: " + id, null));
        return toResponse(entity);
    }

    @Override
    public PaymentMethodResponseDTO getMethodByCode(String code) throws FailedRequestException {
        PaymentMethodEntity entity = paymentMethodRepository.findByMethodCode(code)
                .orElseThrow(() -> new FailedRequestException("Payment method not found: " + code, null));
        return toResponse(entity);
    }

    private PaymentMethodResponseDTO toResponse(PaymentMethodEntity entity) {
        PaymentMethodResponseDTO dto = new PaymentMethodResponseDTO();
        dto.setId(entity.getId());
        dto.setMethodCode(entity.getMethodCode());
        dto.setMethodName(entity.getMethodName());
        dto.setMethodNameEn(entity.getMethodNameEn());
        dto.setActive(entity.isActive());
        dto.setRequiresApproval(entity.isRequiresApproval());
        dto.setFeePercentage(entity.getFeePercentage());
        dto.setFeeFixed(entity.getFeeFixed());
        dto.setDescription(entity.getDescription());
        return dto;
    }
}
