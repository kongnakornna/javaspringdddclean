package com.icmon.module.weborder.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@Schema(description = "ข้อมูลหมวดหมู่แคตตาล็อก / Catalogue Category Response")
public class CatalogueCategoryResponseDTO {

    @Schema(description = "รหัสหมวดหมู่ / ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID id;

    @Schema(description = "รหัสหมวดหมู่ / Category Code", example = "BRAKE")
    private String categoryCode;

    @Schema(description = "ชื่อหมวดหมู่ / Category Name", example = "ระบบเบรก")
    private String categoryName;

    @Schema(description = "ชื่อหมวดหมู่ (อังกฤษ) / Category Name EN", example = "Brake System")
    private String categoryNameEn;

    @Schema(description = "รหัสหมวดหมู่หลัก / Parent ID")
    private UUID parentId;

    @Schema(description = "ระดับ / Level", example = "1")
    private Integer level;

    @Schema(description = "ลำดับ / Sort Order", example = "1")
    private Integer sortOrder;

    @Schema(description = "URL รูปไอคอน / Icon URL", example = "/icons/brake.png")
    private String iconUrl;

    @Schema(description = "ใช้งานอยู่ / Active", example = "true")
    private Boolean isActive;
}
