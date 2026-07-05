package com.icmon.module.quotation.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import com.icmon.module.quotation.domain.enums.QuotationStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.UUID;

/*
    โดเมนประวัติการเปลี่ยนสถานะใบเสนอราคา / Quotation status history domain entity.
*/
@Data
@EqualsAndHashCode(callSuper = true)
public class TQuotationStatusHistory extends GenericBusinessClass {

    private UUID quotationId;
    private QuotationStatus fromStatus;
    private QuotationStatus toStatus;
    private UUID changedBy;
    private LocalDateTime changedAt;
    private String reason;
}
