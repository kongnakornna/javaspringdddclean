package com.icmon.module.dashboard.presentation.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RevenueResponseDTO {
    private LocalDate period;
    private Integer invoiceCount;
    private BigDecimal revenue;
    private BigDecimal averageRevenue;
}
