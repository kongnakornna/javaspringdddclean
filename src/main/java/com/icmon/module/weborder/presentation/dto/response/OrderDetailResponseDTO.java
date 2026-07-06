package com.icmon.module.weborder.presentation.dto.response;

import com.icmon.module.weborder.domain.enums.OrderSource;
import com.icmon.module.weborder.domain.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Schema(description = "รายละเอียดออเดอร์ / Order Detail Response")
public class OrderDetailResponseDTO {

    @Schema(description = "รหัสออเดอร์ / ID")
    private UUID id;

    @Schema(description = "เลขที่ออเดอร์ / Order No", example = "WO-2026-0001")
    private String orderNo;

    @Schema(description = "รหัสตะกร้า / Cart ID")
    private UUID cartId;

    @Schema(description = "รหัสลูกค้า / Customer ID")
    private UUID customerId;

    @Schema(description = "วันที่สั่งซื้อ / Order Date")
    private LocalDateTime orderDate;

    @Schema(description = "แหล่งที่มา / Order Source", example = "WEB")
    private OrderSource orderSource;

    @Schema(description = "สถานะ / Status", example = "PENDING")
    private OrderStatus status;

    @Schema(description = "สถานะการชำระเงิน / Payment Status", example = "PENDING")
    private String paymentStatus;

    @Schema(description = "ยอดก่อนส่วนลด / Subtotal", example = "1500.00")
    private BigDecimal subtotal;

    @Schema(description = "ส่วนลด / Discount", example = "100.00")
    private BigDecimal discount;

    @Schema(description = "ภาษี / Tax", example = "98.00")
    private BigDecimal tax;

    @Schema(description = "ค่าจัดส่ง / Shipping Cost", example = "50.00")
    private BigDecimal shippingCost;

    @Schema(description = "ยอดสุทธิ / Total", example = "1548.00")
    private BigDecimal total;

    @Schema(description = "โค้ดส่วนลด / Promotion Code", example = "WELCOME10")
    private String promotionCode;

    @Schema(description = "ที่อยู่จัดส่ง / Shipping Address", example = "123 ถนนสุขุมวิท กรุงเทพฯ")
    private String shippingAddress;

    @Schema(description = "เบอร์โทรจัดส่ง / Shipping Phone", example = "0812345678")
    private String shippingPhone;

    @Schema(description = "อีเมลจัดส่ง / Shipping Email", example = "customer@example.com")
    private String shippingEmail;

    @Schema(description = "เลขพัสดุ / Tracking Number", example = "TH1234567890")
    private String trackingNumber;

    @Schema(description = "ผู้ให้บริการขนส่ง / Courier", example = "Kerry Express")
    private String courier;

    @Schema(description = "หมายเหตุ / Notes", example = "จัดส่งในช่วงเช้า")
    private String notes;

    @Schema(description = "วิธีการชำระเงิน / Payment Method", example = "CREDIT_CARD")
    private String paymentMethod;

    @Schema(description = "วันที่ชำระเงิน / Paid At")
    private LocalDateTime paidAt;

    @Schema(description = "วันที่จัดส่งสำเร็จ / Delivered At")
    private LocalDateTime deliveredAt;

    @Schema(description = "เหตุผลยกเลิก / Cancellation Reason")
    private String cancellationReason;

    @Schema(description = "รายการสินค้า / Order Items")
    private List<OrderItemDTO> items;

    @Data
    @Builder
    @Schema(description = "รายการสินค้าในออเดอร์ / Order Item")
    public static class OrderItemDTO {

        @Schema(description = "รหัสรายการ / ID")
        private UUID id;

        @Schema(description = "รหัสสินค้า / Item ID")
        private UUID itemId;

        @Schema(description = "ชื่อสินค้า / Item Name")
        private String itemName;

        @Schema(description = "จำนวน / Quantity", example = "2")
        private Integer quantity;

        @Schema(description = "ราคาต่อหน่วย / Unit Price", example = "500.00")
        private BigDecimal unitPrice;

        @Schema(description = "ราคารวม / Total Price", example = "1000.00")
        private BigDecimal totalPrice;

        @Schema(description = "ส่วนลด / Discount", example = "0.00")
        private BigDecimal discount;

        @Schema(description = "ราคาสุทธิ / Net Price", example = "1000.00")
        private BigDecimal netPrice;
    }
}
