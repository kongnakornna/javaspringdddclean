package com.icmon.module.payment.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "t_receipt")
@Getter
@Setter
public class ReceiptEntity extends GenericBusinessEntity {

    @Column(name = "receipt_no", unique = true, nullable = false, length = 20)
    private String receiptNo;

    @Column(name = "payment_id", nullable = false)
    private UUID paymentId;

    @Column(name = "invoice_id", nullable = false)
    private UUID invoiceId;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "receipt_date", nullable = false)
    private LocalDateTime receiptDate;

    @Column(name = "receipt_type", length = 20)
    private String receiptType;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "amount_in_words_th", columnDefinition = "TEXT")
    private String amountInWordsTh;

    @Column(name = "amount_in_words_en", columnDefinition = "TEXT")
    private String amountInWordsEn;

    @Column(length = 10)
    private String currency;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "issued_by", nullable = false)
    private UUID issuedBy;
}
