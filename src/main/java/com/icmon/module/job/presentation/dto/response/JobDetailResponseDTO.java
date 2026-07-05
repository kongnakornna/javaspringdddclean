package com.icmon.module.job.presentation.dto.response;

import com.icmon.module.job.domain.enums.JobStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Schema(description = "ข้อมูลงานซ่อมแบบละเอียด - Job detail response")
@Data
@Builder
public class JobDetailResponseDTO {
    @Schema(description = "รหัสงาน - Job ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;
    @Schema(description = "เลขที่งาน - Job number", example = "JOB20240115001")
    private String jobNo;
    @Schema(description = "รหัสลูกค้า - Customer ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID customerId;
    @Schema(description = "รหัสรถยนต์ - Car ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID carId;
    @Schema(description = "รหัสช่าง - Mechanic ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID mechanicId;
    @Schema(description = "สถานะงาน - Job status", example = "IN_PROGRESS")
    private JobStatus status;
    @Schema(description = "วันที่เริ่มงาน - Start date", example = "2024-01-15T10:30:00")
    private LocalDateTime startDate;
    @Schema(description = "วันที่สิ้นสุด - End date", example = "2024-01-16T17:00:00")
    private LocalDateTime endDate;
    @Schema(description = "อาการเสีย - Symptom", example = "เครื่องยนต์มีเสียงดัง")
    private String symptom;
    @Schema(description = "บันทึกการวินิจฉัย - Diagnosis note", example = "ตรวจพบว่าสายพานไทม์มิ่งสึก")
    private String diagnosisNote;
    @Schema(description = "เลขไมล์ - Mileage", example = "50000")
    private Integer mileage;
    @Schema(description = "ค่าใช้จ่ายโดยประมาณ - Estimated cost", example = "5000.00")
    private BigDecimal estimatedCost;
    @Schema(description = "ค่าใช้จ่ายจริง - Actual cost", example = "4500.00")
    private BigDecimal actualCost;
    @Schema(description = "ความสำคัญ - Priority", example = "NORMAL")
    private String priority;
    @Schema(description = "รายการบริการ - Services")
    private List<JobServiceResponseDTO> services;
    @Schema(description = "รายการอะไหล่ - Parts")
    private List<JobPartResponseDTO> parts;
    @Schema(description = "ประวัติสถานะ - Status history")
    private List<JobStatusHistoryDTO> statusHistory;
    @Schema(description = "วันที่สร้าง - Created at", example = "2024-01-15T10:30:00")
    private LocalDateTime createdAt;
}
