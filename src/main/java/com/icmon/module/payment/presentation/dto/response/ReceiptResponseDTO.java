package com.icmon.module.payment.presentation.dto.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ReceiptResponseDTO {
    private UUID id;
    private String receiptNo;
    private UUID paymentId;
    private UUID invoiceId;
    private UUID customerId;
    private LocalDateTime receiptDate;
    private String receiptType;
    private BigDecimal amount;
    private String amountInWordsTh;
    private String amountInWordsEn;
    private String currency;
    private String status;
    private String notes;
    private UUID issuedBy;
    private LocalDateTime createdAt;
}
