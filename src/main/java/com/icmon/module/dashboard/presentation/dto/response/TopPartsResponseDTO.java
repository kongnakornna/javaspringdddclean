package com.icmon.module.dashboard.presentation.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TopPartsResponseDTO {
    private UUID partId;
    private String partCode;
    private String partName;
    private Long totalSold;
    private BigDecimal totalRevenue;
    private Integer rank;
}
