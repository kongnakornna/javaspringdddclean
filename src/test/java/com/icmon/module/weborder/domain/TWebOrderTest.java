package com.icmon.module.weborder.domain;

import com.icmon.module.weborder.domain.enums.OrderSource;
import com.icmon.module.weborder.domain.enums.OrderStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TWebOrderTest {

    @Test
    void testCalculateTotal() {
        TWebOrder order = new TWebOrder();
        order.setSubtotal(new BigDecimal("1000.00"));
        order.setTax(new BigDecimal("70.00"));
        order.setShippingCost(new BigDecimal("50.00"));
        order.setDiscount(new BigDecimal("100.00"));

        TWebOrderItem item = new TWebOrderItem();
        item.setItemId(UUID.randomUUID());
        item.setQuantity(1);
        item.setUnitPrice(new BigDecimal("1000.00"));
        item.setTotalPrice(new BigDecimal("1000.00"));
        order.getItems().add(item);

        order.calculateTotal();
        assertEquals(new BigDecimal("1000.00"), order.getSubtotal());
        assertEquals(new BigDecimal("1020.00"), order.getTotal());
    }

    @Test
    void testCanCancel() {
        TWebOrder order = new TWebOrder();
        order.setStatus(OrderStatus.PENDING);
        assertTrue(order.canCancel());

        order.setStatus(OrderStatus.CONFIRMED);
        assertTrue(order.canCancel());

        order.setStatus(OrderStatus.SHIPPED);
        assertFalse(order.canCancel());

        order.setStatus(OrderStatus.DELIVERED);
        assertFalse(order.canCancel());

        order.setStatus(OrderStatus.CANCELLED);
        assertFalse(order.canCancel());
    }

    @Test
    void testCancel() {
        TWebOrder order = new TWebOrder();
        order.setStatus(OrderStatus.PENDING);
        order.cancel("ลูกค้าขอยกเลิก");
        assertEquals(OrderStatus.CANCELLED, order.getStatus());
        assertEquals("ลูกค้าขอยกเลิก", order.getCancellationReason());
        assertNotNull(order.getCancelledAt());
    }

    @Test
    void testCancelThrowsWhenNotAllowed() {
        TWebOrder order = new TWebOrder();
        order.setStatus(OrderStatus.SHIPPED);
        assertThrows(IllegalStateException.class, () -> order.cancel("test"));
    }

    @Test
    void testChangeStatus() {
        TWebOrder order = new TWebOrder();
        order.setStatus(OrderStatus.CONFIRMED);
        order.changeStatus(OrderStatus.SHIPPED);
        assertEquals(OrderStatus.SHIPPED, order.getStatus());
    }

    @Test
    void testChangeStatusToDelivered() {
        TWebOrder order = new TWebOrder();
        order.setStatus(OrderStatus.SHIPPED);
        order.changeStatus(OrderStatus.DELIVERED);
        assertEquals(OrderStatus.DELIVERED, order.getStatus());
        assertNotNull(order.getDeliveredAt());
    }

    @Test
    void testChangeStatusThrowsWhenDelivered() {
        TWebOrder order = new TWebOrder();
        order.setStatus(OrderStatus.DELIVERED);
        assertThrows(IllegalStateException.class, () -> order.changeStatus(OrderStatus.CANCELLED));
    }

    @Test
    void testMarkAsPaid() {
        TWebOrder order = new TWebOrder();
        order.markAsPaid("TXN-12345");
        assertEquals("PAID", order.getPaymentStatus());
        assertEquals("TXN-12345", order.getPaymentTransactionId());
        assertNotNull(order.getPaidAt());
    }

    @Test
    void testDefaultValues() {
        TWebOrder order = new TWebOrder();
        assertTrue(order.getItems().isEmpty());
    }
}
