package com.icmon.module.purchase.domain.valueobjects;

import lombok.Getter;

import java.util.regex.Pattern;

@Getter
public class PONumber {

    private static final Pattern PO_NO_PATTERN = Pattern.compile("^PO-\\d{4}-\\d{4}$");

    private final String value;

    public PONumber(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("ใบสั่งซื้อต้องมีหมายเลข | Purchase order number must not be null or blank");
        }
        if (!PO_NO_PATTERN.matcher(value.trim()).matches()) {
            throw new IllegalArgumentException("รูปแบบหมายเลขใบสั่งซื้อไม่ถูกต้อง (PO-YYYY-NNNN) | Invalid PO number format");
        }
        this.value = value.trim();
    }

    public static PONumber of(final String value) {
        return new PONumber(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PONumber poNumber = (PONumber) o;
        return value.equals(poNumber.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
