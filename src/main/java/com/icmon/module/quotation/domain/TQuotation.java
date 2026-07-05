package com.icmon.module.quotation.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import com.icmon.module.quotation.domain.enums.QuotationStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*
    โดเมนใบเสนอราคา / Quotation domain entity.
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class TQuotation extends GenericBusinessClass {

    private String quotationNo;
    private UUID jobId;
    private UUID customerId;
    private LocalDateTime quotationDate;
    private LocalDateTime expiryDate;
    private QuotationStatus status;
    private BigDecimal subtotal;
    private BigDecimal taxRate;
    private BigDecimal taxAmount;
    private String discountType;
    private BigDecimal discountValue;
    private BigDecimal total;
    private String amountInWordsTh;
    private String amountInWordsEn;
    private String currency;
    private BigDecimal exchangeRate;
    private String notes;
    private String termsAndConditions;
    private UUID approvedBy;
    private LocalDateTime approvedAt;
    private String rejectedReason;
    private Boolean convertedToPo;

    private List<TQuotationPart> parts = new ArrayList<>();
    private List<TQuotationService> services = new ArrayList<>();

    /*
        ฟังก์ชันนี้คำนวณยอดรวมของใบเสนอราคา
        This function calculates the total amounts.
    */
    public void calculateTotals() {
        BigDecimal partsTotal = parts.stream()
                .map(TQuotationPart::getNetPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal servicesTotal = services.stream()
                .map(TQuotationService::getNetPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.subtotal = partsTotal.add(servicesTotal).setScale(2, RoundingMode.HALF_UP);

        if (this.taxRate != null) {
            BigDecimal taxRateDecimal = this.taxRate.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
            this.taxAmount = this.subtotal.multiply(taxRateDecimal).setScale(2, RoundingMode.HALF_UP);
        } else {
            this.taxAmount = BigDecimal.ZERO.setScale(2);
        }

        BigDecimal discountAmount = BigDecimal.ZERO.setScale(2);
        if ("PERCENTAGE".equalsIgnoreCase(this.discountType) && this.discountValue != null) {
            BigDecimal rate = this.discountValue.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
            discountAmount = this.subtotal.multiply(rate).setScale(2, RoundingMode.HALF_UP);
        } else if ("FIXED".equalsIgnoreCase(this.discountType) && this.discountValue != null) {
            discountAmount = this.discountValue.setScale(2, RoundingMode.HALF_UP);
        }

        this.total = this.subtotal.add(this.taxAmount).subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);
    }

    /*
        ฟังก์ชันนี้ตรวจสอบว่าใบเสนอราคาสามารถอนุมัติได้หรือไม่
        This function checks if the quotation can be approved.
    */
    public boolean canApprove() {
        return this.status == QuotationStatus.PENDING || this.status == QuotationStatus.DRAFT;
    }

    /*
        ฟังก์ชันนี้ตรวจสอบว่าใบเสนอราคาหมดอายุแล้วหรือไม่
        This function checks if the quotation has expired.
    */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiryDate);
    }

    /*
        ฟังก์ชันนี้เปลี่ยนสถานะเป็น APPROVED พร้อมบันทึกข้อมูลผู้อนุมัติ
        This function changes status to APPROVED and records the approver.
    */
    public void approve(UUID approverId) {
        if (!canApprove()) {
            throw new IllegalStateException("Quotation cannot be approved in status: " + this.status);
        }
        if (isExpired()) {
            throw new IllegalStateException("Quotation has expired and cannot be approved.");
        }
        this.status = QuotationStatus.APPROVED;
        this.approvedBy = approverId;
        this.approvedAt = LocalDateTime.now();
    }

    /*
        ฟังก์ชันนี้เปลี่ยนสถานะเป็น REJECTED พร้อมบันทึกเหตุผล
        This function changes status to REJECTED and records the reason.
    */
    public void reject(String reason) {
        if (this.status == QuotationStatus.APPROVED || this.status == QuotationStatus.CONVERTED) {
            throw new IllegalStateException("Cannot reject already approved or converted quotation.");
        }
        this.status = QuotationStatus.REJECTED;
        this.rejectedReason = reason;
    }
}
