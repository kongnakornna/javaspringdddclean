package com.icmon.module.document.presentation.controller;

import com.icmon.module.document.application.interfaces.ReportDataService;
import com.icmon.module.document.infrastructure.generator.jasper.JasperReportGenerator;
import com.icmon.module.document.presentation.dto.response.report.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DocumentReportController.class)
class DocumentReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JasperReportGenerator reportGenerator;

    @MockBean
    private ReportDataService reportDataService;

    @Test
    void testGenerateQuotationPdf() throws Exception {
        UUID id = UUID.randomUUID();
        QuotationReportDTO dto = QuotationReportDTO.builder()
                .companyName("Test Company")
                .quotationNo("QTN-001")
                .subtotal(BigDecimal.TEN)
                .taxAmount(BigDecimal.ONE)
                .grandTotal(new BigDecimal("11.00"))
                .items(Collections.emptyList())
                .build();
        when(reportDataService.getQuotationData(id)).thenReturn(dto);
        when(reportGenerator.generatePdf(anyString(), anyMap(), anyList())).thenReturn("PDF Content".getBytes());

        mockMvc.perform(get("/api/v1/reports/quotation/{id}", id))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "inline; filename=quotation_" + id + ".pdf"))
                .andExpect(content().contentType(MediaType.APPLICATION_PDF));
    }

    @Test
    void testGenerateInvoicePdf() throws Exception {
        UUID id = UUID.randomUUID();
        InvoiceReportDTO dto = InvoiceReportDTO.builder()
                .companyName("Test Company")
                .invoiceNo("INV-001")
                .grandTotal(new BigDecimal("10700.00"))
                .items(Collections.emptyList())
                .build();
        when(reportDataService.getInvoiceData(id)).thenReturn(dto);
        when(reportGenerator.generatePdf(anyString(), anyMap(), anyList())).thenReturn("PDF Content".getBytes());

        mockMvc.perform(get("/api/v1/reports/invoice/{id}", id))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "inline; filename=invoice_" + id + ".pdf"))
                .andExpect(content().contentType(MediaType.APPLICATION_PDF));
    }

    @Test
    void testGeneratePurchaseOrderPdf() throws Exception {
        UUID id = UUID.randomUUID();
        PurchaseOrderReportDTO dto = PurchaseOrderReportDTO.builder()
                .poNo("PO-001")
                .supplierName("Test Supplier")
                .grandTotal(new BigDecimal("50000.00"))
                .items(Collections.emptyList())
                .build();
        when(reportDataService.getPurchaseOrderData(id)).thenReturn(dto);
        when(reportGenerator.generatePdf(anyString(), anyMap(), anyList())).thenReturn("PDF Content".getBytes());

        mockMvc.perform(get("/api/v1/reports/purchase-order/{id}", id))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "inline; filename=purchase_order_" + id + ".pdf"));
    }

    @Test
    void testGeneratePartPickingPdf() throws Exception {
        UUID id = UUID.randomUUID();
        PartPickingReportDTO dto = PartPickingReportDTO.builder()
                .pickingNo("PK-001")
                .jobNo("JB-001")
                .items(Collections.emptyList())
                .build();
        when(reportDataService.getPartPickingData(id)).thenReturn(dto);
        when(reportGenerator.generatePdf(anyString(), anyMap(), anyList())).thenReturn("PDF Content".getBytes());

        mockMvc.perform(get("/api/v1/reports/picking/{id}", id))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "inline; filename=picking_" + id + ".pdf"));
    }

    @Test
    void testGenerateReceiptPdf() throws Exception {
        UUID id = UUID.randomUUID();
        ReceiptReportDTO dto = ReceiptReportDTO.builder()
                .receiptNo("RCP-001")
                .amount(new BigDecimal("500.00"))
                .items(Collections.emptyList())
                .build();
        when(reportDataService.getReceiptData(id)).thenReturn(dto);
        when(reportGenerator.generatePdf(anyString(), anyMap(), anyList())).thenReturn("PDF Content".getBytes());

        mockMvc.perform(get("/api/v1/reports/receipt/{id}", id))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "inline; filename=receipt_" + id + ".pdf"));
    }

    @Test
    void testGenerateCreditNotePdf() throws Exception {
        UUID id = UUID.randomUUID();
        CreditNoteReportDTO dto = CreditNoteReportDTO.builder()
                .creditNoteNo("CN-001")
                .totalAmount(new BigDecimal("5000.00"))
                .items(Collections.emptyList())
                .build();
        when(reportDataService.getCreditNoteData(id)).thenReturn(dto);
        when(reportGenerator.generatePdf(anyString(), anyMap(), anyList())).thenReturn("PDF Content".getBytes());

        mockMvc.perform(get("/api/v1/reports/credit-note/{id}", id))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "inline; filename=credit_note_" + id + ".pdf"));
    }

    @Test
    void testGenerateDebitNotePdf() throws Exception {
        UUID id = UUID.randomUUID();
        DebitNoteReportDTO dto = DebitNoteReportDTO.builder()
                .debitNoteNo("DN-001")
                .totalAmount(new BigDecimal("2000.00"))
                .items(Collections.emptyList())
                .build();
        when(reportDataService.getDebitNoteData(id)).thenReturn(dto);
        when(reportGenerator.generatePdf(anyString(), anyMap(), anyList())).thenReturn("PDF Content".getBytes());

        mockMvc.perform(get("/api/v1/reports/debit-note/{id}", id))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "inline; filename=debit_note_" + id + ".pdf"));
    }

    @Test
    void testGenerateDeliverySheetPdf() throws Exception {
        UUID id = UUID.randomUUID();
        DeliverySheetReportDTO dto = DeliverySheetReportDTO.builder()
                .deliveryNo("DLV-001")
                .customerName("Test Customer")
                .items(Collections.emptyList())
                .build();
        when(reportDataService.getDeliverySheetData(id)).thenReturn(dto);
        when(reportGenerator.generatePdf(anyString(), anyMap(), anyList())).thenReturn("PDF Content".getBytes());

        mockMvc.perform(get("/api/v1/reports/delivery-sheet/{id}", id))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "inline; filename=delivery_sheet_" + id + ".pdf"));
    }

    @Test
    void testGenerateJobCardPdf() throws Exception {
        UUID id = UUID.randomUUID();
        JobCardReportDTO dto = JobCardReportDTO.builder()
                .jobNo("JB-001")
                .customerName("Test Customer")
                .grandTotal(new BigDecimal("10000.00"))
                .items(Collections.emptyList())
                .build();
        when(reportDataService.getJobCardData(id)).thenReturn(dto);
        when(reportGenerator.generatePdf(anyString(), anyMap(), anyList())).thenReturn("PDF Content".getBytes());

        mockMvc.perform(get("/api/v1/reports/job-card/{id}", id))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "inline; filename=job_card_" + id + ".pdf"));
    }

    @Test
    void testGenerateDailySalesPdf() throws Exception {
        DailySalesReportDTO dto = DailySalesReportDTO.builder()
                .reportDate("2026-07-04")
                .totalRevenue(new BigDecimal("107000.00"))
                .items(Collections.emptyList())
                .build();
        when(reportDataService.getDailySalesData(anyString())).thenReturn(dto);
        when(reportGenerator.generatePdf(anyString(), anyMap(), anyList())).thenReturn("PDF Content".getBytes());

        mockMvc.perform(get("/api/v1/reports/daily-sales").param("date", "2026-07-04"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "inline; filename=daily_sales_2026-07-04.pdf"));
    }

    @Test
    void testGenerateInventorySummaryPdf() throws Exception {
        InventorySummaryReportDTO dto = InventorySummaryReportDTO.builder()
                .totalItems(150)
                .totalValue(new BigDecimal("2500000.00"))
                .items(Collections.emptyList())
                .build();
        when(reportDataService.getInventorySummaryData()).thenReturn(dto);
        when(reportGenerator.generatePdf(anyString(), anyMap(), anyList())).thenReturn("PDF Content".getBytes());

        mockMvc.perform(get("/api/v1/reports/inventory-summary"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "inline; filename=inventory_summary.pdf"));
    }

    @Test
    void testGenerateCustomerListPdf() throws Exception {
        CustomerListReportDTO dto = CustomerListReportDTO.builder()
                .totalCustomers(200)
                .items(Collections.emptyList())
                .build();
        when(reportDataService.getCustomerListData()).thenReturn(dto);
        when(reportGenerator.generatePdf(anyString(), anyMap(), anyList())).thenReturn("PDF Content".getBytes());

        mockMvc.perform(get("/api/v1/reports/customer-list"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "inline; filename=customer_list.pdf"));
    }
}
