package com.icmon.module.quotation.application.usecase;

import com.icmon.module.quotation.domain.TQuotation;
import com.icmon.module.quotation.infrastructure.cache.QuotationCalculationCacheService;
import com.icmon.module.quotation.infrastructure.entity.QuotationEntity;
import com.icmon.module.quotation.infrastructure.entity.QuotationPartEntity;
import com.icmon.module.quotation.infrastructure.entity.QuotationServiceEntity;
import com.icmon.module.quotation.infrastructure.mapper.QuotationMapper;
import com.icmon.module.quotation.infrastructure.repository.QuotationPartRepository;
import com.icmon.module.quotation.infrastructure.repository.QuotationRepository;
import com.icmon.module.quotation.infrastructure.repository.QuotationServiceRepository;
import com.icmon.exception.SystemGlobalException;
import com.icmon.exception.models.FailedRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CalculateQuotationTotalUseCase {
    private final QuotationRepository quotationRepository;
    private final QuotationPartRepository partRepository;
    private final QuotationServiceRepository serviceRepository;
    private final QuotationMapper quotationMapper;
    private final QuotationCalculationCacheService calculationCacheService;

    public TQuotation execute(UUID quotationId) throws SystemGlobalException {
        QuotationEntity entity = quotationRepository.findById(quotationId)
                .orElseThrow(() -> new FailedRequestException("Quotation not found with id: " + quotationId, null));

        TQuotation domain = quotationMapper.toDomain(entity);

        domain.setParts(
            partRepository.findByQuotationId(quotationId).stream()
                .map(p -> {
                    com.icmon.module.quotation.domain.TQuotationPart part = new com.icmon.module.quotation.domain.TQuotationPart();
                    part.setId(p.getId());
                    part.setQuotationId(p.getQuotationId());
                    part.setPartId(p.getPartId());
                    part.setQuantity(p.getQuantity());
                    part.setUnitPrice(p.getUnitPrice());
                    part.setTotalPrice(p.getTotalPrice());
                    part.setDiscount(p.getDiscount());
                    part.setNetPrice(p.getNetPrice());
                    part.setNote(p.getNote());
                    return part;
                })
                .collect(Collectors.toList())
        );

        domain.setServices(
            serviceRepository.findByQuotationId(quotationId).stream()
                .map(s -> {
                    com.icmon.module.quotation.domain.TQuotationService svc = new com.icmon.module.quotation.domain.TQuotationService();
                    svc.setId(s.getId());
                    svc.setQuotationId(s.getQuotationId());
                    svc.setServiceId(s.getServiceId());
                    svc.setQuantity(s.getQuantity());
                    svc.setUnitPrice(s.getUnitPrice());
                    svc.setTotalPrice(s.getTotalPrice());
                    svc.setDiscount(s.getDiscount());
                    svc.setNetPrice(s.getNetPrice());
                    svc.setNote(s.getNote());
                    return svc;
                })
                .collect(Collectors.toList())
        );

        domain.calculateTotals();
        calculationCacheService.cacheCalculation(domain);
        return domain;
    }
}
