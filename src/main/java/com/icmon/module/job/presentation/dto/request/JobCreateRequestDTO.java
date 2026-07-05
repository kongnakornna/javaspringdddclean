package com.icmon.module.job.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Schema(description = "คำขอสร้างงานซ่อม - Job create request")
@Data
public class JobCreateRequestDTO {

    @NotNull(message = "Customer ID is required")
    @Schema(description = "รหัสลูกค้า - Customer ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID customerId;

    @NotNull(message = "Car ID is required")
    @Schema(description = "รหัสรถยนต์ - Car ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID carId;

    @NotNull(message = "Mechanic ID is required")
    @Schema(description = "รหัสช่าง - Mechanic ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID mechanicId;

    @Schema(description = "อาการเสีย - Symptom", example = "เครื่องยนต์มีเสียงดัง")
    private String symptom;

    @Schema(description = "เลขไมล์ปัจจุบัน - Mileage", example = "50000")
    private Integer mileage;

    @Schema(description = "ความสำคัญ - Priority", example = "NORMAL")
    private String priority;

    @Schema(description = "บันทึกการวินิจฉัย - Diagnosis note", example = "ตรวจพบว่าสายพานไทม์มิ่งสึก")
    private String diagnosisNote;
}
