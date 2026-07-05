package com.icmon.module.inventory.presentation.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class StocktakeDetailRequestDTO {
    private UUID partId;
    private int systemQuantity;
    private int countedQuantity;
    private String note;
    private UUID countedBy;
}
