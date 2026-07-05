package com.icmon.module.inventory.infrastructure.report;

import com.icmon.module.inventory.infrastructure.entity.InventoryEntity;
import com.icmon.module.inventory.infrastructure.entity.PartMasterEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockReportGenerator {

    public void generateStockReport(List<PartMasterEntity> parts, OutputStream outputStream) {
        log.info("Generating stock report for {} parts", parts.size());
        try {
            String content = buildStockReport(parts);
            outputStream.write(content.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("Failed to generate stock report: {}", e.getMessage());
        }
    }

    public byte[] generateStockReportPdf(List<PartMasterEntity> parts) {
        log.info("Generating stock report PDF for {} parts", parts.size());
        return buildStockReport(parts).getBytes(java.nio.charset.StandardCharsets.UTF_8);
    }

    public byte[] generateInventoryTransactionReport(List<InventoryEntity> transactions) {
        log.info("Generating inventory transaction report for {} transactions", transactions.size());
        StringBuilder sb = new StringBuilder();
        sb.append("Inventory Transaction Report\n");
        sb.append("============================\n\n");
        for (InventoryEntity t : transactions) {
            sb.append("Type: ").append(t.getTransactionType())
              .append(" | Part: ").append(t.getPartId())
              .append(" | Qty: ").append(t.getQuantity())
              .append(" | Date: ").append(t.getTransactionDate())
              .append("\n");
        }
        return sb.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8);
    }

    private String buildStockReport(List<PartMasterEntity> parts) {
        StringBuilder sb = new StringBuilder();
        sb.append("Stock Report\n");
        sb.append("============\n\n");
        for (PartMasterEntity p : parts) {
            sb.append("Code: ").append(p.getPartCode())
              .append(" | Name: ").append(p.getPartName())
              .append(" | Stock: ").append(p.getStockQuantity())
              .append(" | Status: ").append(p.getStatus())
              .append("\n");
        }
        return sb.toString();
    }
}
