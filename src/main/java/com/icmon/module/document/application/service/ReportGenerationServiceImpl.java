package com.icmon.module.document.application.service;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.document.application.interfaces.ReportGenerationService;
import com.icmon.module.document.infrastructure.generator.excel.ExcelGenerator;
import com.icmon.module.document.infrastructure.generator.jasper.JasperReportGenerator;
import com.icmon.module.document.infrastructure.generator.pdf.PDFGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportGenerationServiceImpl implements ReportGenerationService {

    private final JasperReportGenerator jasperReportGenerator;
    private final ExcelGenerator excelGenerator;
    private final PDFGenerator pdfGenerator;

    @Override
    public byte[] generatePDF(String templateCode, String parameters) throws SystemGlobalException {
        log.info("Generating PDF report for template: {}", templateCode);
        return jasperReportGenerator.generatePDF(templateCode, parameters);
    }

    @Override
    public byte[] generateExcel(String templateCode, String parameters) throws SystemGlobalException {
        log.info("Generating Excel report for template: {}", templateCode);
        return excelGenerator.generateExcel(templateCode, parameters);
    }
}
