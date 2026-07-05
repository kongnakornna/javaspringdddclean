package com.icmon.module.customer.presentation.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CustomerHistoryResponseDTO {
    private UUID id;
    private UUID carId;
    private UUID jobId;
    private LocalDateTime serviceDate;
    private String serviceType;
    private String description;
    private BigDecimal totalCost;
    private String mechanicName;
}
