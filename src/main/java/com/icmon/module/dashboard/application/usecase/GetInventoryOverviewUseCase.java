package com.icmon.module.dashboard.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.dashboard.application.interfaces.DashboardService;
import com.icmon.module.dashboard.presentation.dto.response.InventoryOverviewResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetInventoryOverviewUseCase {
    private final DashboardService dashboardService;

    public InventoryOverviewResponseDTO execute() throws SystemGlobalException {
        return dashboardService.getInventoryOverview();
    }
}
