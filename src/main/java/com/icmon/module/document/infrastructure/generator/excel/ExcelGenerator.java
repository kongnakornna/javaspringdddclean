package com.icmon.module.document.infrastructure.generator.excel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExcelGenerator {

    public byte[] generateExcel(String templateCode, String parameters) {
        log.info("Generating Excel via Apache POI for template: {}", templateCode);
        StringBuilder sb = new StringBuilder();
        sb.append("Excel Report\n");
        sb.append("Template: ").append(templateCode).append("\n");
        return sb.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8);
    }
}
