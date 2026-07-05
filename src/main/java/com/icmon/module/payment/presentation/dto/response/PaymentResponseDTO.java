package com.icmon.module.payment.presentation.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PaymentResponseDTO {
    private UUID id;
    private String paymentNo;
    private UUID invoiceId;
    private UUID jobId;
    private UUID customerId;
    private LocalDateTime paymentDate;
    private UUID paymentMethodId;
    private BigDecimal amount;
    private BigDecimal amountReceived;
    private BigDecimal changeAmount;
    private String currency;
    private BigDecimal exchangeRate;
    private String status;
    private String referenceNumber;
    private String bankName;
    private String chequeNumber;
    private String chequeBank;
    private LocalDate chequeDate;
    private String notes;
    private UUID receivedBy;
    private UUID approvedBy;
    private LocalDateTime approvedAt;
    private BigDecimal refundedAmount;
    private LocalDateTime refundedAt;
    private ReceiptResponseDTO receipt;
    private LocalDateTime createdAt;
}
