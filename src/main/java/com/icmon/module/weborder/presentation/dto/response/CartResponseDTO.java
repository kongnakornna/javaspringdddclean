package com.icmon.module.weborder.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Schema(description = "ข้อมูลตะกร้าสินค้า / Cart Response")
public class CartResponseDTO {

    @Schema(description = "รหัส / ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID id;

    @Schema(description = "รหัสตะกร้า / Cart ID", example = "cart_1234567890")
    private String cartId;

    @Schema(description = "รหัสลูกค้า / Customer ID")
    private UUID customerId;

    @Schema(description = "ยอดรวมก่อนส่วนลด / Subtotal", example = "1500.00")
    private BigDecimal subtotal;

    @Schema(description = "ส่วนลด / Discount", example = "100.00")
    private BigDecimal discount;

    @Schema(description = "ภาษี / Tax", example = "98.00")
    private BigDecimal tax;

    @Schema(description = "ค่าจัดส่ง / Shipping", example = "50.00")
    private BigDecimal shipping;

    @Schema(description = "ยอดสุทธิ / Total", example = "1548.00")
    private BigDecimal total;

    @Schema(description = "โค้ดส่วนลด / Promotion Code", example = "WELCOME10")
    private String promotionCode;

    @Schema(description = "จำนวนรายการ / Item Count", example = "3")
    private Integer itemCount;

    @Schema(description = "รายการสินค้า / Items")
    private List<CartItemDTO> items;

    @Schema(description = "วันที่สร้าง / Created At")
    private LocalDateTime createdAt;

    @Data
    @Builder
    @Schema(description = "รายการสินค้าในตะกร้า / Cart Item")
    public static class CartItemDTO {

        @Schema(description = "รหัสรายการ / ID")
        private UUID id;

        @Schema(description = "รหัสสินค้า / Item ID")
        private UUID itemId;

        @Schema(description = "รหัสสินค้า / Item Code")
        private String itemCode;

        @Schema(description = "ชื่อสินค้า / Item Name")
        private String itemName;

        @Schema(description = "จำนวน / Quantity", example = "2")
        private Integer quantity;

        @Schema(description = "ราคาต่อหน่วย / Unit Price", example = "500.00")
        private BigDecimal unitPrice;

        @Schema(description = "ราคารวม / Total Price", example = "1000.00")
        private BigDecimal totalPrice;

        @Schema(description = "URL รูปภาพ / Image URL")
        private String imageUrl;
    }
}
