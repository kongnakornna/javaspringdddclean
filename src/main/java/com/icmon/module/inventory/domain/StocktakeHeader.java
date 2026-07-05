package com.icmon.module.inventory.domain;

import com.icmon._shared.domain.GenericBusinessClass;
import com.icmon.module.inventory.domain.enums.StocktakeStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public class StocktakeHeader extends GenericBusinessClass {
    private String stocktakeNo;
    private LocalDateTime stocktakeDate;
    private StocktakeStatus status;
    private UUID startedBy;
    private LocalDateTime startedAt;
    private UUID completedBy;
    private LocalDateTime completedAt;
    private int totalDiscrepancy;
    private String notes;

    public String getStocktakeNo() { return stocktakeNo; }
    public void setStocktakeNo(String stocktakeNo) { this.stocktakeNo = stocktakeNo; }
    public LocalDateTime getStocktakeDate() { return stocktakeDate; }
    public void setStocktakeDate(LocalDateTime stocktakeDate) { this.stocktakeDate = stocktakeDate; }
    public StocktakeStatus getStatus() { return status; }
    public void setStatus(StocktakeStatus status) { this.status = status; }
    public UUID getStartedBy() { return startedBy; }
    public void setStartedBy(UUID startedBy) { this.startedBy = startedBy; }
    public LocalDateTime getStartedAt() { return startedAt; }
    public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }
    public UUID getCompletedBy() { return completedBy; }
    public void setCompletedBy(UUID completedBy) { this.completedBy = completedBy; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
    public int getTotalDiscrepancy() { return totalDiscrepancy; }
    public void setTotalDiscrepancy(int totalDiscrepancy) { this.totalDiscrepancy = totalDiscrepancy; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
