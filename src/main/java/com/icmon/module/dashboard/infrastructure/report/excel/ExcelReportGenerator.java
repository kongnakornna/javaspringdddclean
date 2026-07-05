package com.icmon.module.dashboard.infrastructure.report.excel;

import com.icmon.module.dashboard.infrastructure.report.ReportGenerator;
import com.icmon.module.dashboard.presentation.dto.request.ReportRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExcelReportGenerator implements ReportGenerator {

    @Override
    public byte[] generate(ReportRequestDTO request) {
        log.info("Generating Excel report for type: {}", request.getReportType());
        StringBuilder sb = new StringBuilder();
        sb.append("Excel Report\n");
        sb.append("============\n\n");
        sb.append("Report Type: ").append(request.getReportType()).append("\n");
        sb.append("Period: ").append(request.getPeriod()).append("\n");
        return sb.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8);
    }

    @Override
    public String getFormat() {
        return "EXCEL";
    }
}
