package com.icmon.module.inventory.domain.enums;

/**
 * สถานะคำขอเบิก (Picking request status)
 */
public enum PickingStatus {
    DRAFT,
    PENDING_APPROVAL,
    APPROVED,
    PICKING,
    PICKED,
    CANCELLED
}
