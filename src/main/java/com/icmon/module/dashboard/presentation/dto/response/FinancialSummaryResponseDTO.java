package com.icmon.module.dashboard.presentation.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FinancialSummaryResponseDTO {
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal netIncome;
    private LocalDate startDate;
    private LocalDate endDate;
}
