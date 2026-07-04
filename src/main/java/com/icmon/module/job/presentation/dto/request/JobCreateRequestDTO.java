package com.icmon.module.job.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class JobCreateRequestDTO {

    @NotNull(message = "Customer ID is required")
    private UUID customerId;

    @NotNull(message = "Car ID is required")
    private UUID carId;

    @NotNull(message = "Mechanic ID is required")
    private UUID mechanicId;

    private String symptom;

    private Integer mileage;

    private String priority;

    private String diagnosisNote;
}
