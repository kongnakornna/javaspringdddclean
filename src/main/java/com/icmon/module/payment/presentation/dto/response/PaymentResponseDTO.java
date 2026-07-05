package com.icmon.module.payment.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "ข้อมูลการชำระเงิน - Payment Response")
public class PaymentResponseDTO {
    @Schema(description = "รหัสการชำระเงิน - Payment ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "เลขที่การชำระเงิน - Payment Number", example = "PAY-20240101-001")
    private String paymentNo;

    @Schema(description = "รหัสใบแจ้งหนี้ - Invoice ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID invoiceId;

    @Schema(description = "รหัสงาน - Job ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID jobId;

    @Schema(description = "รหัสลูกค้า - Customer ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID customerId;

    @Schema(description = "วันที่ชำระเงิน - Payment Date", example = "2024-01-15T10:30:00")
    private LocalDateTime paymentDate;

    @Schema(description = "รหัสวิธีชำระเงิน - Payment Method ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID paymentMethodId;

    @Schema(description = "จำนวนเงิน - Amount", example = "1500.00")
    private BigDecimal amount;

    @Schema(description = "จำนวนเงินที่รับ - Amount Received", example = "2000.00")
    private BigDecimal amountReceived;

    @Schema(description = "เงินทอน - Change Amount", example = "500.00")
    private BigDecimal changeAmount;

    @Schema(description = "สกุลเงิน - Currency", example = "THB")
    private String currency;

    @Schema(description = "อัตราแลกเปลี่ยน - Exchange Rate", example = "1.00")
    private BigDecimal exchangeRate;

    @Schema(description = "สถานะ - Status", example = "COMPLETED")
    private String status;

    @Schema(description = "เลขที่อ้างอิง - Reference Number", example = "REF-20240101-001")
    private String referenceNumber;

    @Schema(description = "ชื่อธนาคาร - Bank Name", example = "กรุงเทพ")
    private String bankName;

    @Schema(description = "เลขที่เช็ค - Cheque Number", example = "CH-001")
    private String chequeNumber;

    @Schema(description = "ธนาคารของเช็ค - Cheque Bank", example = "กรุงเทพ")
    private String chequeBank;

    @Schema(description = "วันที่ในเช็ค - Cheque Date", example = "2024-01-15")
    private LocalDate chequeDate;

    @Schema(description = "หมายเหตุ - Notes", example = "ชำระค่าบริการรายเดือน")
    private String notes;

    @Schema(description = "รหัสผู้รับเงิน - Received By", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID receivedBy;

    @Schema(description = "รหัสผู้อนุมัติ - Approved By", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID approvedBy;

    @Schema(description = "วันที่อนุมัติ - Approved At", example = "2024-01-15T11:00:00")
    private LocalDateTime approvedAt;

    @Schema(description = "จำนวนเงินที่คืน - Refunded Amount", example = "0.00")
    private BigDecimal refundedAmount;

    @Schema(description = "วันที่คืนเงิน - Refunded At", example = "2024-01-16T10:00:00")
    private LocalDateTime refundedAt;

    @Schema(description = "ข้อมูลใบเสร็จ - Receipt")
    private ReceiptResponseDTO receipt;

    @Schema(description = "วันที่สร้าง - Created At", example = "2024-01-15T10:30:00")
    private LocalDateTime createdAt;
}
