package com.icmon.module.dashboard.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.dashboard.application.interfaces.DashboardService;
import com.icmon.module.dashboard.presentation.dto.response.JobStatusSummaryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetJobStatusSummaryUseCase {
    private final DashboardService dashboardService;

    public List<JobStatusSummaryResponseDTO> execute() throws SystemGlobalException {
        return dashboardService.getJobStatusSummary();
    }
}
