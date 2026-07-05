package com.icmon.module.dashboard.presentation.dto.request;

import lombok.Data;
import java.util.UUID;

@Data
public class DashboardFilterRequestDTO {
    private UUID customerId;
    private String startDate;
    private String endDate;
    private String branchId;
}
