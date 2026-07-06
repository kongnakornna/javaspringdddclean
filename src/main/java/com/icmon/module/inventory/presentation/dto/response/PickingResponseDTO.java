package com.icmon.module.inventory.presentation.dto.response;

import com.icmon.module.inventory.infrastructure.entity.PartPickingRequestEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PickingResponseDTO {
    private UUID id;
    private String pickingNo;
    private UUID jobId;
    private UUID quotationId;
    private LocalDateTime requestedDate;
    private String status;
    private String priority;
    private String notes;
    private List<PickingItemDTO> items;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PickingItemDTO {
        private UUID id;
        private UUID partId;
        private Integer requestedQuantity;
        private Integer pickedQuantity;
        private BigDecimal unitPrice;
    }

    public static PickingResponseDTO fromEntity(PartPickingRequestEntity entity, List<PickingItemDTO> items) {
        return PickingResponseDTO.builder()
                .id(entity.getId()).pickingNo(entity.getPickingNo())
                .jobId(entity.getJobId()).quotationId(entity.getQuotationId())
                .requestedDate(entity.getRequestedDate()).status(entity.getStatus())
                .priority(entity.getPriority()).notes(entity.getNotes())
                .items(items).build();
    }
}
