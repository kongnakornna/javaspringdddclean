package com.icmon.module.dashboard.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.dashboard.application.interfaces.DashboardService;
import com.icmon.module.dashboard.presentation.dto.response.SalesOverviewResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetSalesOverviewUseCase {
    private final DashboardService dashboardService;

    public SalesOverviewResponseDTO execute(String period, String startDate, String endDate) throws SystemGlobalException {
        return dashboardService.getSalesOverview(period, startDate, endDate);
    }
}
