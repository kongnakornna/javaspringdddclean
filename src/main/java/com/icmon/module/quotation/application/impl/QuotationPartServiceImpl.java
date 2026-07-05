package com.icmon.module.quotation.application.impl;

import com.icmon._shared.application.GenericAuthDomainServiceImpl;
import com.icmon.module.quotation.application.interfaces.QuotationPartService;
import com.icmon.module.quotation.domain.TQuotationPart;
import com.icmon.module.quotation.infrastructure.entity.QuotationPartEntity;
import com.icmon.module.quotation.infrastructure.mapper.QuotationPartMapper;
import com.icmon.module.quotation.infrastructure.repository.QuotationPartRepository;
import com.icmon.module.quotation.infrastructure.repository.QuotationRepository;
import com.icmon.module.quotation.presentation.dto.request.QuotationPartRequestDTO;
import com.icmon.module.quotation.presentation.dto.response.QuotationPartResponseDTO;
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
public class QuotationPartServiceImpl extends GenericAuthDomainServiceImpl implements QuotationPartService {

    private final QuotationPartRepository partRepository;
    private final QuotationRepository quotationRepository;
    private final QuotationPartMapper partMapper;

    @Override
    @Transactional
    public QuotationPartResponseDTO addPart(QuotationPartRequestDTO request) throws SystemGlobalException {
        quotationRepository.findById(request.getQuotationId())
                .orElseThrow(() -> new FailedRequestException("Quotation not found with id: " + request.getQuotationId(), null));

        QuotationPartEntity entity = new QuotationPartEntity();
        entity.setQuotationId(request.getQuotationId());
        entity.setPartId(request.getPartId());
        entity.setQuantity(request.getQuantity());
        entity.setUnitPrice(request.getUnitPrice());
        entity.setDiscount(request.getDiscount() != null ? request.getDiscount() : java.math.BigDecimal.ZERO);
        entity.setNote(request.getNote());
        entity.setTotalPrice(entity.getUnitPrice().multiply(java.math.BigDecimal.valueOf(entity.getQuantity())));
        entity.setNetPrice(entity.getTotalPrice().subtract(entity.getDiscount() != null ? entity.getDiscount() : java.math.BigDecimal.ZERO));
        entity.setUserId(getUserId());
        entity.setWhitelabelId(getWhitelabelId());

        QuotationPartEntity saved = partRepository.save(entity);
        TQuotationPart domain = partMapper.toDomain(saved);
        return toResponseDTO(domain);
    }

    @Override
    @Transactional
    public QuotationPartResponseDTO updatePart(UUID id, QuotationPartRequestDTO request) throws SystemGlobalException {
        QuotationPartEntity entity = partRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Part not found with id: " + id, null));

        if (request.getQuantity() != null) entity.setQuantity(request.getQuantity());
        if (request.getUnitPrice() != null) entity.setUnitPrice(request.getUnitPrice());
        if (request.getDiscount() != null) entity.setDiscount(request.getDiscount());
        if (request.getNote() != null) entity.setNote(request.getNote());

        entity.setTotalPrice(entity.getUnitPrice().multiply(java.math.BigDecimal.valueOf(entity.getQuantity())));
        entity.setNetPrice(entity.getTotalPrice().subtract(entity.getDiscount() != null ? entity.getDiscount() : java.math.BigDecimal.ZERO));

        QuotationPartEntity saved = partRepository.save(entity);
        TQuotationPart domain = partMapper.toDomain(saved);
        return toResponseDTO(domain);
    }

    @Override
    @Transactional
    public void removePart(UUID id) throws SystemGlobalException {
        QuotationPartEntity entity = partRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Part not found with id: " + id, null));
        partRepository.delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuotationPartResponseDTO> getPartsByQuotation(UUID quotationId) throws SystemGlobalException {
        List<QuotationPartEntity> entities = partRepository.findByQuotationId(quotationId);
        return entities.stream()
                .map(e -> toResponseDTO(partMapper.toDomain(e)))
                .collect(Collectors.toList());
    }

    private QuotationPartResponseDTO toResponseDTO(TQuotationPart domain) {
        return QuotationPartResponseDTO.builder()
                .id(domain.getId())
                .quotationId(domain.getQuotationId())
                .partId(domain.getPartId())
                .quantity(domain.getQuantity())
                .unitPrice(domain.getUnitPrice())
                .totalPrice(domain.getTotalPrice())
                .discount(domain.getDiscount())
                .netPrice(domain.getNetPrice())
                .note(domain.getNote())
                .build();
    }
}
