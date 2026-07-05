package com.icmon.module.quotation.domain.valueobjects;

import java.util.regex.Pattern;

/*
    มูลค่าเลขที่ใบเสนอราคา / Quotation number value object.
*/
public class QuotationNumber {
    private static final Pattern PATTERN = Pattern.compile("^QT-\\d{4}-\\d{4}$");
    private final String value;

    public QuotationNumber(String value) {
        if (value == null || !PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid quotation number format: " + value);
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
