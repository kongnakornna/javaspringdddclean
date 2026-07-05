package com.icmon.module.dashboard.presentation.dto.response;

import lombok.Data;

@Data
public class JobStatusSummaryResponseDTO {
    private String status;
    private Long count;
    private Double percentage;
}
