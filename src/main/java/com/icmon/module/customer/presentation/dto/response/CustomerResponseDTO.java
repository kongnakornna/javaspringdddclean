package com.icmon.module.customer.presentation.dto.response;

import com.icmon.module.customer.domain.enums.CustomerStatus;
import com.icmon.module.customer.domain.enums.CustomerType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "ข้อมูลลูกค้า (สรุป) - Customer response")
@Data
@Builder
public class CustomerResponseDTO {
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
    @Schema(description = "อีเมล - Email", example = "somchai@example.com")
    private String email;
    @Schema(description = "เบอร์โทรศัพท์ - Phone number", example = "0812345678")
    private String phoneNumber;
    @Schema(description = "ที่อยู่ - Address", example = "123 ถนนสุขุมวิท")
    private String address;
    @Schema(description = "จังหวัด - Province", example = "กรุงเทพมหานคร")
    private String province;
    @Schema(description = "เมือง/อำเภอ - City/District", example = "วัฒนา")
    private String city;
    @Schema(description = "วันที่สร้าง - Created at", example = "2024-01-15T10:30:00")
    private LocalDateTime createdAt;
}
