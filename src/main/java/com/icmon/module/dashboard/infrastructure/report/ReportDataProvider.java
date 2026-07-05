package com.icmon.module.dashboard.infrastructure.report;

import com.icmon.module.dashboard.presentation.dto.request.ReportRequestDTO;

import java.util.List;
import java.util.Map;

public interface ReportDataProvider {
    List<Map<String, Object>> getReportData(ReportRequestDTO request);
    String getReportType();
}
