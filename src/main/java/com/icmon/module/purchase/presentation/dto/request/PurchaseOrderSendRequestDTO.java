package com.icmon.module.purchase.presentation.dto.request;

import lombok.Data;

@Data
public class PurchaseOrderSendRequestDTO {
    private Boolean sendEmail;
    private String supplierEmail;
}
