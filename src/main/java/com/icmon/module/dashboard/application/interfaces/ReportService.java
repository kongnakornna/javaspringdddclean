package com.icmon.module.dashboard.application.interfaces;

import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.dashboard.presentation.dto.request.ReportRequestDTO;
import com.icmon.module.dashboard.presentation.dto.response.ReportResponseDTO;

public interface ReportService {
    ReportResponseDTO generateDailyReport(ReportRequestDTO request) throws FailedRequestException;
    ReportResponseDTO generateMonthlyReport(ReportRequestDTO request) throws FailedRequestException;
    ReportResponseDTO generateYearlyReport(ReportRequestDTO request) throws FailedRequestException;
    ReportResponseDTO getReportStatus(String reportId) throws FailedRequestException;
}
