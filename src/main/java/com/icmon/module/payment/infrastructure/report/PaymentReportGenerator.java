package com.icmon.module.payment.infrastructure.report;

import com.icmon.module.payment.infrastructure.entity.PaymentEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PaymentReportGenerator {

    public byte[] generatePaymentSummaryReport(List<PaymentEntity> payments) {
        log.info("Generating payment summary report for {} payments", payments.size());
        StringBuilder sb = new StringBuilder();
        sb.append("Payment Summary Report\n");
        sb.append("======================\n\n");
        for (PaymentEntity p : payments) {
            sb.append("No: ").append(p.getPaymentNo())
              .append(" | Amount: ").append(p.getAmount())
              .append(" | Status: ").append(p.getStatus())
              .append("\n");
        }
        return sb.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8);
    }
}
