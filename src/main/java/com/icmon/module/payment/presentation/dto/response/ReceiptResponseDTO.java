package com.icmon.module.payment.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "ข้อมูลใบเสร็จ - Receipt Response")
public class ReceiptResponseDTO {
    @Schema(description = "รหัสใบเสร็จ - Receipt ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "เลขที่ใบเสร็จ - Receipt Number", example = "RCP-20240115-001")
    private String receiptNo;

    @Schema(description = "รหัสการชำระเงิน - Payment ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID paymentId;

    @Schema(description = "รหัสใบแจ้งหนี้ - Invoice ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID invoiceId;

    @Schema(description = "รหัสลูกค้า - Customer ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID customerId;

    @Schema(description = "วันที่ออกใบเสร็จ - Receipt Date", example = "2024-01-15T10:30:00")
    private LocalDateTime receiptDate;

    @Schema(description = "ประเภทใบเสร็จ - Receipt Type", example = "TAX_INVOICE")
    private String receiptType;

    @Schema(description = "จำนวนเงิน - Amount", example = "1500.00")
    private BigDecimal amount;

    @Schema(description = "จำนวนเงิน (ตัวอักษรไทย) - Amount in Words (TH)", example = "หนึ่งพันห้าร้อยบาทถ้วน")
    private String amountInWordsTh;

    @Schema(description = "จำนวนเงิน (ตัวอักษรอังกฤษ) - Amount in Words (EN)", example = "One Thousand Five Hundred Baht Only")
    private String amountInWordsEn;

    @Schema(description = "สกุลเงิน - Currency", example = "THB")
    private String currency;

    @Schema(description = "สถานะ - Status", example = "ISSUED")
    private String status;

    @Schema(description = "หมายเหตุ - Notes", example = "ออกใบเสร็จเรียบร้อย")
    private String notes;

    @Schema(description = "รหัสผู้ออกใบเสร็จ - Issued By", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID issuedBy;

    @Schema(description = "วันที่สร้าง - Created At", example = "2024-01-15T10:30:00")
    private LocalDateTime createdAt;
}
