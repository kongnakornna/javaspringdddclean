package com.icmon.module.document.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "คำค้นหาเอกสาร // Document search request")
public class DocumentSearchRequestDTO {
    @Schema(description = "ประเภทเอกสาร // Document type filter", example = "INVOICE")
    private String documentType;

    @Schema(description = "สถานะเอกสาร // Document status filter", example = "GENERATED")
    private String status;

    @Schema(description = "ประเภทย่อยของเอกสาร // Document sub-type filter", example = "STANDARD")
    private String documentSubType;
}
