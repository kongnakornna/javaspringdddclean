package com.icmon.module.job.presentation.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class JobUpdateRequestDTO {
    private UUID mechanicId;
    private String symptom;
    private String diagnosisNote;
    private Integer mileage;
    private BigDecimal estimatedCost;
    private String priority;
}
