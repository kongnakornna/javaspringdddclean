package com.icmon.module.payment.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "คำขอค้นหาการชำระเงิน - Payment Search Request")
public class PaymentSearchRequestDTO {
    @Schema(description = "รหัสลูกค้า - Customer ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID customerId;

    @Schema(description = "สถานะการชำระเงิน - Payment Status", example = "COMPLETED")
    private String status;

    @Schema(description = "วันที่เริ่มต้น - Start Date", example = "2024-01-01T00:00:00")
    private LocalDateTime startDate;

    @Schema(description = "วันที่สิ้นสุด - End Date", example = "2024-12-31T23:59:59")
    private LocalDateTime endDate;
}
