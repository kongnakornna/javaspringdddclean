package com.icmon.module.purchase.domain;

import com.icmon.module.purchase.domain.enums.DiscountType;
import com.icmon.module.purchase.domain.enums.PurchaseOrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TPurchaseOrderHeaderTest {

    private TPurchaseOrderHeader poHeader;

    @BeforeEach
    void setUp() {
        poHeader = new TPurchaseOrderHeader();
        poHeader.setStatus(PurchaseOrderStatus.DRAFT);
        poHeader.setTaxRate(new BigDecimal("7.00"));
    }

    @Test
    void testCalculateTotalsWithNoDetails() {
        poHeader.calculateTotals();
        assertEquals(new BigDecimal("0.00"), poHeader.getSubtotal());
        assertEquals(new BigDecimal("0.00"), poHeader.getTaxAmount());
        assertEquals(new BigDecimal("0.00"), poHeader.getTotal());
    }

    @Test
    void testCalculateTotalsWithDetails() {
        TPurchaseOrderDetail detail = new TPurchaseOrderDetail();
        detail.setQuantityOrdered(2);
        detail.setUnitPrice(new BigDecimal("500.00"));
        detail.setDiscount(BigDecimal.ZERO);
        detail.calculatePrices();

        poHeader.getDetails().add(detail);
        poHeader.calculateTotals();

        assertEquals(new BigDecimal("1000.00"), poHeader.getSubtotal());
        assertEquals(new BigDecimal("70.00"), poHeader.getTaxAmount());
        assertEquals(new BigDecimal("1070.00"), poHeader.getTotal());
    }

    @Test
    void testCalculateTotalsWithPercentageDiscount() {
        TPurchaseOrderDetail detail = new TPurchaseOrderDetail();
        detail.setQuantityOrdered(1);
        detail.setUnitPrice(new BigDecimal("1000.00"));
        detail.setDiscount(BigDecimal.ZERO);
        detail.calculatePrices();

        poHeader.getDetails().add(detail);
        poHeader.setDiscountType(DiscountType.PERCENTAGE);
        poHeader.setDiscountValue(new BigDecimal("10"));
        poHeader.calculateTotals();

        assertEquals(new BigDecimal("1000.00"), poHeader.getSubtotal());
        assertEquals(new BigDecimal("70.00"), poHeader.getTaxAmount());
        assertEquals(new BigDecimal("970.00"), poHeader.getTotal());
    }

    @Test
    void testCalculateTotalsWithFixedDiscount() {
        TPurchaseOrderDetail detail = new TPurchaseOrderDetail();
        detail.setQuantityOrdered(1);
        detail.setUnitPrice(new BigDecimal("1000.00"));
        detail.setDiscount(BigDecimal.ZERO);
        detail.calculatePrices();

        poHeader.getDetails().add(detail);
        poHeader.setDiscountType(DiscountType.FIXED);
        poHeader.setDiscountValue(new BigDecimal("50"));
        poHeader.calculateTotals();

        assertEquals(new BigDecimal("1000.00"), poHeader.getSubtotal());
        assertEquals(new BigDecimal("70.00"), poHeader.getTaxAmount());
        assertEquals(new BigDecimal("1020.00"), poHeader.getTotal());
    }

    @Test
    void testCalculateTotalsWithShippingCost() {
        TPurchaseOrderDetail detail = new TPurchaseOrderDetail();
        detail.setQuantityOrdered(1);
        detail.setUnitPrice(new BigDecimal("2000.00"));
        detail.setDiscount(BigDecimal.ZERO);
        detail.calculatePrices();

        poHeader.getDetails().add(detail);
        poHeader.setShippingCost(new BigDecimal("100.00"));
        poHeader.calculateTotals();

        assertEquals(new BigDecimal("2000.00"), poHeader.getSubtotal());
        assertEquals(new BigDecimal("140.00"), poHeader.getTaxAmount());
        assertEquals(new BigDecimal("2240.00"), poHeader.getTotal());
    }

    @Test
    void testDetailCalculatePrices() {
        TPurchaseOrderDetail detail = new TPurchaseOrderDetail();
        detail.setQuantityOrdered(3);
        detail.setUnitPrice(new BigDecimal("250.00"));
        detail.setDiscount(new BigDecimal("50.00"));
        detail.calculatePrices();

        assertEquals(new BigDecimal("750.00"), detail.getTotalPrice());
        assertEquals(new BigDecimal("700.00"), detail.getNetPrice());
    }

    @Test
    void testDetailCalculatePricesNoDiscount() {
        TPurchaseOrderDetail detail = new TPurchaseOrderDetail();
        detail.setQuantityOrdered(5);
        detail.setUnitPrice(new BigDecimal("100.00"));
        detail.calculatePrices();

        assertEquals(new BigDecimal("500.00"), detail.getTotalPrice());
        assertEquals(new BigDecimal("500.00"), detail.getNetPrice());
    }
}
