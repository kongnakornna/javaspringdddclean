package com.icmon.module.inventory.application.impl;

import com.icmon._shared.application.GenericAuthDomainServiceImpl;
import com.icmon.exception.SystemGlobalException;
import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.inventory.application.interfaces.PartPickingService;
import com.icmon.module.inventory.infrastructure.entity.PartPickingDetailEntity;
import com.icmon.module.inventory.infrastructure.entity.PartPickingRequestEntity;
import com.icmon.module.inventory.infrastructure.repository.PartPickingDetailRepository;
import com.icmon.module.inventory.infrastructure.repository.PartPickingRequestRepository;
import com.icmon.module.inventory.presentation.dto.request.PickingCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.PickingResponseDTO;
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
public class PartPickingServiceImpl extends GenericAuthDomainServiceImpl implements PartPickingService {

    private final PartPickingRequestRepository requestRepository;
    private final PartPickingDetailRepository detailRepository;

    @Override
    @Transactional
    public PickingResponseDTO createPickingRequest(PickingCreateRequestDTO request) throws SystemGlobalException {
        PartPickingRequestEntity entity = new PartPickingRequestEntity();
        entity.setJobId(request.getJobId());
        entity.setQuotationId(request.getQuotationId());
        entity.setRequestedDate(LocalDateTime.now());
        entity.setRequestedBy(request.getRequestedBy());
        entity.setStatus("DRAFT");
        entity.setPriority(request.getPriority() != null ? request.getPriority() : "NORMAL");
        entity.setNotes(request.getNotes());
        PartPickingRequestEntity saved = requestRepository.save(entity);

        if (request.getItems() != null) {
            for (PickingCreateRequestDTO.PickingItem item : request.getItems()) {
                PartPickingDetailEntity detail = new PartPickingDetailEntity();
                detail.setPickingRequestId(saved.getId());
                detail.setPartId(item.getPartId());
                detail.setRequestedQuantity(item.getRequestedQuantity());
                detail.setPickedQuantity(0);
                detail.setNote(item.getNote());
                detailRepository.save(detail);
            }
        }
        return toResponseDTO(saved);
    }

    @Override
    public PickingResponseDTO getPickingRequest(UUID id) throws SystemGlobalException {
        PartPickingRequestEntity entity = requestRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Picking request not found with id: " + id, null));
        return toResponseDTO(entity);
    }

    @Override
    public PickingResponseDTO getPickingRequestByNo(String pickingNo) throws SystemGlobalException {
        PartPickingRequestEntity entity = requestRepository.findByPickingNo(pickingNo)
                .orElseThrow(() -> new FailedRequestException("Picking request not found with no: " + pickingNo, null));
        return toResponseDTO(entity);
    }

    @Override
    public Page<PickingResponseDTO> listPickingRequests(String status, UUID jobId, Pageable pageable) throws SystemGlobalException {
        if (jobId != null) {
            List<PartPickingRequestEntity> list = requestRepository.findByJobId(jobId);
            return toPage(list.stream().map(this::toResponseDTO).collect(Collectors.toList()), pageable);
        }
        if (status != null) {
            List<PartPickingRequestEntity> list = requestRepository.findByStatus(status);
            return toPage(list.stream().map(this::toResponseDTO).collect(Collectors.toList()), pageable);
        }
        return requestRepository.findAll(pageable).map(this::toResponseDTO);
    }

    @Override
    @Transactional
    public PickingResponseDTO pickItems(UUID id) throws SystemGlobalException {
        PartPickingRequestEntity entity = requestRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Picking request not found with id: " + id, null));
        if (!"APPROVED".equals(entity.getStatus())) {
            throw new FailedRequestException("Picking request must be APPROVED before picking", null);
        }
        entity.setStatus("PICKING");
        requestRepository.save(entity);
        return toResponseDTO(entity);
    }

    @Override
    @Transactional
    public PickingResponseDTO confirmPicking(UUID id) throws SystemGlobalException {
        PartPickingRequestEntity entity = requestRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Picking request not found with id: " + id, null));
        if (!"PICKING".equals(entity.getStatus())) {
            throw new FailedRequestException("Picking request must be in PICKING status to confirm", null);
        }
        entity.setStatus("PICKED");
        entity.setPickedDate(LocalDateTime.now());
        requestRepository.save(entity);
        return toResponseDTO(entity);
    }

    @Override
    @Transactional
    public void deletePickingRequest(UUID id) throws SystemGlobalException {
        PartPickingRequestEntity entity = requestRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Picking request not found with id: " + id, null));
        entity.setDeleted(true);
        entity.setDeletedAt(LocalDateTime.now());
        requestRepository.save(entity);
    }

    private PickingResponseDTO toResponseDTO(PartPickingRequestEntity entity) {
        PickingResponseDTO dto = new PickingResponseDTO();
        dto.setId(entity.getId());
        dto.setPickingNo(entity.getPickingNo());
        dto.setJobId(entity.getJobId());
        dto.setQuotationId(entity.getQuotationId());
        dto.setRequestedDate(entity.getRequestedDate());
        dto.setRequestedBy(entity.getRequestedBy());
        dto.setStatus(entity.getStatus());
        dto.setPriority(entity.getPriority());
        dto.setNotes(entity.getNotes());
        dto.setPickedBy(entity.getPickedBy());
        dto.setPickedDate(entity.getPickedDate());
        dto.setConfirmedBy(entity.getConfirmedBy());
        dto.setConfirmedDate(entity.getConfirmedDate());
        dto.setCreatedAt(entity.getCreatedAt());
        List<PartPickingDetailEntity> details = detailRepository.findByPickingRequestId(entity.getId());
        dto.setItems(details.stream().map(d -> {
            PickingResponseDTO.PickingItem item = new PickingResponseDTO.PickingItem();
            item.setId(d.getId());
            item.setPartId(d.getPartId());
            item.setRequestedQuantity(d.getRequestedQuantity());
            item.setPickedQuantity(d.getPickedQuantity());
            item.setNote(d.getNote());
            return item;
        }).collect(Collectors.toList()));
        return dto;
    }

    private Page<PickingResponseDTO> toPage(List<PickingResponseDTO> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        return new org.springframework.data.domain.PageImpl<>(list.subList(start, end), pageable, list.size());
    }
}
