package com.icmon.module.job.presentation.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class JobServiceResponseDTO {
    private UUID id;
    private UUID jobId;
    private UUID serviceId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private BigDecimal discount;
    private BigDecimal netPrice;
    private String note;
}
