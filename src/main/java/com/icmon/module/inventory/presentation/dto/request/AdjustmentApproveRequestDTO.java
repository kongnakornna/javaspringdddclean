package com.icmon.module.inventory.presentation.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class AdjustmentApproveRequestDTO {
    private UUID approvedBy;
    private String description;
}
