package com.icmon.module.dashboard.application.impl;

import com.icmon._shared.application.GenericAuthDomainServiceImpl;
import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.dashboard.application.interfaces.ExportService;
import com.icmon.module.dashboard.infrastructure.report.excel.ExcelReportGenerator;
import com.icmon.module.dashboard.infrastructure.report.pdf.PDFReportGenerator;
import com.icmon.module.dashboard.presentation.dto.request.ExportRequestDTO;
import com.icmon.module.dashboard.presentation.dto.request.ReportRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExportServiceImpl extends GenericAuthDomainServiceImpl implements ExportService {

    private final ExcelReportGenerator excelReportGenerator;
    private final PDFReportGenerator pdfReportGenerator;

    @Override
    public byte[] exportToExcel(ExportRequestDTO request) throws FailedRequestException {
        ReportRequestDTO reportRequest = new ReportRequestDTO();
        reportRequest.setReportType(request.getReportType());
        reportRequest.setFormat("excel");
        reportRequest.setPeriod(request.getPeriod());
        return excelReportGenerator.generate(reportRequest);
    }

    @Override
    public byte[] exportToPDF(ExportRequestDTO request) throws FailedRequestException {
        ReportRequestDTO reportRequest = new ReportRequestDTO();
        reportRequest.setReportType(request.getReportType());
        reportRequest.setFormat("pdf");
        reportRequest.setPeriod(request.getPeriod());
        return pdfReportGenerator.generate(reportRequest);
    }

    @Override
    public byte[] exportToCSV(ExportRequestDTO request) throws FailedRequestException {
        ReportRequestDTO reportRequest = new ReportRequestDTO();
        reportRequest.setReportType(request.getReportType());
        reportRequest.setFormat("csv");
        reportRequest.setPeriod(request.getPeriod());
        return pdfReportGenerator.generate(reportRequest);
    }
}
