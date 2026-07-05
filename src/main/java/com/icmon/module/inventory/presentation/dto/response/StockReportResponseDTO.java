package com.icmon.module.inventory.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "ข้อมูลรายงานสต็อกสินค้า | Stock Report Response")
public class StockReportResponseDTO {
    @Schema(description = "เนื้อหาไฟล์รายงาน (binary) | Report File Content", example = "[B@...")
    private byte[] content;
    @Schema(description = "ชื่อไฟล์ | Filename", example = "stock_report_20240630.xlsx")
    private String filename;
    @Schema(description = "ประเภทเนื้อหา | Content Type", example = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    private String contentType;

    public StockReportResponseDTO(byte[] content, String filename, String contentType) {
        this.content = content;
        this.filename = filename;
        this.contentType = contentType;
    }
}
