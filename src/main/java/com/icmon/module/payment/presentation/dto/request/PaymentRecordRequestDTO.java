package com.icmon.module.payment.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Schema(description = "คำขอบันทึกการชำระเงิน - Payment Record Request")
public class PaymentRecordRequestDTO {
    @Schema(description = "รหัสใบแจ้งหนี้ - Invoice ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID invoiceId;

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

    @Schema(description = "รหัสผู้รับชำระเงิน - Received By", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID receivedBy;
}
