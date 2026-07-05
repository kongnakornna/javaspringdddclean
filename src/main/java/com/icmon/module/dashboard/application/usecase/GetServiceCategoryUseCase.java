package com.icmon.module.dashboard.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.dashboard.application.interfaces.DashboardService;
import com.icmon.module.dashboard.presentation.dto.response.ServiceCategoryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetServiceCategoryUseCase {
    private final DashboardService dashboardService;

    public List<ServiceCategoryResponseDTO> execute() throws SystemGlobalException {
        return dashboardService.getServiceCategory();
    }
}
