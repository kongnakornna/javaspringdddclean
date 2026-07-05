package com.icmon.module.quotation.application.impl;

import com.icmon._shared.application.GenericAuthDomainServiceImpl;
import com.icmon.module.quotation.application.interfaces.QuotationServiceItemService;
import com.icmon.module.quotation.domain.TQuotationService;
import com.icmon.module.quotation.infrastructure.entity.QuotationServiceEntity;
import com.icmon.module.quotation.infrastructure.mapper.QuotationServiceItemMapper;
import com.icmon.module.quotation.infrastructure.repository.QuotationRepository;
import com.icmon.module.quotation.infrastructure.repository.QuotationServiceRepository;
import com.icmon.module.quotation.presentation.dto.request.QuotationServiceRequestDTO;
import com.icmon.module.quotation.presentation.dto.response.QuotationServiceResponseDTO;
import com.icmon.exception.SystemGlobalException;
import com.icmon.exception.models.FailedRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuotationServiceItemServiceImpl extends GenericAuthDomainServiceImpl implements QuotationServiceItemService {

    private final QuotationServiceRepository serviceRepository;
    private final QuotationRepository quotationRepository;
    private final QuotationServiceItemMapper serviceItemMapper;

    @Override
    @Transactional
    public QuotationServiceResponseDTO addService(QuotationServiceRequestDTO request) throws SystemGlobalException {
        quotationRepository.findById(request.getQuotationId())
                .orElseThrow(() -> new FailedRequestException("Quotation not found with id: " + request.getQuotationId(), null));

        QuotationServiceEntity entity = new QuotationServiceEntity();
        entity.setQuotationId(request.getQuotationId());
        entity.setServiceId(request.getServiceId());
        entity.setQuantity(request.getQuantity());
        entity.setUnitPrice(request.getUnitPrice());
        entity.setDiscount(request.getDiscount() != null ? request.getDiscount() : java.math.BigDecimal.ZERO);
        entity.setNote(request.getNote());
        entity.setTotalPrice(entity.getUnitPrice().multiply(java.math.BigDecimal.valueOf(entity.getQuantity())));
        entity.setNetPrice(entity.getTotalPrice().subtract(entity.getDiscount() != null ? entity.getDiscount() : java.math.BigDecimal.ZERO));
        entity.setUserId(getUserId());
        entity.setWhitelabelId(getWhitelabelId());

        QuotationServiceEntity saved = serviceRepository.save(entity);
        TQuotationService domain = serviceItemMapper.toDomain(saved);
        return toResponseDTO(domain);
    }

    @Override
    @Transactional
    public QuotationServiceResponseDTO updateService(UUID id, QuotationServiceRequestDTO request) throws SystemGlobalException {
        QuotationServiceEntity entity = serviceRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Service not found with id: " + id, null));

        if (request.getQuantity() != null) entity.setQuantity(request.getQuantity());
        if (request.getUnitPrice() != null) entity.setUnitPrice(request.getUnitPrice());
        if (request.getDiscount() != null) entity.setDiscount(request.getDiscount());
        if (request.getNote() != null) entity.setNote(request.getNote());

        entity.setTotalPrice(entity.getUnitPrice().multiply(java.math.BigDecimal.valueOf(entity.getQuantity())));
        entity.setNetPrice(entity.getTotalPrice().subtract(entity.getDiscount() != null ? entity.getDiscount() : java.math.BigDecimal.ZERO));

        QuotationServiceEntity saved = serviceRepository.save(entity);
        TQuotationService domain = serviceItemMapper.toDomain(saved);
        return toResponseDTO(domain);
    }

    @Override
    @Transactional
    public void removeService(UUID id) throws SystemGlobalException {
        QuotationServiceEntity entity = serviceRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Service not found with id: " + id, null));
        serviceRepository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuotationServiceResponseDTO> getServicesByQuotation(UUID quotationId) throws SystemGlobalException {
        List<QuotationServiceEntity> entities = serviceRepository.findByQuotationId(quotationId);
        return entities.stream()
                .map(e -> toResponseDTO(serviceItemMapper.toDomain(e)))
                .collect(Collectors.toList());
    }

    private QuotationServiceResponseDTO toResponseDTO(TQuotationService domain) {
        return QuotationServiceResponseDTO.builder()
                .id(domain.getId())
                .quotationId(domain.getQuotationId())
                .serviceId(domain.getServiceId())
                .quantity(domain.getQuantity())
                .unitPrice(domain.getUnitPrice())
                .totalPrice(domain.getTotalPrice())
                .discount(domain.getDiscount())
                .netPrice(domain.getNetPrice())
                .note(domain.getNote())
                .build();
    }
}
