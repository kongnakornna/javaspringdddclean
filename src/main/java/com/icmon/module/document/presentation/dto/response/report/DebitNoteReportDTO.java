package com.icmon.module.document.presentation.dto.response.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@Schema(description = "\u0e02\u0e49\u0e2d\u0e21\u0e39\u0e25\u0e2a\u0e33\u0e2b\u0e23\u0e31\u0e1a\u0e23\u0e32\u0e22\u0e07\u0e32\u0e19\u0e43\u0e1a\u0e40\u0e1e\u0e34\u0e48\u0e21\u0e2b\u0e19\u0e35\u0e49 // Debit Note report data")
public class DebitNoteReportDTO {
    private String companyName;
    private String debitNoteNo;
    private String debitNoteDate;
    private String referenceInvoiceNo;
    private String customerName;
    private String reason;
    private BigDecimal totalAmount;
    private String approvedBy;
    private List<DebitNoteItemDTO> items;

    @Data @Builder
    public static class DebitNoteItemDTO {
        private Integer lineNo;
        private String description;
        private BigDecimal amount;
    }
}
