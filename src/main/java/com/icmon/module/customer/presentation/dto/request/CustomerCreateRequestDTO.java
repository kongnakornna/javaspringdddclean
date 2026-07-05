package com.icmon.module.customer.presentation.dto.request;

import com.icmon.module.customer.domain.enums.CustomerType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "คำขอสร้างลูกค้า - Customer create request")
@Data
public class CustomerCreateRequestDTO {
    @NotBlank(message = "Full name is required")
    @Schema(description = "ชื่อเต็มของลูกค้า - Full name", example = "สมชาย ใจดี", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fullName;
    @Schema(description = "ชื่อที่ใช้แสดง - Display name", example = "สมชาย")
    private String displayName;
    @NotBlank(message = "Phone number is required")
    @Schema(description = "เบอร์โทรศัพท์ - Phone number", example = "0812345678", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phoneNumber;
    @Schema(description = "เบอร์โทรศัพท์สำรอง - Secondary phone", example = "0898765432")
    private String secondaryPhone;
    @Email(message = "Invalid email format")
    @Schema(description = "อีเมล - Email", example = "somchai@example.com")
    private String email;
    @NotNull(message = "Customer type is required")
    @Schema(description = "ประเภทลูกค้า - Customer type", example = "INDIVIDUAL", requiredMode = Schema.RequiredMode.REQUIRED)
    private CustomerType customerType;
    @Schema(description = "เลขประจำตัวผู้เสียภาษี - Tax ID", example = "1234567890123")
    private String taxId;
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
}
