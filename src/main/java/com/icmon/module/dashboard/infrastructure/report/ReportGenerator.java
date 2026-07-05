package com.icmon.module.dashboard.infrastructure.report;

import com.icmon.module.dashboard.presentation.dto.request.ReportRequestDTO;

public interface ReportGenerator {
    byte[] generate(ReportRequestDTO request);
    String getFormat();
}
