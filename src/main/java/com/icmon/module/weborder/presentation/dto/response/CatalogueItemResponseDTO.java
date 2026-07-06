package com.icmon.module.weborder.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@Schema(description = "ข้อมูลสินค้าในแคตตาล็อก / Catalogue Item Response")
public class CatalogueItemResponseDTO {

    @Schema(description = "รหัสสินค้า / ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID id;

    @Schema(description = "รหัสสินค้า / Item Code", example = "BRK-001")
    private String itemCode;

    @Schema(description = "ชื่อสินค้า / Item Name", example = "ผ้าเบรกหน้า TOYOTA VIOS")
    private String itemName;

    @Schema(description = "ชื่อสินค้า (อังกฤษ) / Item Name EN", example = "Front Brake Pad TOYOTA VIOS")
    private String itemNameEn;

    @Schema(description = "รหัสหมวดหมู่ / Category ID", example = "b2c3d4e5-f6a7-8901-bcde-f12345678901")
    private UUID categoryId;

    @Schema(description = "คำอธิบายสั้น / Short Description", example = "ผ้าเบรกหน้าคุณภาพสูง")
    private String shortDescription;

    @Schema(description = "ยี่ห้อ / Brand", example = "TOYOTA")
    private String brand;

    @Schema(description = "URL รูปภาพ / Image URL", example = "/images/brk-001.jpg")
    private String imageUrl;

    @Schema(description = "พร้อมขาย / Active", example = "true")
    private Boolean isActive;

    @Schema(description = "สินค้าแนะนำ / Featured", example = "true")
    private Boolean isFeatured;

    @Schema(description = "สินค้าใหม่ / New", example = "false")
    private Boolean isNew;
}
