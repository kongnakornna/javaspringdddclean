package com.icmon.module.dashboard.presentation.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ServiceCategoryResponseDTO {
    private String categoryName;
    private Long serviceCount;
    private BigDecimal totalRevenue;
}
