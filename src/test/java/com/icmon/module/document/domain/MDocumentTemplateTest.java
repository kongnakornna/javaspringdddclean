package com.icmon.module.document.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MDocumentTemplateTest {

    @Test
    void shouldIncrementVersion() {
        MDocumentTemplate template = new MDocumentTemplate();
        template.setVersion(1);
        template.incrementVersion();
        assertEquals(2, template.getVersion());
    }

    @Test
    void shouldIncrementVersionFromNull() {
        MDocumentTemplate template = new MDocumentTemplate();
        template.incrementVersion();
        assertEquals(1, template.getVersion());
    }

    @Test
    void shouldBeUsableWhenActiveAndHasFilePath() {
        MDocumentTemplate template = new MDocumentTemplate();
        template.setActive(true);
        template.setFilePath("/path/to/template.jasper");
        assertTrue(template.isUsable());
    }

    @Test
    void shouldNotBeUsableWhenInactive() {
        MDocumentTemplate template = new MDocumentTemplate();
        template.setActive(false);
        template.setFilePath("/path/to/template.jasper");
        assertFalse(template.isUsable());
    }

    @Test
    void shouldNotBeUsableWhenNoFilePath() {
        MDocumentTemplate template = new MDocumentTemplate();
        template.setActive(true);
        assertFalse(template.isUsable());
    }

    @Test
    void shouldNotBeUsableWhenActiveIsNull() {
        MDocumentTemplate template = new MDocumentTemplate();
        template.setFilePath("/path/to/template.jasper");
        assertFalse(template.isUsable());
    }
}
