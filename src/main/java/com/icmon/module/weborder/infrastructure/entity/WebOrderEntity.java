package com.icmon.module.weborder.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import com.icmon.module.weborder.domain.enums.OrderSource;
import com.icmon.module.weborder.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "t_web_order")
@EqualsAndHashCode(callSuper = true)
public class WebOrderEntity extends GenericBusinessEntity {

    @Column(name = "order_no", unique = true, nullable = false, length = 30)
    private String orderNo;

    @Column(name = "cart_id")
    private UUID cartId;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_source", length = 20)
    private OrderSource orderSource;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private OrderStatus status;

    @Column(name = "payment_status", length = 20)
    private String paymentStatus;

    @Column(precision = 15, scale = 2)
    private BigDecimal subtotal;

    @Column(precision = 15, scale = 2)
    private BigDecimal discount;

    @Column(precision = 15, scale = 2)
    private BigDecimal tax;

    @Column(name = "shipping_cost", precision = 15, scale = 2)
    private BigDecimal shippingCost;

    @Column(precision = 15, scale = 2)
    private BigDecimal total;

    @Column(name = "promotion_code", length = 50)
    private String promotionCode;

    @Column(name = "shipping_address", columnDefinition = "TEXT", nullable = false)
    private String shippingAddress;

    @Column(name = "shipping_phone", length = 20)
    private String shippingPhone;

    @Column(name = "shipping_email", length = 100)
    private String shippingEmail;

    @Column(name = "tracking_number", length = 50)
    private String trackingNumber;

    @Column(length = 50)
    private String courier;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "payment_method", length = 30)
    private String paymentMethod;

    @Column(name = "payment_transaction_id", length = 100)
    private String paymentTransactionId;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @Column(name = "cancellation_reason", columnDefinition = "TEXT")
    private String cancellationReason;
}
