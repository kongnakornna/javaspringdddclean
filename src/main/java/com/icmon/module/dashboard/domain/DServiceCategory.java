package com.icmon.module.dashboard.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class DServiceCategory {
    private String categoryName;
    private Long serviceCount;
    private BigDecimal totalRevenue;
    private UUID whitelabelId;

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public Long getServiceCount() { return serviceCount; }
    public void setServiceCount(Long serviceCount) { this.serviceCount = serviceCount; }
    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }
    public UUID getWhitelabelId() { return whitelabelId; }
    public void setWhitelabelId(UUID whitelabelId) { this.whitelabelId = whitelabelId; }
}
