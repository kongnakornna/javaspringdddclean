package com.icmon.module.job.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "คำขออัปเดตงานซ่อม - Job update request")
@Data
public class JobUpdateRequestDTO {
    @Schema(description = "รหัสช่าง - Mechanic ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID mechanicId;
    @Schema(description = "อาการเสีย - Symptom", example = "เครื่องยนต์มีเสียงดัง")
    private String symptom;
    @Schema(description = "บันทึกการวินิจฉัย - Diagnosis note", example = "ตรวจพบว่าสายพานไทม์มิ่งสึก")
    private String diagnosisNote;
    @Schema(description = "เลขไมล์ปัจจุบัน - Mileage", example = "50000")
    private Integer mileage;
    @Schema(description = "ค่าใช้จ่ายโดยประมาณ - Estimated cost", example = "5000.00")
    private BigDecimal estimatedCost;
    @Schema(description = "ความสำคัญ - Priority", example = "NORMAL")
    private String priority;
}
