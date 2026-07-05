package com.icmon.module.email.domain;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MEmailTemplateTest {

    @Test
    void shouldRenderSubjectWithVariables() {
        MEmailTemplate template = new MEmailTemplate();
        template.setSubject("ใบเสนอราคา #{quotationNo} - {customerName}");

        String result = template.renderSubject(Map.of("quotationNo", "Q-001", "customerName", "สมชาย"));
        assertEquals("ใบเสนอราคา #Q-001 - สมชาย", result);
    }

    @Test
    void shouldRenderBodyHtmlWithVariables() {
        MEmailTemplate template = new MEmailTemplate();
        template.setBodyHtml("<p>เรียน {customerName}, ใบเสนอราคาของท่าน #{quotationNo}</p>");

        String result = template.renderBodyHtml(Map.of("quotationNo", "Q-001", "customerName", "สมชาย"));
        assertEquals("<p>เรียน สมชาย, ใบเสนอราคาของท่าน #Q-001</p>", result);
    }

    @Test
    void shouldRenderBodyTextWithVariables() {
        MEmailTemplate template = new MEmailTemplate();
        template.setBodyText("เรียน {customerName}, ใบเสนอราคาของท่าน #{quotationNo}");

        String result = template.renderBodyText(Map.of("quotationNo", "Q-001", "customerName", "สมชาย"));
        assertEquals("เรียน สมชาย, ใบเสนอราคาของท่าน #Q-001", result);
    }

    @Test
    void shouldReturnNullWhenBodyHtmlIsNull() {
        MEmailTemplate template = new MEmailTemplate();
        assertNull(template.renderBodyHtml(Map.of("key", "value")));
    }

    @Test
    void shouldReturnNullWhenBodyTextIsNull() {
        MEmailTemplate template = new MEmailTemplate();
        assertNull(template.renderBodyText(Map.of("key", "value")));
    }

    @Test
    void shouldHandleEmptyVariables() {
        MEmailTemplate template = new MEmailTemplate();
        template.setSubject("Hello {name}");
        template.setBodyHtml("<p>Hello {name}</p>");
        template.setBodyText("Hello {name}");

        assertEquals("Hello {name}", template.renderSubject(Map.of()));
        assertEquals("<p>Hello {name}</p>", template.renderBodyHtml(Map.of()));
        assertEquals("Hello {name}", template.renderBodyText(Map.of()));
    }

    @Test
    void shouldHandleMissingVariableGracefully() {
        MEmailTemplate template = new MEmailTemplate();
        template.setSubject("Hello {name}, your order #{orderNo}");

        String result = template.renderSubject(Map.of("name", "สมชาย"));
        assertEquals("Hello สมชาย, your order #{orderNo}", result);
    }
}
