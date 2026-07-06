package com.icmon.module.weborder.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import com.icmon.module.weborder.domain.enums.OrderSource;
import com.icmon.module.weborder.domain.enums.OrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class TWebOrder extends GenericBusinessClass {

    private String orderNo;
    private UUID cartId;
    private UUID customerId;
    private LocalDateTime orderDate;
    private OrderSource orderSource;
    private OrderStatus status;
    private String paymentStatus;
    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal tax;
    private BigDecimal shippingCost;
    private BigDecimal total;
    private String promotionCode;
    private String shippingAddress;
    private String shippingPhone;
    private String shippingEmail;
    private String trackingNumber;
    private String courier;
    private String notes;
    private String paymentMethod;
    private String paymentTransactionId;
    private LocalDateTime paidAt;
    private LocalDateTime deliveredAt;
    private LocalDateTime cancelledAt;
    private String cancellationReason;

    private List<TWebOrderItem> items = new ArrayList<>();

    public void calculateTotal() {
        this.subtotal = items.stream()
                .map(TWebOrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.total = this.subtotal
                .add(this.tax != null ? this.tax : BigDecimal.ZERO)
                .add(this.shippingCost != null ? this.shippingCost : BigDecimal.ZERO)
                .subtract(this.discount != null ? this.discount : BigDecimal.ZERO);
    }

    public boolean canCancel() {
        return this.status == OrderStatus.PENDING || this.status == OrderStatus.CONFIRMED;
    }

    public void cancel(String reason) {
        if (!canCancel()) {
            throw new IllegalStateException("Cannot cancel order with status: " + this.status);
        }
        this.status = OrderStatus.CANCELLED;
        this.cancellationReason = reason;
        this.cancelledAt = LocalDateTime.now();
    }

    public void changeStatus(OrderStatus newStatus) {
        if (this.status == OrderStatus.DELIVERED || this.status == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Cannot change status of delivered or cancelled order.");
        }
        this.status = newStatus;
        if (newStatus == OrderStatus.DELIVERED) {
            this.deliveredAt = LocalDateTime.now();
        }
    }

    public void markAsPaid(String transactionId) {
        this.paymentStatus = "PAID";
        this.paymentTransactionId = transactionId;
        this.paidAt = LocalDateTime.now();
    }
}
