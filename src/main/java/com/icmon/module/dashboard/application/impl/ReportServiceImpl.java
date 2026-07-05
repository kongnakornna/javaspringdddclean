package com.icmon.module.dashboard.application.impl;

import com.icmon._shared.application.GenericAuthDomainServiceImpl;
import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.dashboard.application.interfaces.ReportService;
import com.icmon.module.dashboard.infrastructure.cache.ReportCacheService;
import com.icmon.module.dashboard.infrastructure.report.excel.ExcelReportGenerator;
import com.icmon.module.dashboard.infrastructure.report.pdf.PDFReportGenerator;
import com.icmon.module.dashboard.presentation.dto.request.ReportRequestDTO;
import com.icmon.module.dashboard.presentation.dto.response.ReportResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl extends GenericAuthDomainServiceImpl implements ReportService {

    private final ExcelReportGenerator excelReportGenerator;
    private final PDFReportGenerator pdfReportGenerator;
    private final ReportCacheService reportCacheService;

    @Override
    public ReportResponseDTO generateDailyReport(ReportRequestDTO request) throws FailedRequestException {
        return generateReport(request, "DAILY");
    }

    @Override
    public ReportResponseDTO generateMonthlyReport(ReportRequestDTO request) throws FailedRequestException {
        return generateReport(request, "MONTHLY");
    }

    @Override
    public ReportResponseDTO generateYearlyReport(ReportRequestDTO request) throws FailedRequestException {
        return generateReport(request, "YEARLY");
    }

    @Override
    public ReportResponseDTO getReportStatus(String reportId) throws FailedRequestException {
        String status = reportCacheService.getReportStatus(reportId);
        ReportResponseDTO dto = new ReportResponseDTO();
        dto.setReportId(reportId);
        dto.setStatus(status != null ? status : "UNKNOWN");
        return dto;
    }

    private ReportResponseDTO generateReport(ReportRequestDTO request, String reportType) {
        String reportId = UUID.randomUUID().toString();
        reportCacheService.updateReportStatus(reportId, "GENERATING");

        byte[] data;
        if ("pdf".equalsIgnoreCase(request.getFormat())) {
            data = pdfReportGenerator.generate(request);
        } else {
            data = excelReportGenerator.generate(request);
        }

        reportCacheService.updateReportStatus(reportId, "COMPLETED");

        ReportResponseDTO dto = new ReportResponseDTO();
        dto.setReportId(reportId);
        dto.setStatus("COMPLETED");
        dto.setReportType(reportType);
        dto.setData(data);
        dto.setFormat(request.getFormat() != null ? request.getFormat() : "excel");
        return dto;
    }
}
