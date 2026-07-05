package com.icmon.module.quotation.infrastructure.entity;

import com.icmon._shared.infrastructure.GenericBusinessEntity;
import com.icmon.module.quotation.domain.enums.QuotationStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "t_quotation")
@EqualsAndHashCode(callSuper = true)
public class QuotationEntity extends GenericBusinessEntity {

    @Column(name = "quotation_no", unique = true, nullable = false, length = 20)
    private String quotationNo;

    @Column(name = "job_id", nullable = false)
    private UUID jobId;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "quotation_date", nullable = false)
    private LocalDateTime quotationDate;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private QuotationStatus status;

    @Column(precision = 15, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "tax_rate", precision = 5, scale = 2)
    private BigDecimal taxRate;

    @Column(name = "tax_amount", precision = 15, scale = 2)
    private BigDecimal taxAmount;

    @Column(name = "discount_type", length = 20)
    private String discountType;

    @Column(name = "discount_value", precision = 15, scale = 2)
    private BigDecimal discountValue;

    @Column(precision = 15, scale = 2)
    private BigDecimal total;

    @Column(name = "amount_in_words_th", columnDefinition = "TEXT")
    private String amountInWordsTh;

    @Column(name = "amount_in_words_en", columnDefinition = "TEXT")
    private String amountInWordsEn;

    @Column(length = 10)
    private String currency;

    @Column(name = "exchange_rate", precision = 10, scale = 4)
    private BigDecimal exchangeRate;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "terms_and_conditions", columnDefinition = "TEXT")
    private String termsAndConditions;

    @Column(name = "approved_by")
    private UUID approvedBy;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "rejected_reason", columnDefinition = "TEXT")
    private String rejectedReason;

    @Column(name = "converted_to_po")
    private Boolean convertedToPo;
}
