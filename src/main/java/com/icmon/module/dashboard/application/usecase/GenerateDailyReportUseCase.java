package com.icmon.module.dashboard.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.dashboard.application.interfaces.ReportService;
import com.icmon.module.dashboard.presentation.dto.request.ReportRequestDTO;
import com.icmon.module.dashboard.presentation.dto.response.ReportResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GenerateDailyReportUseCase {
    private final ReportService reportService;

    public ReportResponseDTO execute(ReportRequestDTO request) throws SystemGlobalException {
        return reportService.generateDailyReport(request);
    }
}
