package com.icmon.module.weborder.presentation.dto.response;

import com.icmon.module.weborder.domain.enums.PromotionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Schema(description = "ข้อมูลโปรโมชัน / Promotion Response")
public class PromotionResponseDTO {

    @Schema(description = "รหัส / ID")
    private UUID id;

    @Schema(description = "โค้ดโปรโมชัน / Promotion Code", example = "WELCOME10")
    private String promotionCode;

    @Schema(description = "ชื่อโปรโมชัน / Promotion Name", example = "ส่วนลด 10% สำหรับสมาชิกใหม่")
    private String promotionName;

    @Schema(description = "ประเภท / Promotion Type", example = "PERCENTAGE")
    private PromotionType promotionType;

    @Schema(description = "มูลค่าส่วนลด / Discount Value", example = "10.00")
    private BigDecimal discountValue;

    @Schema(description = "ยอดสั่งซื้อขั้นต่ำ / Min Order Amount", example = "500.00")
    private BigDecimal minOrderAmount;

    @Schema(description = "ส่วนลดสูงสุด / Max Discount", example = "500.00")
    private BigDecimal maxDiscount;

    @Schema(description = "วันที่เริ่มต้น / Start Date")
    private LocalDateTime startDate;

    @Schema(description = "วันที่สิ้นสุด / End Date")
    private LocalDateTime endDate;

    @Schema(description = "ใช้งานอยู่ / Active", example = "true")
    private Boolean isActive;

    @Schema(description = "คำอธิบาย / Description")
    private String description;
}
