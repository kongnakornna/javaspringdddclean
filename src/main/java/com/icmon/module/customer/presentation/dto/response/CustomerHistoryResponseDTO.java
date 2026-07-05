package com.icmon.module.customer.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "ประวัติการใช้บริการของลูกค้า - Customer history response")
@Data
@Builder
public class CustomerHistoryResponseDTO {
    @Schema(description = "รหัสประวัติ - History ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;
    @Schema(description = "รหัสรถยนต์ - Car ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID carId;
    @Schema(description = "รหัสงาน - Job ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID jobId;
    @Schema(description = "วันที่ให้บริการ - Service date", example = "2024-06-20T14:30:00")
    private LocalDateTime serviceDate;
    @Schema(description = "ประเภทบริการ - Service type", example = "เปลี่ยนถ่ายน้ำมันเครื่อง")
    private String serviceType;
    @Schema(description = "รายละเอียด - Description", example = "เปลี่ยนถ่ายน้ำมันเครื่องและไส้กรอง")
    private String description;
    @Schema(description = "ค่าใช้จ่ายทั้งหมด - Total cost", example = "3500.00")
    private BigDecimal totalCost;
    @Schema(description = "ชื่อช่าง - Mechanic name", example = "ช่างณรงค์")
    private String mechanicName;
}
