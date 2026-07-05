package com.icmon.module.quotation.domain;

import com.icmon.module.quotation.domain.enums.QuotationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TQuotationTest {

    private TQuotation quotation;

    @BeforeEach
    void setUp() {
        quotation = new TQuotation();
        quotation.setStatus(QuotationStatus.DRAFT);
        quotation.setTaxRate(new BigDecimal("7.00"));
        quotation.setExpiryDate(LocalDateTime.now().plusDays(7));
    }

    @Test
    void testCalculateTotalsWithNoItems() {
        quotation.calculateTotals();
        assertEquals(new BigDecimal("0.00"), quotation.getSubtotal());
        assertEquals(new BigDecimal("0.00"), quotation.getTaxAmount());
        assertEquals(new BigDecimal("0.00"), quotation.getTotal());
    }

    @Test
    void testCalculateTotalsWithParts() {
        TQuotationPart part = new TQuotationPart();
        part.setQuantity(2);
        part.setUnitPrice(new BigDecimal("500.00"));
        part.setDiscount(BigDecimal.ZERO);
        part.calculatePrices();

        quotation.getParts().add(part);
        quotation.calculateTotals();

        assertEquals(new BigDecimal("1000.00"), quotation.getSubtotal());
        assertEquals(new BigDecimal("70.00"), quotation.getTaxAmount());
        assertEquals(new BigDecimal("1070.00"), quotation.getTotal());
    }

    @Test
    void testCalculateTotalsWithServices() {
        TQuotationService service = new TQuotationService();
        service.setQuantity(1);
        service.setUnitPrice(new BigDecimal("1500.00"));
        service.setDiscount(BigDecimal.ZERO);
        service.calculatePrices();

        quotation.getServices().add(service);
        quotation.calculateTotals();

        assertEquals(new BigDecimal("1500.00"), quotation.getSubtotal());
        assertEquals(new BigDecimal("105.00"), quotation.getTaxAmount());
        assertEquals(new BigDecimal("1605.00"), quotation.getTotal());
    }

    @Test
    void testCalculateTotalsWithPercentageDiscount() {
        TQuotationPart part = new TQuotationPart();
        part.setQuantity(1);
        part.setUnitPrice(new BigDecimal("1000.00"));
        part.setDiscount(BigDecimal.ZERO);
        part.calculatePrices();

        quotation.getParts().add(part);
        quotation.setDiscountType("PERCENTAGE");
        quotation.setDiscountValue(new BigDecimal("10"));
        quotation.calculateTotals();

        assertEquals(new BigDecimal("1000.00"), quotation.getSubtotal());
        assertEquals(new BigDecimal("70.00"), quotation.getTaxAmount());
        assertEquals(new BigDecimal("970.00"), quotation.getTotal());
    }

    @Test
    void testCalculateTotalsWithFixedDiscount() {
        TQuotationPart part = new TQuotationPart();
        part.setQuantity(1);
        part.setUnitPrice(new BigDecimal("1000.00"));
        part.setDiscount(BigDecimal.ZERO);
        part.calculatePrices();

        quotation.getParts().add(part);
        quotation.setDiscountType("FIXED");
        quotation.setDiscountValue(new BigDecimal("100"));
        quotation.calculateTotals();

        assertEquals(new BigDecimal("1000.00"), quotation.getSubtotal());
        assertEquals(new BigDecimal("70.00"), quotation.getTaxAmount());
        assertEquals(new BigDecimal("970.00"), quotation.getTotal());
    }

    @Test
    void testCanApproveWhenDraft() {
        quotation.setStatus(QuotationStatus.DRAFT);
        assertTrue(quotation.canApprove());
    }

    @Test
    void testCanApproveWhenPending() {
        quotation.setStatus(QuotationStatus.PENDING);
        assertTrue(quotation.canApprove());
    }

    @Test
    void testCanApproveWhenApproved() {
        quotation.setStatus(QuotationStatus.APPROVED);
        assertFalse(quotation.canApprove());
    }

    @Test
    void testCanApproveWhenRejected() {
        quotation.setStatus(QuotationStatus.REJECTED);
        assertFalse(quotation.canApprove());
    }

    @Test
    void testIsExpired() {
        quotation.setExpiryDate(LocalDateTime.now().minusDays(1));
        assertTrue(quotation.isExpired());
    }

    @Test
    void testIsNotExpired() {
        quotation.setExpiryDate(LocalDateTime.now().plusDays(1));
        assertFalse(quotation.isExpired());
    }

    @Test
    void testApproveSuccess() {
        UUID approverId = UUID.randomUUID();
        quotation.setStatus(QuotationStatus.PENDING);
        quotation.setExpiryDate(LocalDateTime.now().plusDays(7));

        quotation.approve(approverId);

        assertEquals(QuotationStatus.APPROVED, quotation.getStatus());
        assertEquals(approverId, quotation.getApprovedBy());
        assertNotNull(quotation.getApprovedAt());
    }

    @Test
    void testApproveFailsWhenAlreadyApproved() {
        quotation.setStatus(QuotationStatus.APPROVED);
        assertThrows(IllegalStateException.class, () -> quotation.approve(UUID.randomUUID()));
    }

    @Test
    void testApproveFailsWhenExpired() {
        quotation.setStatus(QuotationStatus.PENDING);
        quotation.setExpiryDate(LocalDateTime.now().minusDays(1));
        assertThrows(IllegalStateException.class, () -> quotation.approve(UUID.randomUUID()));
    }

    @Test
    void testRejectSuccess() {
        quotation.setStatus(QuotationStatus.PENDING);
        quotation.reject("Customer declined");

        assertEquals(QuotationStatus.REJECTED, quotation.getStatus());
        assertEquals("Customer declined", quotation.getRejectedReason());
    }

    @Test
    void testRejectFailsWhenAlreadyApproved() {
        quotation.setStatus(QuotationStatus.APPROVED);
        assertThrows(IllegalStateException.class, () -> quotation.reject("Reason"));
    }

    @Test
    void testRejectFailsWhenConverted() {
        quotation.setStatus(QuotationStatus.CONVERTED);
        assertThrows(IllegalStateException.class, () -> quotation.reject("Reason"));
    }

    @Test
    void testPartCalculatePrices() {
        TQuotationPart part = new TQuotationPart();
        part.setQuantity(3);
        part.setUnitPrice(new BigDecimal("250.00"));
        part.setDiscount(new BigDecimal("50.00"));
        part.calculatePrices();

        assertEquals(new BigDecimal("750.00"), part.getTotalPrice());
        assertEquals(new BigDecimal("700.00"), part.getNetPrice());
    }

    @Test
    void testServiceCalculatePrices() {
        TQuotationService service = new TQuotationService();
        service.setQuantity(2);
        service.setUnitPrice(new BigDecimal("1000.00"));
        service.setDiscount(new BigDecimal("100.00"));
        service.calculatePrices();

        assertEquals(new BigDecimal("2000.00"), service.getTotalPrice());
        assertEquals(new BigDecimal("1900.00"), service.getNetPrice());
    }
}
