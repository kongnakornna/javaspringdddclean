package com.icmon.module.dashboard.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.dashboard.application.interfaces.DashboardService;
import com.icmon.module.dashboard.presentation.dto.response.RevenueResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetRevenueByPeriodUseCase {
    private final DashboardService dashboardService;

    public List<RevenueResponseDTO> execute(String period, Integer months) throws SystemGlobalException {
        return dashboardService.getRevenueByPeriod(period, months);
    }
}
