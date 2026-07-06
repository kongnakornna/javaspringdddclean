package com.icmon.module.document.infrastructure.generator.jasper;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class JasperReportGenerator {

    private static final String TEMPLATE_DIR = "static/template/jrxml/";

    public byte[] generatePdf(String templatePath, Map<String, Object> parameters, List<?> dataList) throws JRException {
        try {
            InputStream inputStream = new ClassPathResource(TEMPLATE_DIR + templatePath).getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JRDataSource dataSource = (dataList != null && !dataList.isEmpty())
                    ? new JRBeanCollectionDataSource(dataList)
                    : new net.sf.jasperreports.engine.JREmptyDataSource();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            configuration.setMetadataTitle("ICMON Report");
            configuration.setMetadataAuthor("ICMON System");
            exporter.setConfiguration(configuration);
            exporter.exportReport();
            return outputStream.toByteArray();
        } catch (Exception e) {
            log.error("Failed to generate PDF for template: {}", templatePath, e);
            throw new JRException("Failed to generate PDF for template: " + templatePath, e);
        }
    }

    public byte[] generatePDF(String templateCode, String parameters) {
        log.warn("generatePDF(String, String) is deprecated, use generatePdf(String, Map, List) instead");
        try {
            return generatePdf(templateCode, new java.util.HashMap<>(), new java.util.ArrayList<>());
        } catch (JRException e) {
            log.error("Failed to generate PDF", e);
            return new byte[0];
        }
    }

    public byte[] generateExcel(String templatePath, Map<String, Object> parameters, List<?> dataList) throws JRException {
        try {
            InputStream inputStream = new ClassPathResource(TEMPLATE_DIR + templatePath).getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JRDataSource dataSource = (dataList != null && !dataList.isEmpty())
                    ? new JRBeanCollectionDataSource(dataList)
                    : new net.sf.jasperreports.engine.JREmptyDataSource();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            exporter.exportReport();
            return outputStream.toByteArray();
        } catch (Exception e) {
            log.error("Failed to generate Excel for template: {}", templatePath, e);
            throw new JRException("Failed to generate Excel for template: " + templatePath, e);
        }
    }
}
