package com.icmon.module.email.infrastructure.consumer;

import com.icmon.module.email.application.interfaces.EmailService;
import com.icmon.module.email.presentation.dto.request.EmailSendRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(EmailEventConsumer.class);
    private final EmailService emailService;

    public EmailEventConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    public void consumeEmailEvent(EmailEvent event) {
        try {
            log.info("Received email event: {}", event.getEventType());

            switch (event.getEventType()) {
                case "QUOTATION_APPROVED":
                    sendQuotationEmail(event.getData());
                    break;
                case "PO_CREATED":
                    sendPOEmail(event.getData());
                    break;
                case "INVOICE_CREATED":
                    sendInvoiceEmail(event.getData());
                    break;
                case "PAYMENT_RECEIVED":
                    sendReceiptEmail(event.getData());
                    break;
                default:
                    log.warn("Unknown email event type: {}", event.getEventType());
            }
        } catch (Exception e) {
            log.error("Error processing email event: {}", e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private void sendQuotationEmail(Map<String, Object> data) throws Exception {
        EmailSendRequestDTO request = new EmailSendRequestDTO();
        request.setTemplateCode("QUOTATION_EMAIL");
        request.setToEmail((String) data.get("customerEmail"));
        request.setVariables(toMapString(data.get("variables")));
        emailService.sendTemplateEmail(request);
    }

    @SuppressWarnings("unchecked")
    private void sendPOEmail(Map<String, Object> data) throws Exception {
        EmailSendRequestDTO request = new EmailSendRequestDTO();
        request.setTemplateCode("PO_EMAIL");
        request.setToEmail((String) data.get("supplierEmail"));
        request.setVariables(toMapString(data.get("variables")));
        emailService.sendTemplateEmail(request);
    }

    @SuppressWarnings("unchecked")
    private void sendInvoiceEmail(Map<String, Object> data) throws Exception {
        EmailSendRequestDTO request = new EmailSendRequestDTO();
        request.setTemplateCode("INVOICE_EMAIL");
        request.setToEmail((String) data.get("customerEmail"));
        request.setVariables(toMapString(data.get("variables")));
        emailService.sendTemplateEmail(request);
    }

    @SuppressWarnings("unchecked")
    private void sendReceiptEmail(Map<String, Object> data) throws Exception {
        EmailSendRequestDTO request = new EmailSendRequestDTO();
        request.setTemplateCode("RECEIPT_EMAIL");
        request.setToEmail((String) data.get("customerEmail"));
        request.setVariables(toMapString(data.get("variables")));
        emailService.sendTemplateEmail(request);
    }

    private Map<String, String> toMapString(Object obj) {
        if (obj instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) obj;
            Map<String, String> result = new HashMap<>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                result.put(entry.getKey(), entry.getValue() != null ? entry.getValue().toString() : "");
            }
            return result;
        }
        return new HashMap<>();
    }

    public static class EmailEvent {

        private String eventType;
        private Map<String, Object> data;

        public EmailEvent() {}

        public EmailEvent(String eventType, Map<String, Object> data) {
            this.eventType = eventType;
            this.data = data;
        }

        public String getEventType() { return eventType; }
        public void setEventType(String eventType) { this.eventType = eventType; }
        public Map<String, Object> getData() { return data; }
        public void setData(Map<String, Object> data) { this.data = data; }
    }
}
