package com.icmon.module.i18n.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "คำขอนำเข้าข้อความจำนวนมาก / Bulk import request")
public class BulkImportRequestDTO {
    @Schema(description = "รายการข้อความที่นำเข้า / List of translations to import")
    private List<TranslationUpdateRequestDTO> translations;
}
