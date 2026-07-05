package com.icmon.module.customer.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "คำขอค้นหารถยนต์ - Car search request")
@Data
public class CarSearchRequestDTO {
    @Schema(description = "ยี่ห้อรถ - Brand", example = "Toyota")
    private String brand;
    @Schema(description = "รุ่นรถ - Model", example = "Camry")
    private String model;
    @Schema(description = "ปีที่ผลิต - Year", example = "2020")
    private Integer year;
}
