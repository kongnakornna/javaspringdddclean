package com.icmon.module.inventory.application.impl;

import com.icmon._shared.application.GenericAuthDomainServiceImpl;
import com.icmon.exception.SystemGlobalException;
import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.inventory.application.interfaces.InventoryAdjustmentService;
import com.icmon.module.inventory.infrastructure.cache.PartMasterCacheService;
import com.icmon.module.inventory.infrastructure.entity.InventoryAdjustmentDetailEntity;
import com.icmon.module.inventory.infrastructure.entity.InventoryAdjustmentHeaderEntity;
import com.icmon.module.inventory.infrastructure.repository.InventoryAdjustmentDetailRepository;
import com.icmon.module.inventory.infrastructure.repository.InventoryAdjustmentHeaderRepository;
import com.icmon.module.inventory.presentation.dto.request.AdjustmentApproveRequestDTO;
import com.icmon.module.inventory.presentation.dto.request.AdjustmentCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.AdjustmentResponseDTO;
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
public class InventoryAdjustmentServiceImpl extends GenericAuthDomainServiceImpl implements InventoryAdjustmentService {

    private final InventoryAdjustmentHeaderRepository headerRepository;
    private final InventoryAdjustmentDetailRepository detailRepository;
    private final PartMasterCacheService partCacheService;

    @Override
    @Transactional
    public AdjustmentResponseDTO createAdjustment(AdjustmentCreateRequestDTO request) throws SystemGlobalException {
        InventoryAdjustmentHeaderEntity header = new InventoryAdjustmentHeaderEntity();
        header.setAdjustmentDate(LocalDateTime.now());
        header.setAdjustmentType(request.getAdjustmentType());
        header.setReason(request.getReason());
        header.setStatus("DRAFT");
        header.setDescription(request.getDescription());
        InventoryAdjustmentHeaderEntity savedHeader = headerRepository.save(header);

        BigDecimal totalValue = BigDecimal.ZERO;
        if (request.getDetails() != null) {
            for (AdjustmentCreateRequestDTO.AdjustmentDetailItem item : request.getDetails()) {
                InventoryAdjustmentDetailEntity detail = new InventoryAdjustmentDetailEntity();
                detail.setAdjustmentHeaderId(savedHeader.getId());
                detail.setPartId(UUID.fromString(item.getPartId()));
                detail.setQuantity(item.getQuantity());
                detail.setNote(item.getNote());
                detailRepository.save(detail);
                BigDecimal cost = BigDecimal.valueOf(item.getQuantity());
                totalValue = totalValue.add(cost);
            }
        }
        savedHeader.setTotalAdjustmentValue(totalValue);
        headerRepository.save(savedHeader);
        return toResponseDTO(savedHeader);
    }

    @Override
    public AdjustmentResponseDTO getAdjustment(UUID id) throws SystemGlobalException {
        InventoryAdjustmentHeaderEntity header = headerRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Adjustment not found with id: " + id, null));
        return toResponseDTO(header);
    }

    @Override
    public AdjustmentResponseDTO getAdjustmentByNo(String adjustmentNo) throws SystemGlobalException {
        InventoryAdjustmentHeaderEntity header = headerRepository.findByAdjustmentNo(adjustmentNo)
                .orElseThrow(() -> new FailedRequestException("Adjustment not found with no: " + adjustmentNo, null));
        return toResponseDTO(header);
    }

    @Override
    public Page<AdjustmentResponseDTO> listAdjustments(String status, String type, Pageable pageable) throws SystemGlobalException {
        if (status != null) {
            List<InventoryAdjustmentHeaderEntity> list = headerRepository.findByStatus(status);
            return toPage(list, pageable);
        }
        if (type != null) {
            List<InventoryAdjustmentHeaderEntity> list = headerRepository.findByAdjustmentType(type);
            return toPage(list, pageable);
        }
        return headerRepository.findAll(pageable).map(this::toResponseDTO);
    }

    @Override
    @Transactional
    public AdjustmentResponseDTO approveAdjustment(UUID id, AdjustmentApproveRequestDTO request) throws SystemGlobalException {
        InventoryAdjustmentHeaderEntity header = headerRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Adjustment not found with id: " + id, null));
        if (!"DRAFT".equals(header.getStatus())) {
            throw new FailedRequestException("Only DRAFT adjustments can be approved", null);
        }
        header.setStatus("APPROVED");
        header.setApprovedBy(request.getApprovedBy());
        header.setApprovedAt(LocalDateTime.now());
        if (request.getDescription() != null) header.setDescription(request.getDescription());
        headerRepository.save(header);
        return toResponseDTO(header);
    }

    @Override
    @Transactional
    public void deleteAdjustment(UUID id) throws SystemGlobalException {
        InventoryAdjustmentHeaderEntity header = headerRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Adjustment not found with id: " + id, null));
        header.setDeleted(true);
        header.setDeletedAt(LocalDateTime.now());
        headerRepository.save(header);
    }

    private AdjustmentResponseDTO toResponseDTO(InventoryAdjustmentHeaderEntity header) {
        AdjustmentResponseDTO dto = new AdjustmentResponseDTO();
        dto.setId(header.getId());
        dto.setAdjustmentNo(header.getAdjustmentNo());
        dto.setAdjustmentDate(header.getAdjustmentDate());
        dto.setAdjustmentType(header.getAdjustmentType());
        dto.setReason(header.getReason());
        dto.setStatus(header.getStatus());
        dto.setDescription(header.getDescription());
        dto.setApprovedBy(header.getApprovedBy());
        dto.setApprovedAt(header.getApprovedAt());
        dto.setTotalAdjustmentValue(header.getTotalAdjustmentValue());
        dto.setCreatedAt(header.getCreatedAt());
        List<InventoryAdjustmentDetailEntity> details = detailRepository.findByAdjustmentHeaderId(header.getId());
        dto.setDetails(details.stream().map(d -> {
            AdjustmentResponseDTO.AdjustmentDetailItem item = new AdjustmentResponseDTO.AdjustmentDetailItem();
            item.setId(d.getId());
            item.setPartId(d.getPartId());
            item.setQuantity(d.getQuantity());
            item.setNote(d.getNote());
            return item;
        }).collect(Collectors.toList()));
        return dto;
    }

    private Page<AdjustmentResponseDTO> toPage(List<InventoryAdjustmentHeaderEntity> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        List<AdjustmentResponseDTO> content = list.subList(start, end).stream()
                .map(this::toResponseDTO).collect(Collectors.toList());
        return new org.springframework.data.domain.PageImpl<>(content, pageable, list.size());
    }
}
