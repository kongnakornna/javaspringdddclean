package com.icmon.module.dashboard.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class DInventoryOverview {
    private UUID whitelabelId;
    private Long totalParts;
    private Long totalQuantity;
    private BigDecimal totalValue;
    private Long lowStockCount;
    private Long activeParts;

    public UUID getWhitelabelId() { return whitelabelId; }
    public void setWhitelabelId(UUID whitelabelId) { this.whitelabelId = whitelabelId; }
    public Long getTotalParts() { return totalParts; }
    public void setTotalParts(Long totalParts) { this.totalParts = totalParts; }
    public Long getTotalQuantity() { return totalQuantity; }
    public void setTotalQuantity(Long totalQuantity) { this.totalQuantity = totalQuantity; }
    public BigDecimal getTotalValue() { return totalValue; }
    public void setTotalValue(BigDecimal totalValue) { this.totalValue = totalValue; }
    public Long getLowStockCount() { return lowStockCount; }
    public void setLowStockCount(Long lowStockCount) { this.lowStockCount = lowStockCount; }
    public Long getActiveParts() { return activeParts; }
    public void setActiveParts(Long activeParts) { this.activeParts = activeParts; }
}
