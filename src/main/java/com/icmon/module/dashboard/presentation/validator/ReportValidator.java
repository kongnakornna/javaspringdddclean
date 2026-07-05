package com.icmon.module.dashboard.presentation.validator;

import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.dashboard.presentation.dto.request.ReportRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class ReportValidator {

    public void validateReportRequest(ReportRequestDTO request) throws FailedRequestException {
        if (request.getReportType() == null || request.getReportType().isBlank()) {
            throw new FailedRequestException("Report type is required", null);
        }
    }
}
