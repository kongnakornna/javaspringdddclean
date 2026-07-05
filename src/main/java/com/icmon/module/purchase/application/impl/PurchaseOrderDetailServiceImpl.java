package com.icmon.module.purchase.application.impl;

import com.icmon._shared.application.GenericAuthDomainServiceImpl;
import com.icmon.exception.SystemGlobalException;
import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.purchase.application.interfaces.PurchaseOrderDetailService;
import com.icmon.module.purchase.domain.enums.PurchaseOrderStatus;
import com.icmon.module.purchase.infrastructure.cache.PurchaseOrderCacheService;
import com.icmon.module.purchase.infrastructure.entity.PurchaseOrderDetailEntity;
import com.icmon.module.purchase.infrastructure.entity.PurchaseOrderHeaderEntity;
import com.icmon.module.purchase.infrastructure.mapper.PurchaseOrderDetailMapper;
import com.icmon.module.purchase.infrastructure.repository.PurchaseOrderDetailRepository;
import com.icmon.module.purchase.infrastructure.repository.PurchaseOrderHeaderRepository;
import com.icmon.module.purchase.presentation.dto.request.PurchaseOrderDetailRequestDTO;
import com.icmon.module.purchase.presentation.dto.response.PurchaseOrderDetailResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseOrderDetailServiceImpl extends GenericAuthDomainServiceImpl implements PurchaseOrderDetailService {

    private final PurchaseOrderDetailRepository detailRepository;
    private final PurchaseOrderHeaderRepository poHeaderRepository;
    private final PurchaseOrderDetailMapper detailMapper;
    private final PurchaseOrderCacheService cacheService;

    @Override
    @Transactional
    public PurchaseOrderDetailResponseDTO addDetail(UUID poHeaderId, PurchaseOrderDetailRequestDTO request) throws SystemGlobalException {
        PurchaseOrderHeaderEntity header = poHeaderRepository.findById(poHeaderId)
                .orElseThrow(() -> new FailedRequestException("Purchase order not found with id: " + poHeaderId, null));

        if (!header.getStatus().equals(PurchaseOrderStatus.DRAFT.name())) {
            throw new FailedRequestException("Can only add details to DRAFT purchase orders.", null);
        }

        PurchaseOrderDetailEntity entity = new PurchaseOrderDetailEntity();
        entity.setPoHeaderId(poHeaderId);
        entity.setPartId(request.getPartId());
        entity.setQuantityOrdered(request.getQuantityOrdered());
        entity.setQuantityReceived(0);
        entity.setUnitPrice(request.getUnitPrice());
        entity.setDiscount(request.getDiscount() != null ? request.getDiscount() : BigDecimal.ZERO);
        entity.setNote(request.getNote());
        entity.setTotalPrice(entity.getUnitPrice().multiply(BigDecimal.valueOf(entity.getQuantityOrdered())));
        entity.setNetPrice(entity.getTotalPrice().subtract(entity.getDiscount()));
        entity.setUserId(getUserId());
        entity.setWhitelabelId(getWhitelabelId());

        PurchaseOrderDetailEntity saved = detailRepository.save(entity);
        header.setSubtotal(header.getSubtotal().add(saved.getNetPrice()));
        poHeaderRepository.save(header);
        cacheService.evict(poHeaderId);
        return toResponseDTO(saved);
    }

    @Override
    @Transactional
    public PurchaseOrderDetailResponseDTO updateDetail(UUID poHeaderId, UUID detailId, PurchaseOrderDetailRequestDTO request) throws SystemGlobalException {
        PurchaseOrderDetailEntity entity = detailRepository.findById(detailId)
                .orElseThrow(() -> new FailedRequestException("Detail not found with id: " + detailId, null));

        if (!entity.getPoHeaderId().equals(poHeaderId)) {
            throw new FailedRequestException("Detail does not belong to this purchase order.", null);
        }

        if (request.getQuantityOrdered() != null) entity.setQuantityOrdered(request.getQuantityOrdered());
        if (request.getUnitPrice() != null) entity.setUnitPrice(request.getUnitPrice());
        if (request.getDiscount() != null) entity.setDiscount(request.getDiscount());
        if (request.getNote() != null) entity.setNote(request.getNote());

        entity.setTotalPrice(entity.getUnitPrice().multiply(BigDecimal.valueOf(entity.getQuantityOrdered())));
        entity.setNetPrice(entity.getTotalPrice().subtract(entity.getDiscount()));

        PurchaseOrderDetailEntity saved = detailRepository.save(entity);
        cacheService.evict(poHeaderId);
        return toResponseDTO(saved);
    }

    @Override
    @Transactional
    public void removeDetail(UUID poHeaderId, UUID detailId) throws SystemGlobalException {
        PurchaseOrderDetailEntity entity = detailRepository.findById(detailId)
                .orElseThrow(() -> new FailedRequestException("Detail not found with id: " + detailId, null));

        if (!entity.getPoHeaderId().equals(poHeaderId)) {
            throw new FailedRequestException("Detail does not belong to this purchase order.", null);
        }

        detailRepository.delete(entity);
        cacheService.evict(poHeaderId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseOrderDetailResponseDTO> getDetailsByPoHeaderId(UUID poHeaderId) throws SystemGlobalException {
        List<PurchaseOrderDetailEntity> entities = detailRepository.findByPoHeaderId(poHeaderId);
        return entities.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private PurchaseOrderDetailResponseDTO toResponseDTO(PurchaseOrderDetailEntity entity) {
        return PurchaseOrderDetailResponseDTO.builder()
                .id(entity.getId())
                .poHeaderId(entity.getPoHeaderId())
                .partId(entity.getPartId())
                .quantityOrdered(entity.getQuantityOrdered())
                .quantityReceived(entity.getQuantityReceived())
                .unitPrice(entity.getUnitPrice())
                .totalPrice(entity.getTotalPrice())
                .discount(entity.getDiscount())
                .netPrice(entity.getNetPrice())
                .note(entity.getNote())
                .build();
    }
}
