package com.icmon.module.inventory.presentation.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class PickingReportDTO {
    private String pickingNo;
    private String jobNo;
    private String licensePlate;
    private String carModel;
    private String requestDate;
    private String requestedBy;
    private String mechanicName;
    private String status;
    private String notes;
    private String companyName;
    private String companyAddress;
    private String companyPhone;
    private String companyTaxId;
    private List<PickingItemDTO> items;

    @Data
    @Builder
    public static class PickingItemDTO {
        private Integer lineNo;
        private String partCode;
        private String partName;
        private Integer requestedQty;
        private Integer pickedQty;
        private String unit;
        private String location;
        private String note;
    }
}
