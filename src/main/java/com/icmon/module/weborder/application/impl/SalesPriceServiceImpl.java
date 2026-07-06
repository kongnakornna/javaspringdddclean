package com.icmon.module.weborder.application.impl;

import com.icmon._shared.application.GenericAuthDomainServiceImpl;
import com.icmon.module.weborder.application.interfaces.SalesPriceService;
import com.icmon.module.weborder.infrastructure.cache.SalesPriceCacheService;
import com.icmon.module.weborder.infrastructure.entity.SalesPriceEntity;
import com.icmon.module.weborder.infrastructure.repository.SalesPriceRepository;
import com.icmon.module.weborder.presentation.dto.response.SalesPriceResponseDTO;
import com.icmon.exception.SystemGlobalException;
import com.icmon.exception.models.FailedRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesPriceServiceImpl extends GenericAuthDomainServiceImpl implements SalesPriceService {

    private final SalesPriceRepository salesPriceRepository;
    private final SalesPriceCacheService cacheService;

    @Override
    @Transactional(readOnly = true)
    public List<SalesPriceResponseDTO> getPricesByItem(UUID itemId) throws SystemGlobalException {
        List<SalesPriceEntity> prices = salesPriceRepository.findByItemIdAndIsActiveTrue(itemId);
        return prices.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SalesPriceResponseDTO createPrice(SalesPriceResponseDTO request) throws SystemGlobalException {
        SalesPriceEntity entity = new SalesPriceEntity();
        entity.setItemId(request.getItemId());
        entity.setPriceTier(request.getPriceTier() != null ? request.getPriceTier() : "DEFAULT");
        entity.setUnitPrice(request.getUnitPrice());
        entity.setCurrency(request.getCurrency() != null ? request.getCurrency() : "THB");
        entity.setEffectiveDate(request.getEffectiveDate() != null ? request.getEffectiveDate() : LocalDateTime.now());
        entity.setExpiryDate(request.getExpiryDate());
        entity.setMinQuantity(request.getMinQuantity() != null ? request.getMinQuantity() : 1);
        entity.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
        entity.setUserId(getUserId());
        entity.setWhitelabelId(getWhitelabelId());
        SalesPriceEntity saved = salesPriceRepository.save(entity);
        return toDTO(saved);
    }

    @Override
    @Transactional
    public SalesPriceResponseDTO updatePrice(UUID id, SalesPriceResponseDTO request) throws SystemGlobalException {
        SalesPriceEntity entity = salesPriceRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Sales price not found with id: " + id, null));
        if (request.getUnitPrice() != null) entity.setUnitPrice(request.getUnitPrice());
        if (request.getPriceTier() != null) entity.setPriceTier(request.getPriceTier());
        if (request.getCurrency() != null) entity.setCurrency(request.getCurrency());
        if (request.getExpiryDate() != null) entity.setExpiryDate(request.getExpiryDate());
        if (request.getMinQuantity() != null) entity.setMinQuantity(request.getMinQuantity());
        if (request.getIsActive() != null) entity.setIsActive(request.getIsActive());
        SalesPriceEntity saved = salesPriceRepository.save(entity);
        return toDTO(saved);
    }

    @Override
    @Transactional
    public void deletePrice(UUID id) throws SystemGlobalException {
        SalesPriceEntity entity = salesPriceRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Sales price not found with id: " + id, null));
        entity.setIsActive(false);
        salesPriceRepository.save(entity);
    }

    private SalesPriceResponseDTO toDTO(SalesPriceEntity entity) {
        return SalesPriceResponseDTO.builder()
                .id(entity.getId())
                .itemId(entity.getItemId())
                .priceTier(entity.getPriceTier())
                .unitPrice(entity.getUnitPrice())
                .currency(entity.getCurrency())
                .effectiveDate(entity.getEffectiveDate())
                .expiryDate(entity.getExpiryDate())
                .minQuantity(entity.getMinQuantity())
                .isActive(entity.getIsActive())
                .build();
    }
}
