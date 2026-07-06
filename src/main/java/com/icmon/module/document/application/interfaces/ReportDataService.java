package com.icmon.module.document.application.interfaces;

import com.icmon.module.document.presentation.dto.response.report.*;

import java.util.UUID;

public interface ReportDataService {
    QuotationReportDTO getQuotationData(UUID quotationId);
    InvoiceReportDTO getInvoiceData(UUID invoiceId);
    PurchaseOrderReportDTO getPurchaseOrderData(UUID poId);
    PartPickingReportDTO getPartPickingData(UUID pickingId);
    ReceiptReportDTO getReceiptData(UUID receiptId);
    CreditNoteReportDTO getCreditNoteData(UUID creditNoteId);
    DebitNoteReportDTO getDebitNoteData(UUID debitNoteId);
    DeliverySheetReportDTO getDeliverySheetData(UUID deliveryId);
    JobCardReportDTO getJobCardData(UUID jobId);
    DailySalesReportDTO getDailySalesData(String date);
    InventorySummaryReportDTO getInventorySummaryData();
    CustomerListReportDTO getCustomerListData();
}
