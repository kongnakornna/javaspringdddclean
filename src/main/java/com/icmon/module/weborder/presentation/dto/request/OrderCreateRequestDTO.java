package com.icmon.module.weborder.presentation.dto.request;

import com.icmon.module.weborder.domain.enums.OrderSource;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "คำขอสร้างออเดอร์ / Create Order Request")
public class OrderCreateRequestDTO {

    @NotNull(message = "Cart ID is required")
    @Schema(description = "รหัสตะกร้าสินค้า / Cart ID", example = "cart_1234567890", requiredMode = Schema.RequiredMode.REQUIRED)
    private String cartId;

    @NotNull(message = "Customer ID is required")
    @Schema(description = "รหัสลูกค้า / Customer ID", example = "b2c3d4e5-f6a7-8901-bcde-f12345678901", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID customerId;

    @Schema(description = "แหล่งที่มาออเดอร์ / Order Source", example = "WEB")
    private OrderSource orderSource;

    @NotBlank(message = "Shipping address is required")
    @Schema(description = "ที่อยู่จัดส่ง / Shipping Address", example = "123 ถนนสุขุมวิท แขวงคลองเตย เขตคลองเตย กรุงเทพฯ 10110", requiredMode = Schema.RequiredMode.REQUIRED)
    private String shippingAddress;

    @Schema(description = "เบอร์โทรจัดส่ง / Shipping Phone", example = "0812345678")
    private String shippingPhone;

    @Schema(description = "อีเมลจัดส่ง / Shipping Email", example = "customer@example.com")
    private String shippingEmail;

    @Schema(description = "หมายเหตุ / Notes", example = "กรุณาจัดส่งในช่วงเช้า")
    private String notes;

    @Schema(description = "วิธีการชำระเงิน / Payment Method", example = "CREDIT_CARD")
    private String paymentMethod;
}
