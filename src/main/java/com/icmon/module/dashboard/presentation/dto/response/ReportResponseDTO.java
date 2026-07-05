package com.icmon.module.dashboard.presentation.dto.response;

import lombok.Data;

@Data
public class ReportResponseDTO {
    private String reportId;
    private String status;
    private String reportType;
    private String format;
    private byte[] data;
}
