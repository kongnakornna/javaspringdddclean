package com.icmon.module.inventory.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "ข้อมูลอะไหล่หลัก | Part Master Response")
public class PartMasterResponseDTO {
    @Schema(description = "รหัส | ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;
    @Schema(description = "รหัสอะไหล่ | Part Code", example = "BRK-001")
    private String partCode;
    @Schema(description = "ชื่ออะไหล่ (ภาษาไทย) | Part Name (Thai)", example = "ผ้าเบรกหน้า")
    private String partName;
    @Schema(description = "ชื่ออะไหล่ (ภาษาอังกฤษ) | Part Name (English)", example = "Front Brake Pad")
    private String partNameEn;
    @Schema(description = "รหัสหมวดหมู่ | Category ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID categoryId;
    @Schema(description = "ยี่ห้อ | Brand", example = "Toyota")
    private String brand;
    @Schema(description = "รุ่น | Model", example = "Camry 2020")
    private String model;
    @Schema(description = "หมายเลข OEM | OEM Number", example = "04465-33210")
    private String oemNumber;
    @Schema(description = "คำอธิบาย | Description", example = "ผ้าเบรกหน้าสำหรับ Toyota Camry")
    private String description;
    @Schema(description = "หน่วยนับ | Unit", example = "ชิ้น")
    private String unit;
    @Schema(description = "ระดับการสั่งซื้อซ้ำ | Reorder Level", example = "10")
    private int reorderLevel;
    @Schema(description = "จำนวนที่สั่งซื้อซ้ำ | Reorder Quantity", example = "50")
    private int reorderQuantity;
    @Schema(description = "จำนวนคงเหลือ | Stock Quantity", example = "25")
    private int stockQuantity;
    @Schema(description = "สต็อกขั้นต่ำ | Min Stock", example = "5")
    private int minStock;
    @Schema(description = "สต็อกสูงสุด | Max Stock", example = "100")
    private int maxStock;
    @Schema(description = "ต้นทุนต่อหน่วย | Unit Cost", example = "500.00")
    private BigDecimal unitCost;
    @Schema(description = "ราคาขาย | Selling Price", example = "750.00")
    private BigDecimal sellingPrice;
    @Schema(description = "รหัสสถานที่จัดเก็บ | Location ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID locationId;
    @Schema(description = "สถานะ | Status", example = "ACTIVE")
    private String status;
    @Schema(description = "URL รูปภาพ | Image URL", example = "https://example.com/images/brk-001.jpg")
    private String imageUrl;
    @Schema(description = "หมายเหตุ | Notes", example = "อะไหล่แท้จากศูนย์")
    private String notes;
    @Schema(description = "วันที่อัปเดตสต็อกล่าสุด | Last Updated Stock", example = "2024-06-15T10:30:00")
    private LocalDateTime lastUpdatedStock;
    @Schema(description = "วันที่สร้าง | Created At", example = "2024-01-01T08:00:00")
    private LocalDateTime createdAt;
    @Schema(description = "วันที่แก้ไขล่าสุด | Updated At", example = "2024-06-15T10:30:00")
    private LocalDateTime updatedAt;
}
