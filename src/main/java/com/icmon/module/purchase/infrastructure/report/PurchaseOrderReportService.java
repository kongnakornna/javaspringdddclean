package com.icmon.module.purchase.infrastructure.report;

import com.icmon.module.purchase.infrastructure.entity.PurchaseOrderHeaderEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.OutputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseOrderReportService {

    public void generatePurchaseOrderReport(PurchaseOrderHeaderEntity po, OutputStream outputStream) {
        log.info("Generating report for PO: {}", po.getPoNo());
        try {
            String content = buildReportContent(po);
            outputStream.write(content.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("Failed to generate report for PO {}: {}", po.getPoNo(), e.getMessage());
        }
    }

    public byte[] generatePurchaseOrderPdf(PurchaseOrderHeaderEntity po) {
        log.info("Generating PDF for PO: {}", po.getPoNo());
        String content = buildReportContent(po);
        return content.getBytes(java.nio.charset.StandardCharsets.UTF_8);
    }

    private String buildReportContent(PurchaseOrderHeaderEntity po) {
        StringBuilder sb = new StringBuilder();
        sb.append("Purchase Order: ").append(po.getPoNo()).append("\n");
        sb.append("Date: ").append(po.getPoDate()).append("\n");
        sb.append("Status: ").append(po.getStatus()).append("\n");
        sb.append("Total: ").append(po.getTotal()).append("\n");
        return sb.toString();
    }
}
