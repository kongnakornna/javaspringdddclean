package com.icmon.module.dashboard.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class DTopSellingParts {
    private UUID partId;
    private String partCode;
    private String partName;
    private Long totalSold;
    private BigDecimal totalRevenue;
    private Integer rank;
    private UUID whitelabelId;

    public UUID getPartId() { return partId; }
    public void setPartId(UUID partId) { this.partId = partId; }
    public String getPartCode() { return partCode; }
    public void setPartCode(String partCode) { this.partCode = partCode; }
    public String getPartName() { return partName; }
    public void setPartName(String partName) { this.partName = partName; }
    public Long getTotalSold() { return totalSold; }
    public void setTotalSold(Long totalSold) { this.totalSold = totalSold; }
    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }
    public Integer getRank() { return rank; }
    public void setRank(Integer rank) { this.rank = rank; }
    public UUID getWhitelabelId() { return whitelabelId; }
    public void setWhitelabelId(UUID whitelabelId) { this.whitelabelId = whitelabelId; }
}
