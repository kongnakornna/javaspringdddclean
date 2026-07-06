package com.icmon.module.inventory.presentation.dto.request;

import lombok.Data;
import java.util.UUID;

@Data
public class PickingConfirmRequestDTO {
    private UUID confirmedBy;
}
