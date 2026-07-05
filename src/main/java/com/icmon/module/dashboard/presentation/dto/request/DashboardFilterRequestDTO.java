package com.icmon.module.dashboard.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.UUID;

@Data
@Schema(description = "ตัวกรองข้อมูลแดชบอร์ด / Dashboard filter request")
public class DashboardFilterRequestDTO {
    @Schema(description = "รหัสลูกค้า / Customer ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
    private UUID customerId;
    @Schema(description = "วันที่เริ่มต้น / Start date", example = "2024-01-01")
    private String startDate;
    @Schema(description = "วันที่สิ้นสุด / End date", example = "2024-12-31")
    private String endDate;
    @Schema(description = "รหัสสาขา / Branch ID", example = "BR001")
    private String branchId;
}
