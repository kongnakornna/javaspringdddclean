package com.icmon.module.customer.presentation.dto.response;

import com.icmon.module.customer.domain.enums.CustomerStatus;
import com.icmon.module.customer.domain.enums.CustomerType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "ข้อมูลลูกค้าแบบละเอียด - Customer detail response")
@Data
@Builder
public class CustomerDetailResponseDTO {
    @Schema(description = "รหัสลูกค้า - Customer ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;
    @Schema(description = "รหัสลูกค้า (code) - Customer code", example = "CUS00001")
    private String customerCode;
    @Schema(description = "ชื่อเต็มของลูกค้า - Full name", example = "สมชาย ใจดี")
    private String fullName;
    @Schema(description = "ชื่อที่ใช้แสดง - Display name", example = "สมชาย")
    private String displayName;
    @Schema(description = "ประเภทลูกค้า - Customer type", example = "INDIVIDUAL")
    private CustomerType customerType;
    @Schema(description = "สถานะลูกค้า - Customer status", example = "ACTIVE")
    private CustomerStatus status;
    @Schema(description = "เลขประจำตัวผู้เสียภาษี - Tax ID", example = "1234567890123")
    private String taxId;
    @Schema(description = "อีเมล - Email", example = "somchai@example.com")
    private String email;
    @Schema(description = "เบอร์โทรศัพท์ - Phone number", example = "0812345678")
    private String phoneNumber;
    @Schema(description = "เบอร์โทรศัพท์สำรอง - Secondary phone", example = "0898765432")
    private String secondaryPhone;
    @Schema(description = "ที่อยู่ - Address", example = "123 ถนนสุขุมวิท")
    private String address;
    @Schema(description = "จังหวัด - Province", example = "กรุงเทพมหานคร")
    private String province;
    @Schema(description = "เมือง/อำเภอ - City/District", example = "วัฒนา")
    private String city;
    @Schema(description = "เขต/ตำบล - Sub-district", example = "คลองเตย")
    private String district;
    @Schema(description = "รหัสไปรษณีย์ - Postal code", example = "10110")
    private String postalCode;
    @Schema(description = "ประเทศ - Country", example = "ไทย")
    private String country;
    @Schema(description = "ผู้ติดต่อ - Contact person", example = "สมศรี ใจดี")
    private String contactPerson;
    @Schema(description = "เบอร์โทรศัพท์ผู้ติดต่อ - Contact phone", example = "0812345679")
    private String contactPhone;
    @Schema(description = "หมายเหตุ - Notes", example = "ลูกค้าประจำ")
    private String notes;
    @Schema(description = "วันที่มาครั้งล่าสุด - Last visit date", example = "2024-06-20T14:30:00")
    private LocalDateTime lastVisitDate;
    @Schema(description = "จำนวนครั้งที่มาใช้บริการ - Total visit count", example = "10")
    private Integer totalVisitCount;
    @Schema(description = "ยอดใช้บริการทั้งหมด - Total spent", example = "50000.00")
    private BigDecimal totalSpent;
    @Schema(description = "วันที่สร้าง - Created at", example = "2024-01-15T10:30:00")
    private LocalDateTime createdAt;
    @Schema(description = "วันที่อัปเดตล่าสุด - Updated at", example = "2024-06-20T15:00:00")
    private LocalDateTime updatedAt;
}
