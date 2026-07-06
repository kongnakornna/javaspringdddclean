package com.icmon.module.document.presentation.dto.response.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@Schema(description = "\u0e02\u0e49\u0e2d\u0e21\u0e39\u0e25\u0e2a\u0e33\u0e2b\u0e23\u0e31\u0e1a\u0e23\u0e32\u0e22\u0e07\u0e32\u0e19\u0e43\u0e1a\u0e2a\u0e31\u0e48\u0e07\u0e0b\u0e37\u0e49\u0e2d // Purchase Order report data")
public class PurchaseOrderReportDTO {
    private String companyName;
    private String companyAddress;
    private String poNo;
    private String poDate;
    private String supplierName;
    private String supplierAddress;
    private String supplierPhone;
    private String supplierTaxId;
    private String deliveryDate;
    private String paymentTerms;
    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal taxAmount;
    private BigDecimal grandTotal;
    private String remark;
    private String createdBy;
    private List<POItemDTO> items;

    @Data @Builder
    public static class POItemDTO {
        private Integer lineNo;
        private String partCode;
        private String description;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal totalPrice;
    }
}
