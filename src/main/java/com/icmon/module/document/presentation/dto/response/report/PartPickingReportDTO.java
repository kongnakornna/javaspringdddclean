package com.icmon.module.document.presentation.dto.response.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "\u0e02\u0e49\u0e2d\u0e21\u0e39\u0e25\u0e2a\u0e33\u0e2b\u0e23\u0e31\u0e1a\u0e40\u0e2d\u0e01\u0e2a\u0e32\u0e23\u0e40\u0e1a\u0e34\u0e01\u0e2d\u0e30\u0e44\u0e2b\u0e25\u0e48 // Part Picking report data")
public class PartPickingReportDTO {
    private String pickingNo;
    private String jobNo;
    private String requestDate;
    private String requestedBy;
    private String mechanicName;
    private String licensePlate;
    private List<PickingItemDTO> items;

    @Data @Builder
    public static class PickingItemDTO {
        private String partCode;
        private String partName;
        private Integer requestedQty;
        private Integer pickedQty;
        private String location;
    }
}
