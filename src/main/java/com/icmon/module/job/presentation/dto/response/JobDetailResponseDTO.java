package com.icmon.module.job.presentation.dto.response;

import com.icmon.module.job.domain.enums.JobStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class JobDetailResponseDTO {
    private UUID id;
    private String jobNo;
    private UUID customerId;
    private UUID carId;
    private UUID mechanicId;
    private JobStatus status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String symptom;
    private String diagnosisNote;
    private Integer mileage;
    private BigDecimal estimatedCost;
    private BigDecimal actualCost;
    private String priority;
    private List<JobServiceResponseDTO> services;
    private List<JobPartResponseDTO> parts;
    private List<JobStatusHistoryDTO> statusHistory;
    private LocalDateTime createdAt;
}
