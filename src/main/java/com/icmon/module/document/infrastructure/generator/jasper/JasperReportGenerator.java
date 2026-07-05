package com.icmon.module.document.infrastructure.generator.jasper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JasperReportGenerator {

    public byte[] generatePDF(String templateCode, String parameters) {
        log.info("Generating PDF via JasperReports for template: {}", templateCode);
        String content = "PDF generated from Jasper template: " + templateCode;
        return content.getBytes(java.nio.charset.StandardCharsets.UTF_8);
    }
}
