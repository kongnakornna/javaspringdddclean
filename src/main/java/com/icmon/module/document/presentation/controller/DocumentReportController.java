package com.icmon.module.document.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.document.application.interfaces.ReportDataService;
import com.icmon.module.document.infrastructure.generator.jasper.JasperReportGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController("documentReportController")
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@Tag(name = "Reports", description = "สร้างรายงาน PDF ผ่าน JasperReports // JasperReports PDF Generation APIs")
public class DocumentReportController {

    private final JasperReportGenerator reportGenerator;
    private final ReportDataService reportDataService;

    @GetMapping(value = "/quotation/{quotationId}", produces = MediaType.APPLICATION_PDF_VALUE)
    @RateLimit(limit = 10, duration = 60, keyType = "USER_ID")
    @Operation(summary = "\u0e2a\u0e23\u0e49\u0e32\u0e07 PDF \u0e43\u0e1a\u0e40\u0e2a\u0e19\u0e2d\u0e23\u0e32\u0e04\u0e32 // Generate Quotation PDF")
    public ResponseEntity<byte[]> generateQuotationPdf(@PathVariable UUID quotationId) throws SystemGlobalException {
        try {
            var dto = reportDataService.getQuotationData(quotationId);
            Map<String, Object> params = buildQuotationParams(dto);
            byte[] pdfBytes = reportGenerator.generatePdf("quotation.jrxml", params, dto.getItems());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=quotation_" + quotationId + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (Exception e) {
            throw new SystemGlobalException("Failed to generate Quotation PDF: " + e.getMessage(), e);
        }
    }

    @GetMapping(value = "/invoice/{invoiceId}", produces = MediaType.APPLICATION_PDF_VALUE)
    @RateLimit(limit = 10, duration = 60, keyType = "USER_ID")
    @Operation(summary = "\u0e2a\u0e23\u0e49\u0e32\u0e07 PDF \u0e43\u0e1a\u0e41\u0e08\u0e49\u0e07\u0e2b\u0e19\u0e35\u0e49 // Generate Invoice PDF")
    public ResponseEntity<byte[]> generateInvoicePdf(@PathVariable UUID invoiceId) throws SystemGlobalException {
        try {
            var dto = reportDataService.getInvoiceData(invoiceId);
            Map<String, Object> params = new HashMap<>();
            params.put("companyName", dto.getCompanyName());
            params.put("companyAddress", dto.getCompanyAddress());
            params.put("companyPhone", dto.getCompanyPhone());
            params.put("companyTaxId", dto.getCompanyTaxId());
            params.put("invoiceNo", dto.getInvoiceNo());
            params.put("invoiceDate", dto.getInvoiceDate());
            params.put("customerName", dto.getCustomerName());
            params.put("subtotal", dto.getSubtotal());
            params.put("taxAmount", dto.getTaxAmount());
            params.put("grandTotal", dto.getGrandTotal());
            params.put("paymentStatus", dto.getPaymentStatus());
            byte[] pdfBytes = reportGenerator.generatePdf("invoice.jrxml", params, dto.getItems());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=invoice_" + invoiceId + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (Exception e) {
            throw new SystemGlobalException("Failed to generate Invoice PDF: " + e.getMessage(), e);
        }
    }

    @GetMapping(value = "/purchase-order/{poId}", produces = MediaType.APPLICATION_PDF_VALUE)
    @RateLimit(limit = 10, duration = 60, keyType = "USER_ID")
    @Operation(summary = "\u0e2a\u0e23\u0e49\u0e32\u0e07 PDF \u0e43\u0e1a\u0e2a\u0e31\u0e48\u0e07\u0e0b\u0e37\u0e49\u0e2d // Generate Purchase Order PDF")
    public ResponseEntity<byte[]> generatePurchaseOrderPdf(@PathVariable UUID poId) throws SystemGlobalException {
        try {
            var dto = reportDataService.getPurchaseOrderData(poId);
            Map<String, Object> params = new HashMap<>();
            params.put("companyName", dto.getCompanyName());
            params.put("poNo", dto.getPoNo());
            params.put("poDate", dto.getPoDate());
            params.put("supplierName", dto.getSupplierName());
            params.put("supplierAddress", dto.getSupplierAddress());
            params.put("supplierPhone", dto.getSupplierPhone());
            params.put("supplierTaxId", dto.getSupplierTaxId());
            params.put("deliveryDate", dto.getDeliveryDate());
            params.put("paymentTerms", dto.getPaymentTerms());
            params.put("subtotal", dto.getSubtotal());
            params.put("discount", dto.getDiscount());
            params.put("taxAmount", dto.getTaxAmount());
            params.put("grandTotal", dto.getGrandTotal());
            byte[] pdfBytes = reportGenerator.generatePdf("purchase_order.jrxml", params, dto.getItems());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=purchase_order_" + poId + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (Exception e) {
            throw new SystemGlobalException("Failed to generate Purchase Order PDF: " + e.getMessage(), e);
        }
    }

    @GetMapping(value = "/picking/{pickingId}", produces = MediaType.APPLICATION_PDF_VALUE)
    @RateLimit(limit = 10, duration = 60, keyType = "USER_ID")
    @Operation(summary = "\u0e2a\u0e23\u0e49\u0e32\u0e07 PDF \u0e40\u0e2d\u0e01\u0e2a\u0e32\u0e23\u0e40\u0e1a\u0e34\u0e01\u0e2d\u0e30\u0e44\u0e2b\u0e25\u0e48 // Generate Part Picking PDF")
    public ResponseEntity<byte[]> generatePartPickingPdf(@PathVariable UUID pickingId) throws SystemGlobalException {
        try {
            var dto = reportDataService.getPartPickingData(pickingId);
            Map<String, Object> params = new HashMap<>();
            params.put("pickingNo", dto.getPickingNo());
            params.put("jobNo", dto.getJobNo());
            params.put("requestDate", dto.getRequestDate());
            params.put("requestedBy", dto.getRequestedBy());
            params.put("mechanicName", dto.getMechanicName());
            params.put("licensePlate", dto.getLicensePlate());
            byte[] pdfBytes = reportGenerator.generatePdf("part_picking.jrxml", params, dto.getItems());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=picking_" + pickingId + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (Exception e) {
            throw new SystemGlobalException("Failed to generate Part Picking PDF: " + e.getMessage(), e);
        }
    }

    @GetMapping(value = "/receipt/{receiptId}", produces = MediaType.APPLICATION_PDF_VALUE)
    @RateLimit(limit = 10, duration = 60, keyType = "USER_ID")
    @Operation(summary = "\u0e2a\u0e23\u0e49\u0e32\u0e07 PDF \u0e43\u0e1a\u0e40\u0e2a\u0e23\u0e47\u0e08\u0e23\u0e31\u0e1a\u0e40\u0e07\u0e34\u0e19 // Generate Receipt PDF")
    public ResponseEntity<byte[]> generateReceiptPdf(@PathVariable UUID receiptId) throws SystemGlobalException {
        try {
            var dto = reportDataService.getReceiptData(receiptId);
            Map<String, Object> params = new HashMap<>();
            params.put("companyName", dto.getCompanyName());
            params.put("receiptNo", dto.getReceiptNo());
            params.put("receiptDate", dto.getReceiptDate());
            params.put("customerName", dto.getCustomerName());
            params.put("paymentMethod", dto.getPaymentMethod());
            params.put("amount", dto.getAmount());
            params.put("amountInWords", dto.getAmountInWords());
            params.put("cashierName", dto.getCashierName());
            byte[] pdfBytes = reportGenerator.generatePdf("receipt.jrxml", params, dto.getItems());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=receipt_" + receiptId + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (Exception e) {
            throw new SystemGlobalException("Failed to generate Receipt PDF: " + e.getMessage(), e);
        }
    }

    @GetMapping(value = "/credit-note/{creditNoteId}", produces = MediaType.APPLICATION_PDF_VALUE)
    @RateLimit(limit = 10, duration = 60, keyType = "USER_ID")
    @Operation(summary = "\u0e2a\u0e23\u0e49\u0e32\u0e07 PDF \u0e43\u0e1a\u0e25\u0e14\u0e2b\u0e19\u0e35\u0e49 // Generate Credit Note PDF")
    public ResponseEntity<byte[]> generateCreditNotePdf(@PathVariable UUID creditNoteId) throws SystemGlobalException {
        try {
            var dto = reportDataService.getCreditNoteData(creditNoteId);
            Map<String, Object> params = new HashMap<>();
            params.put("companyName", dto.getCompanyName());
            params.put("creditNoteNo", dto.getCreditNoteNo());
            params.put("creditNoteDate", dto.getCreditNoteDate());
            params.put("referenceInvoiceNo", dto.getReferenceInvoiceNo());
            params.put("customerName", dto.getCustomerName());
            params.put("reason", dto.getReason());
            params.put("totalAmount", dto.getTotalAmount());
            params.put("approvedBy", dto.getApprovedBy());
            byte[] pdfBytes = reportGenerator.generatePdf("credit_note.jrxml", params, dto.getItems());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=credit_note_" + creditNoteId + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (Exception e) {
            throw new SystemGlobalException("Failed to generate Credit Note PDF: " + e.getMessage(), e);
        }
    }

    @GetMapping(value = "/debit-note/{debitNoteId}", produces = MediaType.APPLICATION_PDF_VALUE)
    @RateLimit(limit = 10, duration = 60, keyType = "USER_ID")
    @Operation(summary = "\u0e2a\u0e23\u0e49\u0e32\u0e07 PDF \u0e43\u0e1a\u0e40\u0e1e\u0e34\u0e48\u0e21\u0e2b\u0e19\u0e35\u0e49 // Generate Debit Note PDF")
    public ResponseEntity<byte[]> generateDebitNotePdf(@PathVariable UUID debitNoteId) throws SystemGlobalException {
        try {
            var dto = reportDataService.getDebitNoteData(debitNoteId);
            Map<String, Object> params = new HashMap<>();
            params.put("companyName", dto.getCompanyName());
            params.put("debitNoteNo", dto.getDebitNoteNo());
            params.put("debitNoteDate", dto.getDebitNoteDate());
            params.put("referenceInvoiceNo", dto.getReferenceInvoiceNo());
            params.put("customerName", dto.getCustomerName());
            params.put("reason", dto.getReason());
            params.put("totalAmount", dto.getTotalAmount());
            params.put("approvedBy", dto.getApprovedBy());
            byte[] pdfBytes = reportGenerator.generatePdf("debit_note.jrxml", params, dto.getItems());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=debit_note_" + debitNoteId + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (Exception e) {
            throw new SystemGlobalException("Failed to generate Debit Note PDF: " + e.getMessage(), e);
        }
    }

    @GetMapping(value = "/delivery-sheet/{deliveryId}", produces = MediaType.APPLICATION_PDF_VALUE)
    @RateLimit(limit = 10, duration = 60, keyType = "USER_ID")
    @Operation(summary = "\u0e2a\u0e23\u0e49\u0e32\u0e07 PDF \u0e43\u0e1a\u0e2a\u0e48\u0e07\u0e02\u0e2d\u0e07 // Generate Delivery Sheet PDF")
    public ResponseEntity<byte[]> generateDeliverySheetPdf(@PathVariable UUID deliveryId) throws SystemGlobalException {
        try {
            var dto = reportDataService.getDeliverySheetData(deliveryId);
            Map<String, Object> params = new HashMap<>();
            params.put("companyName", dto.getCompanyName());
            params.put("deliveryNo", dto.getDeliveryNo());
            params.put("deliveryDate", dto.getDeliveryDate());
            params.put("customerName", dto.getCustomerName());
            params.put("customerAddress", dto.getCustomerAddress());
            params.put("customerPhone", dto.getCustomerPhone());
            params.put("referenceNo", dto.getReferenceNo());
            params.put("deliveryPerson", dto.getDeliveryPerson());
            byte[] pdfBytes = reportGenerator.generatePdf("delivery_sheet.jrxml", params, dto.getItems());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=delivery_sheet_" + deliveryId + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (Exception e) {
            throw new SystemGlobalException("Failed to generate Delivery Sheet PDF: " + e.getMessage(), e);
        }
    }

    @GetMapping(value = "/job-card/{jobId}", produces = MediaType.APPLICATION_PDF_VALUE)
    @RateLimit(limit = 10, duration = 60, keyType = "USER_ID")
    @Operation(summary = "\u0e2a\u0e23\u0e49\u0e32\u0e07 PDF \u0e43\u0e1a\u0e07\u0e32\u0e19\u0e0b\u0e48\u0e2d\u0e21 // Generate Job Card PDF")
    public ResponseEntity<byte[]> generateJobCardPdf(@PathVariable UUID jobId) throws SystemGlobalException {
        try {
            var dto = reportDataService.getJobCardData(jobId);
            Map<String, Object> params = new HashMap<>();
            params.put("companyName", dto.getCompanyName());
            params.put("jobNo", dto.getJobNo());
            params.put("jobDate", dto.getJobDate());
            params.put("customerName", dto.getCustomerName());
            params.put("customerPhone", dto.getCustomerPhone());
            params.put("licensePlate", dto.getLicensePlate());
            params.put("carModel", dto.getCarModel());
            params.put("carColor", dto.getCarColor());
            params.put("mileage", dto.getMileage());
            params.put("mechanicName", dto.getMechanicName());
            params.put("serviceAdvisor", dto.getServiceAdvisor());
            params.put("complaint", dto.getComplaint());
            params.put("totalParts", dto.getTotalParts());
            params.put("totalLabor", dto.getTotalLabor());
            params.put("grandTotal", dto.getGrandTotal());
            byte[] pdfBytes = reportGenerator.generatePdf("job_card.jrxml", params, dto.getItems());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=job_card_" + jobId + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (Exception e) {
            throw new SystemGlobalException("Failed to generate Job Card PDF: " + e.getMessage(), e);
        }
    }

    @GetMapping(value = "/daily-sales", produces = MediaType.APPLICATION_PDF_VALUE)
    @RateLimit(limit = 10, duration = 60, keyType = "USER_ID")
    @Operation(summary = "\u0e2a\u0e23\u0e49\u0e32\u0e07 PDF \u0e23\u0e32\u0e22\u0e07\u0e32\u0e19\u0e22\u0e2d\u0e14\u0e02\u0e32\u0e22\u0e23\u0e32\u0e22\u0e27\u0e31\u0e19 // Generate Daily Sales Summary PDF")
    public ResponseEntity<byte[]> generateDailySalesPdf(@RequestParam(required = false, defaultValue = "") String date) throws SystemGlobalException {
        try {
            var dto = reportDataService.getDailySalesData(date.isEmpty() ? java.time.LocalDate.now().toString() : date);
            Map<String, Object> params = new HashMap<>();
            params.put("companyName", dto.getCompanyName());
            params.put("reportDate", dto.getReportDate());
            params.put("totalRevenue", dto.getTotalRevenue());
            params.put("totalInvoices", dto.getTotalInvoices());
            params.put("totalCustomers", dto.getTotalCustomers());
            params.put("avgPerInvoice", dto.getAvgPerInvoice());
            byte[] pdfBytes = reportGenerator.generatePdf("daily_sales.jrxml", params, dto.getItems());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=daily_sales_" + date + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (Exception e) {
            throw new SystemGlobalException("Failed to generate Daily Sales PDF: " + e.getMessage(), e);
        }
    }

    @GetMapping(value = "/inventory-summary", produces = MediaType.APPLICATION_PDF_VALUE)
    @RateLimit(limit = 10, duration = 60, keyType = "USER_ID")
    @Operation(summary = "\u0e2a\u0e23\u0e49\u0e32\u0e07 PDF \u0e23\u0e32\u0e22\u0e07\u0e32\u0e19\u0e2a\u0e34\u0e19\u0e04\u0e49\u0e32\u0e04\u0e07\u0e04\u0e25\u0e31\u0e07 // Generate Inventory Summary PDF")
    public ResponseEntity<byte[]> generateInventorySummaryPdf() throws SystemGlobalException {
        try {
            var dto = reportDataService.getInventorySummaryData();
            Map<String, Object> params = new HashMap<>();
            params.put("companyName", dto.getCompanyName());
            params.put("reportDate", dto.getReportDate());
            params.put("totalItems", dto.getTotalItems());
            params.put("totalValue", dto.getTotalValue());
            params.put("lowStockCount", dto.getLowStockCount());
            byte[] pdfBytes = reportGenerator.generatePdf("inventory_summary.jrxml", params, dto.getItems());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=inventory_summary.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (Exception e) {
            throw new SystemGlobalException("Failed to generate Inventory Summary PDF: " + e.getMessage(), e);
        }
    }

    @GetMapping(value = "/customer-list", produces = MediaType.APPLICATION_PDF_VALUE)
    @RateLimit(limit = 10, duration = 60, keyType = "USER_ID")
    @Operation(summary = "\u0e2a\u0e23\u0e49\u0e32\u0e07 PDF \u0e23\u0e32\u0e22\u0e07\u0e32\u0e19\u0e23\u0e32\u0e22\u0e0a\u0e37\u0e48\u0e2d\u0e25\u0e39\u0e01\u0e04\u0e49\u0e32 // Generate Customer List PDF")
    public ResponseEntity<byte[]> generateCustomerListPdf() throws SystemGlobalException {
        try {
            var dto = reportDataService.getCustomerListData();
            Map<String, Object> params = new HashMap<>();
            params.put("companyName", dto.getCompanyName());
            params.put("reportDate", dto.getReportDate());
            params.put("totalCustomers", dto.getTotalCustomers());
            byte[] pdfBytes = reportGenerator.generatePdf("customer_list.jrxml", params, dto.getItems());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=customer_list.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (Exception e) {
            throw new SystemGlobalException("Failed to generate Customer List PDF: " + e.getMessage(), e);
        }
    }

    private Map<String, Object> buildQuotationParams(com.icmon.module.document.presentation.dto.response.report.QuotationReportDTO dto) {
        Map<String, Object> params = new HashMap<>();
        params.put("companyName", dto.getCompanyName());
        params.put("companyAddress", dto.getCompanyAddress());
        params.put("companyPhone", dto.getCompanyPhone());
        params.put("companyTaxId", dto.getCompanyTaxId());
        params.put("quotationNo", dto.getQuotationNo());
        params.put("quotationDate", dto.getQuotationDate());
        params.put("expiryDate", dto.getExpiryDate());
        params.put("customerName", dto.getCustomerName());
        params.put("customerAddress", dto.getCustomerAddress());
        params.put("customerPhone", dto.getCustomerPhone());
        params.put("jobNo", dto.getJobNo());
        params.put("licensePlate", dto.getLicensePlate());
        params.put("carModel", dto.getCarModel());
        params.put("subtotal", dto.getSubtotal());
        params.put("taxRate", dto.getTaxRate());
        params.put("taxAmount", dto.getTaxAmount());
        params.put("discount", dto.getDiscount());
        params.put("grandTotal", dto.getGrandTotal());
        params.put("amountInWordsTh", dto.getAmountInWordsTh());
        params.put("amountInWordsEn", dto.getAmountInWordsEn());
        params.put("remark", dto.getRemark());
        params.put("createdBy", dto.getCreatedBy());
        return params;
    }
}
