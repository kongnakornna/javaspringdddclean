package com.icmon.module.job.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import com.icmon.module.job.domain.enums.JobStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class TJob extends GenericBusinessClass {

    private String jobNo;               // เลขที่ใบงาน / Job number.
    private UUID customerId;            // ID ลูกค้า / Customer ID.
    private UUID carId;                 // ID รถยนต์ / Vehicle ID.
    private UUID mechanicId;            // ID ช่างผู้รับผิดชอบ / Assigned mechanic ID.
    private JobStatus status;           // สถานะ / Status.
    private LocalDateTime startDate;    // วันที่เริ่ม / Start date.
    private LocalDateTime endDate;      // วันที่เสร็จ / Completion date.
    private String symptom;             // อาการ / Symptom.
    private String diagnosisNote;       // หมายเหตุวินิจฉัย / Diagnosis note.
    private Integer mileage;            // ระยะทาง (กม.) / Mileage (km).
    private BigDecimal estimatedCost;   // ค่าใช้จ่ายโดยประมาณ / Estimated cost.
    private BigDecimal actualCost;      // ค่าใช้จ่ายจริง / Actual cost.
    private String priority;            // ความสำคัญ: NORMAL, URGENT, EMERGENCY / Priority level.

    /*
        ฟังก์ชันนี้ใช้เปลี่ยนสถานะใบงาน พร้อมตรวจสอบความถูกต้องของลำดับสถานะ
        This function changes the job status, validating the transition order.
    */
    public void changeStatus(JobStatus newStatus) {
        // ตรวจสอบว่าไม่สามารถเปลี่ยนจาก CLOSED หรือ CANCELLED ได้
        // Cannot transition from CLOSED or CANCELLED.
        if (this.status == JobStatus.CLOSED || this.status == JobStatus.CANCELLED) {
            throw new IllegalStateException("Cannot change status of closed or cancelled job.");
        }
        this.status = newStatus;
        // ถ้าสถานะเป็น CLOSED หรือ REPAIR_DONE ให้บันทึกเวลา endDate / Set endDate for terminal statuses.
        if (newStatus == JobStatus.CLOSED || newStatus == JobStatus.REPAIR_DONE) {
            this.endDate = LocalDateTime.now();
        }
    }

    /*
        ฟังก์ชันนี้ใช้ตรวจสอบว่าใบงานสามารถสร้าง Quotation ได้หรือไม่
        This function checks if a quotation can be created for this job.
    */
    public boolean canCreateQuotation() {
        return this.status == JobStatus.OPEN ||
               this.status == JobStatus.IN_PROGRESS ||
               this.status == JobStatus.QUOTATION_PENDING;
    }

    /*
        ฟังก์ชันนี้ใช้คำนวณค่าใช้จ่ายรวมจากรายการบริการและอะไหล่
        This function calculates the total cost from service and part items.
    */
    public BigDecimal calculateTotalCost() {
        // TODO: รวมค่าบริการ + ค่าอะไหล่ / Aggregate service + part costs.
        return BigDecimal.ZERO;
    }
}
