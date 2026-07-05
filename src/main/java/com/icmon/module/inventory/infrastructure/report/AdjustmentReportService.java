package com.icmon.module.inventory.infrastructure.report;

import com.icmon.module.inventory.infrastructure.entity.InventoryAdjustmentHeaderEntity;
import com.icmon.module.inventory.infrastructure.entity.InventoryAdjustmentDetailEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdjustmentReportService {

    public void generateAdjustmentReport(InventoryAdjustmentHeaderEntity header,
                                          List<InventoryAdjustmentDetailEntity> details,
                                          OutputStream outputStream) {
        log.info("Generating adjustment report for: {}", header.getAdjustmentNo());
        try {
            String content = buildAdjustmentReport(header, details);
            outputStream.write(content.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("Failed to generate adjustment report: {}", e.getMessage());
        }
    }

    public byte[] generateAdjustmentReportPdf(InventoryAdjustmentHeaderEntity header,
                                               List<InventoryAdjustmentDetailEntity> details) {
        log.info("Generating adjustment report PDF for: {}", header.getAdjustmentNo());
        return buildAdjustmentReport(header, details).getBytes(java.nio.charset.StandardCharsets.UTF_8);
    }

    private String buildAdjustmentReport(InventoryAdjustmentHeaderEntity header,
                                          List<InventoryAdjustmentDetailEntity> details) {
        StringBuilder sb = new StringBuilder();
        sb.append("Inventory Adjustment Report\n");
        sb.append("===========================\n\n");
        sb.append("Adjustment No: ").append(header.getAdjustmentNo()).append("\n");
        sb.append("Date: ").append(header.getAdjustmentDate()).append("\n");
        sb.append("Type: ").append(header.getAdjustmentType()).append("\n");
        sb.append("Status: ").append(header.getStatus()).append("\n\n");
        sb.append("Details:\n");
        for (InventoryAdjustmentDetailEntity d : details) {
            sb.append("  Part: ").append(d.getPartId())
              .append(" | Qty: ").append(d.getQuantity())
              .append(" | Cost: ").append(d.getTotalCost())
              .append("\n");
        }
        return sb.toString();
    }
}
