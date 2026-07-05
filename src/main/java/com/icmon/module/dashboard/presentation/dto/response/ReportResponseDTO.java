package com.icmon.module.dashboard.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "ข้อมูลรายงาน / Report response")
public class ReportResponseDTO {
    @Schema(description = "รหัสรายงาน / Report ID", example = "RPT-202412-001")
    private String reportId;
    @Schema(description = "สถานะ / Status", example = "COMPLETED")
    private String status;
    @Schema(description = "ประเภทรายงาน / Report type", example = "SALES")
    private String reportType;
    @Schema(description = "รูปแบบไฟล์ / File format", example = "PDF")
    private String format;
    @Schema(description = "ข้อมูลไฟล์ / File data")
    private byte[] data;
}
