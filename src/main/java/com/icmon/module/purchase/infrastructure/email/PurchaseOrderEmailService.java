package com.icmon.module.purchase.infrastructure.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PurchaseOrderEmailService {

    public void sendPurchaseOrderEmail(String to, String poNumber, byte[] pdfAttachment) {
        log.info("Sending PO {} email to {} (PDF size: {} bytes)", poNumber, to,
                pdfAttachment != null ? pdfAttachment.length : 0);
    }

    public void sendPurchaseOrderStatusEmail(String to, String poNumber, String status) {
        log.info("Sending PO {} status update ({}) to {}", poNumber, status, to);
    }
}
