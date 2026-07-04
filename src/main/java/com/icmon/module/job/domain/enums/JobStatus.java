package com.icmon.module.job.domain.enums;

/*
    Enum สำหรับสถานะของใบงานซ่อมรถ
    Enum for job card statuses.
*/
public enum JobStatus {
    OPEN,               // เปิดใบงาน / Job opened.
    IN_PROGRESS,        // กำลังดำเนินการ / In progress.
    QUOTATION_PENDING,  // รอการอนุมัติใบเสนอราคา / Waiting for quotation approval.
    QUOTATION_APPROVED, // อนุมัติใบเสนอราคาแล้ว / Quotation approved.
    PART_PICKING,       // กำลังเบิกอะไหล่ / Part picking in progress.
    REPAIR_IN_PROGRESS, // กำลังซ่อม / Repair in progress.
    REPAIR_DONE,        // ซ่อมเสร็จ / Repair completed.
    INVOICE_PENDING,    // รอออกใบแจ้งหนี้ / Invoice pending.
    INVOICE_CREATED,    // ออกใบแจ้งหนี้แล้ว / Invoice created.
    PAYMENT_RECEIVED,   // รับชำระเงินแล้ว / Payment received.
    CLOSED,             // ปิดงาน / Job closed.
    CANCELLED,          // ยกเลิก / Cancelled.
    ON_HOLD,            // ระงับชั่วคราว / On hold.
    WAITING_PARTS       // รออะไหล่ / Waiting for parts.
}
