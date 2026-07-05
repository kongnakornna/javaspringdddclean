package com.icmon.module.payment.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "t_payment")
@Getter
@Setter
public class PaymentEntity extends GenericBusinessEntity {

    @Column(name = "payment_no", unique = true, nullable = false, length = 20)
    private String paymentNo;

    @Column(name = "invoice_id", nullable = false)
    private UUID invoiceId;

    @Column(name = "job_id")
    private UUID jobId;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    @Column(name = "payment_method_id", nullable = false)
    private UUID paymentMethodId;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "amount_received", nullable = false, precision = 15, scale = 2)
    private BigDecimal amountReceived;

    @Column(name = "change_amount", precision = 15, scale = 2)
    private BigDecimal changeAmount;

    @Column(length = 10)
    private String currency;

    @Column(name = "exchange_rate", precision = 10, scale = 4)
    private BigDecimal exchangeRate;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(name = "reference_number", length = 50)
    private String referenceNumber;

    @Column(name = "bank_name", length = 100)
    private String bankName;

    @Column(name = "cheque_number", length = 50)
    private String chequeNumber;

    @Column(name = "cheque_bank", length = 100)
    private String chequeBank;

    @Column(name = "cheque_date")
    private LocalDate chequeDate;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "received_by", nullable = false)
    private UUID receivedBy;

    @Column(name = "approved_by")
    private UUID approvedBy;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "refunded_amount", precision = 15, scale = 2)
    private BigDecimal refundedAmount;

    @Column(name = "refunded_at")
    private LocalDateTime refundedAt;
}
