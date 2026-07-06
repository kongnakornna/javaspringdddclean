package com.icmon.module.document.infrastructure.generator.jasper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JasperReportGeneratorTest {

    private final JasperReportGenerator generator = new JasperReportGenerator();

    @Test
    void testConstructorAndBean() {
        assertNotNull(generator);
    }

    @Test
    void testGeneratePdfWithNonExistentTemplate_shouldThrowException() {
        Map<String, Object> params = new HashMap<>();
        List<Map<String, Object>> data = Collections.singletonList(new HashMap<>());
        assertThrows(Exception.class, () ->
                generator.generatePdf("non_existent.jrxml", params, data));
    }

    @Test
    void testGeneratePdfWithNullData_shouldThrowException() {
        Map<String, Object> params = new HashMap<>();
        assertThrows(Exception.class, () ->
                generator.generatePdf("non_existent.jrxml", params, null));
    }

    @Test
    void testGenerateExcelWithNonExistentTemplate_shouldThrowException() {
        Map<String, Object> params = new HashMap<>();
        List<Map<String, Object>> data = Collections.singletonList(new HashMap<>());
        assertThrows(Exception.class, () ->
                generator.generateExcel("non_existent.jrxml", params, data));
    }
}
