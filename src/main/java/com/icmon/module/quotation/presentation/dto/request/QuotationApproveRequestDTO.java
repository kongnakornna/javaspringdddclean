package com.icmon.module.quotation.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "คำขออนุมัติใบเสนอราคา / Approve Quotation Request")
public class QuotationApproveRequestDTO {
    @Schema(description = "สร้างใบสั่งซื้ออัตโนมัติ / Auto Create Purchase Order", example = "true")
    private Boolean autoCreatePo;
}
