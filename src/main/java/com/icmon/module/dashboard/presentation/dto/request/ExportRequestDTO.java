package com.icmon.module.dashboard.presentation.dto.request;

import lombok.Data;

@Data
public class ExportRequestDTO {
    private String reportType;
    private String format;
    private String period;
    private String startDate;
    private String endDate;
}
