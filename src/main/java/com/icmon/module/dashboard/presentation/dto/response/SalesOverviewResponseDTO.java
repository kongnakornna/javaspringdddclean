package com.icmon.module.dashboard.presentation.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class SalesOverviewResponseDTO {
    private Integer totalInvoices;
    private BigDecimal totalRevenue;
    private String period;
}
