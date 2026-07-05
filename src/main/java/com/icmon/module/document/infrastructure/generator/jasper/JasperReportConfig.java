package com.icmon.module.document.infrastructure.generator.jasper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasperReportConfig {

    @Bean
    public String jasperReportsDir() {
        return "classpath:reports/";
    }
}
