package com.icmon.module.payment.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "ประวัติการชำระเงิน - Payment History Response")
public class PaymentHistoryResponseDTO {
    @Schema(description = "รหัสประวัติ - History ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "รหัสการชำระเงิน - Payment ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID paymentId;

    @Schema(description = "สถานะเดิม - From Status", example = "PENDING")
    private String fromStatus;

    @Schema(description = "สถานะใหม่ - To Status", example = "COMPLETED")
    private String toStatus;

    @Schema(description = "รหัสผู้เปลี่ยนแปลง - Changed By", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID changedBy;

    @Schema(description = "วันที่เปลี่ยนแปลง - Changed At", example = "2024-01-15T10:30:00")
    private LocalDateTime changedAt;

    @Schema(description = "เหตุผล - Reason", example = "ชำระเงินสำเร็จ")
    private String reason;
}
