package com.icmon.module.customer.presentation.dto.response;

import com.icmon.module.customer.domain.enums.CarFuelType;
import com.icmon.module.customer.domain.enums.CarTransmissionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "ข้อมูลรถยนต์ - Car response")
@Data
@Builder
public class CarResponseDTO {
    @Schema(description = "รหัสรถยนต์ - Car ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;
    @Schema(description = "รหัสลูกค้า - Customer ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID customerId;
    @Schema(description = "ทะเบียนรถ - License plate", example = "กข1234")
    private String licensePlate;
    @Schema(description = "จังหวัดที่จดทะเบียน - Province", example = "กรุงเทพมหานคร")
    private String province;
    @Schema(description = "ยี่ห้อรถ - Brand", example = "Toyota")
    private String brand;
    @Schema(description = "รุ่นรถ - Model", example = "Camry")
    private String model;
    @Schema(description = "รุ่นย่อย - Sub-model", example = "2.0 G")
    private String subModel;
    @Schema(description = "ปีที่ผลิต - Year", example = "2020")
    private Integer year;
    @Schema(description = "สีรถ - Color", example = "ขาว")
    private String color;
    @Schema(description = "ประเภทเชื้อเพลิง - Fuel type", example = "GASOLINE")
    private CarFuelType fuelType;
    @Schema(description = "ประเภทเกียร์ - Transmission type", example = "AUTOMATIC")
    private CarTransmissionType transmissionType;
    @Schema(description = "ขนาดเครื่องยนต์ (ซีซี) - Engine displacement (cc)", example = "2000")
    private Integer engineCc;
    @Schema(description = "เลขไมล์ - Mileage", example = "50000")
    private Integer mileage;
    @Schema(description = "สถานะการใช้งาน - Is active", example = "true")
    private Boolean isActive;
    @Schema(description = "วันที่สร้าง - Created at", example = "2024-01-15T10:30:00")
    private LocalDateTime createdAt;
}
