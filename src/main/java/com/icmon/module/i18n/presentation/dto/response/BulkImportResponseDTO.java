package com.icmon.module.i18n.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "ผลลัพธ์การนำเข้าข้อความ / Bulk import response")
public class BulkImportResponseDTO {
    @Schema(description = "จำนวนที่นำเข้า / Imported count")
    private int imported;

    @Schema(description = "จำนวนที่อัปเดต / Updated count")
    private int updated;

    @Schema(description = "จำนวนที่ล้มเหลว / Failed count")
    private int failed;

    @Schema(description = "จำนวนที่สำเร็จ / Success count")
    private int successCount;

    @Schema(description = "จำนวนที่ล้มเหลว / Failure count")
    private int failureCount;

    @Schema(description = "ข้อความ / Message")
    private String message;

    @Schema(description = "รายการข้อผิดพลาด / Errors")
    private List<String> errors;
}
