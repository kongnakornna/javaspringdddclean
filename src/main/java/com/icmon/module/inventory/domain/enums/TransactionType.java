package com.icmon.module.inventory.domain.enums;

/**
 * ประเภทของการเคลื่อนไหวสินค้าคงคลัง (Inventory transaction type)
 */
public enum TransactionType {
    RECEIPT,
    ISSUE,
    ADJUSTMENT,
    TRANSFER_IN,
    TRANSFER_OUT,
    RETURN,
    STOCKTAKE
}
