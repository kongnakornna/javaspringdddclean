package com.icmon.module.customer.presentation.dto.request;

import com.icmon.module.customer.domain.enums.CarFuelType;
import com.icmon.module.customer.domain.enums.CarTransmissionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Schema(description = "คำขอสร้างรถยนต์ - Car create request")
@Data
public class CarCreateRequestDTO {
    @NotNull(message = "Customer ID is required")
    @Schema(description = "รหัสลูกค้า - Customer ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID customerId;
    @NotBlank(message = "License plate is required")
    @Schema(description = "ทะเบียนรถ - License plate", example = "กข1234", requiredMode = Schema.RequiredMode.REQUIRED)
    private String licensePlate;
    @Schema(description = "จังหวัดที่จดทะเบียน - Province", example = "กรุงเทพมหานคร")
    private String province;
    @NotBlank(message = "Brand is required")
    @Schema(description = "ยี่ห้อรถ - Brand", example = "Toyota", requiredMode = Schema.RequiredMode.REQUIRED)
    private String brand;
    @NotBlank(message = "Model is required")
    @Schema(description = "รุ่นรถ - Model", example = "Camry", requiredMode = Schema.RequiredMode.REQUIRED)
    private String model;
    @Schema(description = "รุ่นย่อย - Sub-model", example = "2.0 G")
    private String subModel;
    @Schema(description = "ปีที่ผลิต - Year", example = "2020")
    private Integer year;
    @Schema(description = "สีรถ - Color", example = "ขาว")
    private String color;
    @Schema(description = "หมายเลขเครื่องยนต์ - Engine number", example = "1ZZ-FE123456")
    private String engineNumber;
    @Schema(description = "หมายเลขตัวถัง - Chassis number", example = "MR053REH4X1234567")
    private String chassisNumber;
    @Schema(description = "ประเภทเชื้อเพลิง - Fuel type", example = "GASOLINE")
    private CarFuelType fuelType;
    @Schema(description = "ประเภทเกียร์ - Transmission type", example = "AUTOMATIC")
    private CarTransmissionType transmissionType;
    @Schema(description = "ขนาดเครื่องยนต์ (ซีซี) - Engine displacement (cc)", example = "2000")
    private Integer engineCc;
    @Schema(description = "จำนวนที่นั่ง - Seating capacity", example = "5")
    private Integer seatingCapacity;
    @Schema(description = "หมายเหตุ - Notes", example = "รถเช่าซื้อ")
    private String notes;
}
