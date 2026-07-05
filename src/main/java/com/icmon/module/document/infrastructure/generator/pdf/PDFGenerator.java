package com.icmon.module.document.infrastructure.generator.pdf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PDFGenerator {

    public byte[] generatePDF(String content) {
        log.info("Generating plain PDF");
        return content.getBytes(java.nio.charset.StandardCharsets.UTF_8);
    }
}
