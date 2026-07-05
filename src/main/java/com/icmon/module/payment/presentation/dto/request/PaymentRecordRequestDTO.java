package com.icmon.module.payment.presentation.dto.request;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class PaymentRecordRequestDTO {
    private UUID invoiceId;
    private UUID paymentMethodId;
    private BigDecimal amount;
    private BigDecimal amountReceived;
    private BigDecimal changeAmount;
    private String currency;
    private BigDecimal exchangeRate;
    private String referenceNumber;
    private String bankName;
    private String chequeNumber;
    private String chequeBank;
    private LocalDate chequeDate;
    private String notes;
    private UUID receivedBy;
}
