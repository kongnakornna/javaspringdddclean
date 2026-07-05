package com.icmon.module.inventory.application.impl;

import com.icmon._shared.application.GenericAuthDomainServiceImpl;
import com.icmon.exception.SystemGlobalException;
import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.inventory.application.interfaces.StocktakeService;
import com.icmon.module.inventory.infrastructure.entity.StocktakeDetailEntity;
import com.icmon.module.inventory.infrastructure.entity.StocktakeHeaderEntity;
import com.icmon.module.inventory.infrastructure.repository.StocktakeDetailRepository;
import com.icmon.module.inventory.infrastructure.repository.StocktakeHeaderRepository;
import com.icmon.module.inventory.presentation.dto.request.StocktakeCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.request.StocktakeDetailRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.StocktakeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StocktakeServiceImpl extends GenericAuthDomainServiceImpl implements StocktakeService {

    private final StocktakeHeaderRepository headerRepository;
    private final StocktakeDetailRepository detailRepository;

    @Override
    @Transactional
    public StocktakeResponseDTO createStocktake(StocktakeCreateRequestDTO request) throws SystemGlobalException {
        StocktakeHeaderEntity entity = new StocktakeHeaderEntity();
        entity.setStocktakeDate(LocalDateTime.now());
        entity.setStatus("DRAFT");
        entity.setStartedBy(request.getStartedBy());
        entity.setStartedAt(LocalDateTime.now());
        entity.setNotes(request.getNotes());
        StocktakeHeaderEntity saved = headerRepository.save(entity);
        return toResponseDTO(saved);
    }

    @Override
    public StocktakeResponseDTO getStocktake(UUID id) throws SystemGlobalException {
        StocktakeHeaderEntity entity = headerRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Stocktake not found with id: " + id, null));
        return toResponseDTO(entity);
    }

    @Override
    public StocktakeResponseDTO getStocktakeByNo(String stocktakeNo) throws SystemGlobalException {
        StocktakeHeaderEntity entity = headerRepository.findByStocktakeNo(stocktakeNo)
                .orElseThrow(() -> new FailedRequestException("Stocktake not found with no: " + stocktakeNo, null));
        return toResponseDTO(entity);
    }

    @Override
    public Page<StocktakeResponseDTO> listStocktakes(String status, Pageable pageable) throws SystemGlobalException {
        if (status != null) {
            List<StocktakeHeaderEntity> list = headerRepository.findByStatus(status);
            return toPage(list.stream().map(this::toResponseDTO).collect(Collectors.toList()), pageable);
        }
        return headerRepository.findAll(pageable).map(this::toResponseDTO);
    }

    @Override
    @Transactional
    public StocktakeResponseDTO addDetail(UUID stocktakeId, StocktakeDetailRequestDTO request) throws SystemGlobalException {
        StocktakeHeaderEntity header = headerRepository.findById(stocktakeId)
                .orElseThrow(() -> new FailedRequestException("Stocktake not found with id: " + stocktakeId, null));
        if (!"DRAFT".equals(header.getStatus()) && !"IN_PROGRESS".equals(header.getStatus())) {
            throw new FailedRequestException("Can only add details to DRAFT or IN_PROGRESS stocktake", null);
        }
        StocktakeDetailEntity detail = new StocktakeDetailEntity();
        detail.setStocktakeHeaderId(stocktakeId);
        detail.setPartId(request.getPartId());
        detail.setSystemQuantity(request.getSystemQuantity());
        detail.setCountedQuantity(request.getCountedQuantity());
        detail.setDiscrepancy(request.getCountedQuantity() - request.getSystemQuantity());
        detail.setNote(request.getNote());
        detail.setCountedBy(request.getCountedBy());
        detail.setCountedAt(LocalDateTime.now());
        detailRepository.save(detail);
        if ("DRAFT".equals(header.getStatus())) {
            header.setStatus("IN_PROGRESS");
            headerRepository.save(header);
        }
        return toResponseDTO(headerRepository.findById(stocktakeId).orElse(header));
    }

    @Override
    @Transactional
    public StocktakeResponseDTO completeStocktake(UUID id) throws SystemGlobalException {
        StocktakeHeaderEntity entity = headerRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Stocktake not found with id: " + id, null));
        if (!"IN_PROGRESS".equals(entity.getStatus())) {
            throw new FailedRequestException("Stocktake must be IN_PROGRESS to complete", null);
        }
        List<StocktakeDetailEntity> details = detailRepository.findByStocktakeHeaderId(id);
        int totalDisc = details.stream().mapToInt(StocktakeDetailEntity::getDiscrepancy).sum();
        entity.setTotalDiscrepancy(totalDisc);
        entity.setStatus("COMPLETED");
        entity.setCompletedAt(LocalDateTime.now());
        headerRepository.save(entity);
        return toResponseDTO(entity);
    }

    @Override
    @Transactional
    public void deleteStocktake(UUID id) throws SystemGlobalException {
        StocktakeHeaderEntity entity = headerRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Stocktake not found with id: " + id, null));
        entity.setDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        headerRepository.save(entity);
    }

    private StocktakeResponseDTO toResponseDTO(StocktakeHeaderEntity entity) {
        StocktakeResponseDTO dto = new StocktakeResponseDTO();
        dto.setId(entity.getId());
        dto.setStocktakeNo(entity.getStocktakeNo());
        dto.setStocktakeDate(entity.getStocktakeDate());
        dto.setStatus(entity.getStatus());
        dto.setStartedBy(entity.getStartedBy());
        dto.setStartedAt(entity.getStartedAt());
        dto.setCompletedBy(entity.getCompletedBy());
        dto.setCompletedAt(entity.getCompletedAt());
        dto.setTotalDiscrepancy(entity.getTotalDiscrepancy());
        dto.setNotes(entity.getNotes());
        dto.setCreatedAt(entity.getCreatedAt());
        List<StocktakeDetailEntity> details = detailRepository.findByStocktakeHeaderId(entity.getId());
        dto.setDetails(details.stream().map(d -> {
            StocktakeResponseDTO.StocktakeDetailItem item = new StocktakeResponseDTO.StocktakeDetailItem();
            item.setId(d.getId());
            item.setPartId(d.getPartId());
            item.setSystemQuantity(d.getSystemQuantity());
            item.setCountedQuantity(d.getCountedQuantity());
            item.setDiscrepancy(d.getDiscrepancy());
            item.setNote(d.getNote());
            item.setCountedBy(d.getCountedBy());
            item.setCountedAt(d.getCountedAt());
            return item;
        }).collect(Collectors.toList()));
        return dto;
    }

    private Page<StocktakeResponseDTO> toPage(List<StocktakeResponseDTO> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        return new org.springframework.data.domain.PageImpl<>(list.subList(start, end), pageable, list.size());
    }
}
