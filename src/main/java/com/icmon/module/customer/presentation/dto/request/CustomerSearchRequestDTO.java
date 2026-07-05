package com.icmon.module.customer.presentation.dto.request;

import com.icmon.module.customer.domain.enums.CustomerStatus;
import com.icmon.module.customer.domain.enums.CustomerType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "คำขอค้นหาลูกค้า - Customer search request")
@Data
public class CustomerSearchRequestDTO {
    @Schema(description = "ชื่อลูกค้า - Customer name", example = "สมชาย")
    private String name;
    @Schema(description = "เบอร์โทรศัพท์ - Phone number", example = "0812345678")
    private String phone;
    @Schema(description = "ประเภทลูกค้า - Customer type", example = "INDIVIDUAL")
    private CustomerType customerType;
    @Schema(description = "สถานะลูกค้า - Customer status", example = "ACTIVE")
    private CustomerStatus status;
}
