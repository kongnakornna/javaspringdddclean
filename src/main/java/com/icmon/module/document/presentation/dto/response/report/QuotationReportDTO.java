package com.icmon.module.document.presentation.dto.response.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@Schema(description = "\u0e02\u0e49\u0e2d\u0e21\u0e39\u0e25\u0e2a\u0e33\u0e2b\u0e23\u0e31\u0e1a\u0e23\u0e32\u0e22\u0e07\u0e32\u0e19\u0e43\u0e1a\u0e40\u0e2a\u0e19\u0e2d\u0e23\u0e32\u0e04\u0e32 // Quotation report data")
public class QuotationReportDTO {

    @Schema(description = "\u0e0a\u0e37\u0e48\u0e2d\u0e1a\u0e23\u0e34\u0e29\u0e31\u0e17 // Company name")
    private String companyName;

    @Schema(description = "\u0e17\u0e35\u0e48\u0e2d\u0e22\u0e39\u0e48\u0e1a\u0e23\u0e34\u0e29\u0e31\u0e17 // Company address")
    private String companyAddress;

    @Schema(description = "\u0e40\u0e1a\u0e2d\u0e23\u0e4c\u0e42\u0e17\u0e23\u0e1a\u0e23\u0e34\u0e29\u0e31\u0e17 // Company phone")
    private String companyPhone;

    @Schema(description = "\u0e40\u0e25\u0e02\u0e1b\u0e23\u0e30\u0e08\u0e33\u0e15\u0e31\u0e27\u0e1c\u0e39\u0e49\u0e40\u0e2a\u0e35\u0e22\u0e20\u0e32\u0e29\u0e35 // Tax ID")
    private String companyTaxId;

    @Schema(description = "\u0e42\u0e25\u0e42\u0e01\u0e49\u0e1a\u0e23\u0e34\u0e29\u0e31\u0e17 // Company logo (byte array)")
    private byte[] companyLogo;

    @Schema(description = "\u0e40\u0e25\u0e02\u0e17\u0e35\u0e48\u0e43\u0e1a\u0e40\u0e2a\u0e19\u0e2d\u0e23\u0e32\u0e04\u0e32 // Quotation number")
    private String quotationNo;

    @Schema(description = "\u0e27\u0e31\u0e19\u0e17\u0e35\u0e48\u0e43\u0e1a\u0e40\u0e2a\u0e19\u0e2d\u0e23\u0e32\u0e04\u0e32 // Quotation date")
    private String quotationDate;

    @Schema(description = "\u0e27\u0e31\u0e19\u0e2b\u0e21\u0e14\u0e2d\u0e32\u0e22\u0e38 // Expiry date")
    private String expiryDate;

    @Schema(description = "\u0e0a\u0e37\u0e48\u0e2d\u0e25\u0e39\u0e01\u0e04\u0e49\u0e32 // Customer name")
    private String customerName;

    @Schema(description = "\u0e17\u0e35\u0e48\u0e2d\u0e22\u0e39\u0e48\u0e25\u0e39\u0e01\u0e04\u0e49\u0e32 // Customer address")
    private String customerAddress;

    @Schema(description = "\u0e40\u0e1a\u0e2d\u0e23\u0e4c\u0e42\u0e17\u0e23\u0e25\u0e39\u0e01\u0e04\u0e49\u0e32 // Customer phone")
    private String customerPhone;

    @Schema(description = "\u0e40\u0e25\u0e02\u0e17\u0e35\u0e48\u0e43\u0e1a\u0e07\u0e32\u0e19 // Job number")
    private String jobNo;

    @Schema(description = "\u0e17\u0e30\u0e40\u0e1a\u0e35\u0e22\u0e19\u0e23\u0e16 // License plate")
    private String licensePlate;

    @Schema(description = "\u0e23\u0e38\u0e48\u0e19\u0e23\u0e16 // Car model")
    private String carModel;

    @Schema(description = "\u0e22\u0e2d\u0e14\u0e01\u0e48\u0e2d\u0e19\u0e04\u0e34\u0e14\u0e20\u0e32\u0e29\u0e35 // Subtotal")
    private BigDecimal subtotal;

    @Schema(description = "\u0e2d\u0e31\u0e15\u0e23\u0e32\u0e20\u0e32\u0e29\u0e35 // Tax rate")
    private BigDecimal taxRate;

    @Schema(description = "\u0e08\u0e33\u0e19\u0e27\u0e19\u0e20\u0e32\u0e29\u0e35 // Tax amount")
    private BigDecimal taxAmount;

    @Schema(description = "\u0e2a\u0e48\u0e27\u0e19\u0e25\u0e14 // Discount")
    private BigDecimal discount;

    @Schema(description = "\u0e22\u0e2d\u0e14\u0e2a\u0e38\u0e17\u0e18\u0e34 // Grand total")
    private BigDecimal grandTotal;

    @Schema(description = "\u0e08\u0e33\u0e19\u0e27\u0e19\u0e40\u0e07\u0e34\u0e19\u0e40\u0e1b\u0e47\u0e19\u0e15\u0e31\u0e27\u0e2d\u0e31\u0e01\u0e29\u0e23 (\u0e20\u0e32\u0e29\u0e32\u0e44\u0e17\u0e22) // Amount in words (Thai)")
    private String amountInWordsTh;

    @Schema(description = "\u0e08\u0e33\u0e19\u0e27\u0e19\u0e40\u0e07\u0e34\u0e19\u0e40\u0e1b\u0e47\u0e19\u0e15\u0e31\u0e27\u0e2d\u0e31\u0e01\u0e29\u0e23 (\u0e2d\u0e31\u0e07\u0e01\u0e24\u0e29) // Amount in words (English)")
    private String amountInWordsEn;

    @Schema(description = "\u0e2b\u0e21\u0e32\u0e22\u0e40\u0e2b\u0e15\u0e38 // Remark")
    private String remark;

    @Schema(description = "\u0e1c\u0e39\u0e49\u0e2a\u0e23\u0e49\u0e32\u0e07 // Created by")
    private String createdBy;

    @Schema(description = "\u0e23\u0e32\u0e22\u0e01\u0e32\u0e23\u0e2a\u0e34\u0e19\u0e04\u0e49\u0e32 // List of items")
    private List<QuotationItemDTO> items;

    @Data
    @Builder
    @Schema(description = "\u0e23\u0e32\u0e22\u0e01\u0e32\u0e23\u0e2a\u0e34\u0e19\u0e04\u0e49\u0e32\u0e43\u0e19\u0e43\u0e1a\u0e40\u0e2a\u0e19\u0e2d\u0e23\u0e32\u0e04\u0e32 // Quotation line item")
    public static class QuotationItemDTO {
        private Integer lineNo;
        private String partCode;
        private String description;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal totalPrice;
    }
}
