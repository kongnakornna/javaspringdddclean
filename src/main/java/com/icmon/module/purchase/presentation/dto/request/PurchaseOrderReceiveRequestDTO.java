package com.icmon.module.purchase.presentation.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PurchaseOrderReceiveRequestDTO {
    private LocalDateTime actualDeliveryDate;
    private String notes;
}
