package com.icmon.module.document.presentation.dto.response.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@Schema(description = "\u0e02\u0e49\u0e2d\u0e21\u0e39\u0e25\u0e2a\u0e33\u0e2b\u0e23\u0e31\u0e1a\u0e23\u0e32\u0e22\u0e07\u0e32\u0e19\u0e43\u0e1a\u0e40\u0e2a\u0e23\u0e47\u0e08\u0e23\u0e31\u0e1a\u0e40\u0e07\u0e34\u0e19 // Receipt report data")
public class ReceiptReportDTO {
    private String companyName;
    private String receiptNo;
    private String receiptDate;
    private String customerName;
    private String paymentMethod;
    private BigDecimal amount;
    private String amountInWords;
    private String cashierName;
    private List<ReceiptItemDTO> items;

    @Data @Builder
    public static class ReceiptItemDTO {
        private String description;
        private BigDecimal total;
    }
}
