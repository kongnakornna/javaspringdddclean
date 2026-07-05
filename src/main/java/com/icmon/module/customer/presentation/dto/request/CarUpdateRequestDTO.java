package com.icmon.module.customer.presentation.dto.request;

import com.icmon.module.customer.domain.enums.CarFuelType;
import com.icmon.module.customer.domain.enums.CarTransmissionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "คำขออัปเดตรถยนต์ - Car update request")
@Data
public class CarUpdateRequestDTO {
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
    @Schema(description = "หมายเหตุ - Notes", example = "เปลี่ยนถ่ายน้ำมันเครื่อง")
    private String notes;
}
