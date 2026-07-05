package com.icmon.module.dashboard.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.dashboard.application.interfaces.DashboardService;
import com.icmon.module.dashboard.presentation.dto.response.TopPartsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetTopSellingPartsUseCase {
    private final DashboardService dashboardService;

    public List<TopPartsResponseDTO> execute(int limit) throws SystemGlobalException {
        return dashboardService.getTopParts(limit);
    }
}
