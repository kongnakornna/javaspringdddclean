package com.icmon.module.inventory.application.impl;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.PartPickingService;
import com.icmon.module.inventory.domain.enums.PickingStatus;
import com.icmon.module.inventory.infrastructure.entity.PartPickingDetailEntity;
import com.icmon.module.inventory.infrastructure.entity.PartPickingRequestEntity;
import com.icmon.module.inventory.infrastructure.repository.PartPickingDetailRepository;
import com.icmon.module.inventory.infrastructure.repository.PartPickingRepository;
import com.icmon.module.inventory.presentation.dto.request.PickingCreateRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.PickingResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartPickingServiceImpl implements PartPickingService {
    private final PartPickingRepository pickingRepository;
    private final PartPickingDetailRepository detailRepository;

    @Override
    @Transactional
    public PickingResponseDTO createPicking(PickingCreateRequestDTO request) throws SystemGlobalException {
        PartPickingRequestEntity header = new PartPickingRequestEntity();
        header.setJobId(request.getJobId());
        header.setQuotationId(request.getQuotationId());
        header.setRequestedDate(LocalDateTime.now());
        header.setRequestedBy(getCurrentUserId());
        header.setStatus(PickingStatus.DRAFT.name());
        header.setPriority(request.getPriority() != null ? request.getPriority() : "NORMAL");
        header.setNotes(request.getNotes());
        header.setWhitelabelId(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        PartPickingRequestEntity savedHeader = pickingRepository.save(header);
        List<PickingResponseDTO.PickingItemDTO> items = new ArrayList<>();
        if (request.getItems() != null) {
            for (var itemReq : request.getItems()) {
                PartPickingDetailEntity detail = new PartPickingDetailEntity();
                detail.setPickingRequestId(savedHeader.getId());
                detail.setPartId(itemReq.getPartId());
                detail.setRequestedQuantity(itemReq.getQuantity());
                detail.setPickedQuantity(0);
                detail.setUnitPrice(itemReq.getUnitPrice());
                detail.setTotalPrice(itemReq.getUnitPrice() != null && itemReq.getQuantity() != null
                        ? itemReq.getUnitPrice().multiply(new java.math.BigDecimal(itemReq.getQuantity())) : null);
                detail.setWhitelabelId(UUID.fromString("00000000-0000-0000-0000-000000000001"));
                PartPickingDetailEntity savedDetail = detailRepository.save(detail);
                items.add(new PickingResponseDTO.PickingItemDTO(savedDetail.getId(), savedDetail.getPartId(),
                        savedDetail.getRequestedQuantity(), savedDetail.getPickedQuantity(), savedDetail.getUnitPrice()));
            }
        }
        log.info("Created picking request: {}", savedHeader.getId());
        return PickingResponseDTO.fromEntity(savedHeader, items);
    }

    @Override
    public PickingResponseDTO getPickingById(UUID id) throws SystemGlobalException {
        PartPickingRequestEntity entity = pickingRepository.findById(id)
                .orElseThrow(() -> new SystemGlobalException("Picking not found: " + id, null));
        List<PartPickingDetailEntity> details = detailRepository.findByPickingRequestId(id);
        List<PickingResponseDTO.PickingItemDTO> items = details.stream()
                .map(d -> new PickingResponseDTO.PickingItemDTO(d.getId(), d.getPartId(),
                        d.getRequestedQuantity(), d.getPickedQuantity(), d.getUnitPrice()))
                .collect(Collectors.toList());
        return PickingResponseDTO.fromEntity(entity, items);
    }

    @Override
    @Transactional
    public PickingResponseDTO confirmPicking(UUID id) throws SystemGlobalException {
        PartPickingRequestEntity entity = pickingRepository.findById(id)
                .orElseThrow(() -> new SystemGlobalException("Picking not found: " + id, null));
        if (PickingStatus.CANCELLED.name().equals(entity.getStatus())) {
            throw new SystemGlobalException("Cannot confirm a cancelled picking request.", null);
        }
        entity.setStatus(PickingStatus.CONFIRMED.name());
        entity.setConfirmedBy(getCurrentUserId());
        entity.setConfirmedDate(LocalDateTime.now());
        PartPickingRequestEntity saved = pickingRepository.save(entity);
        List<PartPickingDetailEntity> details = detailRepository.findByPickingRequestId(id);
        List<PickingResponseDTO.PickingItemDTO> items = details.stream()
                .map(d -> new PickingResponseDTO.PickingItemDTO(d.getId(), d.getPartId(),
                        d.getRequestedQuantity(), d.getPickedQuantity(), d.getUnitPrice()))
                .collect(Collectors.toList());
        log.info("Confirmed picking: {}", id);
        return PickingResponseDTO.fromEntity(saved, items);
    }

    @Override
    public List<PickingResponseDTO> getPickingByJobId(UUID jobId) throws SystemGlobalException {
        return pickingRepository.findByJobId(jobId).stream()
                .map(e -> {
                    List<PartPickingDetailEntity> details = detailRepository.findByPickingRequestId(e.getId());
                    List<PickingResponseDTO.PickingItemDTO> items = details.stream()
                            .map(d -> new PickingResponseDTO.PickingItemDTO(d.getId(), d.getPartId(),
                                    d.getRequestedQuantity(), d.getPickedQuantity(), d.getUnitPrice()))
                            .collect(Collectors.toList());
                    return PickingResponseDTO.fromEntity(e, items);
                }).collect(Collectors.toList());
    }

    private UUID getCurrentUserId() {
        return UUID.fromString("00000000-0000-0000-0000-000000000001");
    }
}
