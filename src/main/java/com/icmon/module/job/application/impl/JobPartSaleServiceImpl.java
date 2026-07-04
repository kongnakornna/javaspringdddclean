package com.icmon.module.job.application.impl;

import com.icmon.module.job.application.interfaces.JobPartSaleService;
import com.icmon.module.job.infrastructure.entity.JobPartSalesEntity;
import com.icmon.module.job.infrastructure.repository.JobPartSalesRepository;
import com.icmon.module.job.presentation.dto.request.JobPartRequestDTO;
import com.icmon.module.job.presentation.dto.response.JobPartResponseDTO;
import com.icmon.exception.SystemGlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobPartSaleServiceImpl implements JobPartSaleService {

    private final JobPartSalesRepository jobPartSalesRepository;

    @Override
    public JobPartResponseDTO addPart(UUID jobId, JobPartRequestDTO request) throws SystemGlobalException {
        JobPartSalesEntity entity = new JobPartSalesEntity();
        entity.setJobId(jobId);
        entity.setPartId(request.getPartId());
        entity.setQuantity(request.getQuantity());
        entity.setUnitPrice(request.getUnitPrice());
        entity.setDiscount(request.getDiscount() != null ? request.getDiscount() : BigDecimal.ZERO);
        entity.setNote(request.getNote());
        JobPartSalesEntity saved = jobPartSalesRepository.save(entity);
        return toResponseDTO(saved);
    }

    @Override
    public List<JobPartResponseDTO> getPartsByJobId(UUID jobId) throws SystemGlobalException {
        return jobPartSalesRepository.findByJobId(jobId)
                .stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Override
    public void removePart(UUID partSaleId) throws SystemGlobalException {
        jobPartSalesRepository.deleteById(partSaleId);
    }

    private JobPartResponseDTO toResponseDTO(JobPartSalesEntity entity) {
        BigDecimal total = entity.getUnitPrice().multiply(BigDecimal.valueOf(entity.getQuantity()));
        BigDecimal net = entity.getDiscount() != null ? total.subtract(entity.getDiscount()) : total;
        return JobPartResponseDTO.builder()
                .id(entity.getId())
                .jobId(entity.getJobId())
                .partId(entity.getPartId())
                .quantity(entity.getQuantity())
                .unitPrice(entity.getUnitPrice())
                .totalPrice(total)
                .discount(entity.getDiscount())
                .netPrice(net)
                .note(entity.getNote())
                .build();
    }
}
