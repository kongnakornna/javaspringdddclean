package com.icmon.module.payment.infrastructure.report;

import com.icmon.module.payment.infrastructure.entity.ReceiptEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReceiptReportGenerator {

    public byte[] generateReceiptPdf(ReceiptEntity receipt) {
        log.info("Generating receipt PDF for: {}", receipt.getReceiptNo());
        StringBuilder sb = new StringBuilder();
        sb.append("RECEIPT\n");
        sb.append("=======\n\n");
        sb.append("Receipt No: ").append(receipt.getReceiptNo()).append("\n");
        sb.append("Date: ").append(receipt.getReceiptDate()).append("\n");
        sb.append("Amount: ").append(receipt.getAmount()).append(" ").append(receipt.getCurrency()).append("\n");
        sb.append("Status: ").append(receipt.getStatus()).append("\n");
        return sb.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8);
    }
}
