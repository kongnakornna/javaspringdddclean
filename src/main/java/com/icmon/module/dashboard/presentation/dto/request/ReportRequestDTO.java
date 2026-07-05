package com.icmon.module.dashboard.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "คำขอสร้างรายงาน / Report request")
public class ReportRequestDTO {
    @Schema(description = "ประเภทรายงาน / Report type", example = "SALES")
    private String reportType;
    @Schema(description = "รูปแบบไฟล์ / File format", example = "PDF")
    private String format;
    @Schema(description = "รอบระยะเวลา / Period", example = "MONTHLY")
    private String period;
    @Schema(description = "วันที่เริ่มต้น / Start date", example = "2024-01-01")
    private String startDate;
    @Schema(description = "วันที่สิ้นสุด / End date", example = "2024-12-31")
    private String endDate;
    @Schema(description = "รหัสสาขา / Branch ID", example = "BR001")
    private String branchId;
}
