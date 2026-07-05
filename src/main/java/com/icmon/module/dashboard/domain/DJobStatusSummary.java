package com.icmon.module.dashboard.domain;

import java.util.UUID;

public class DJobStatusSummary {
    private String status;
    private Long count;
    private UUID whitelabelId;

    public Double getPercentage(Long total) {
        if (total == null || total == 0) {
            return 0.0;
        }
        return (this.count.doubleValue() / total.doubleValue()) * 100;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Long getCount() { return count; }
    public void setCount(Long count) { this.count = count; }
    public UUID getWhitelabelId() { return whitelabelId; }
    public void setWhitelabelId(UUID whitelabelId) { this.whitelabelId = whitelabelId; }
}
