package com.icmon.module.document.application.service;

import com.icmon.module.document.application.interfaces.ReportDataService;
import com.icmon.module.document.presentation.dto.response.report.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.UUID;

@Service
@Slf4j
public class ReportDataServiceImpl implements ReportDataService {

    @Override
    public QuotationReportDTO getQuotationData(UUID quotationId) {
        log.info("Fetching quotation report data for id: {}", quotationId);
        return QuotationReportDTO.builder()
                .companyName("ICMON Auto Repair")
                .companyAddress("123/45 \u0e16\u0e19\u0e19\u0e1e\u0e31\u0e13\u0e11\u0e19\u0e32 \u0e41\u0e02\u0e27\u0e07\u0e1a\u0e32\u0e07\u0e01\u0e30\u0e1b\u0e34 \u0e40\u0e02\u0e15\u0e1a\u0e32\u0e07\u0e01\u0e30\u0e1b\u0e34 \u0e01\u0e23\u0e38\u0e07\u0e40\u0e17\u0e1e 10110")
                .companyPhone("02-123-4567")
                .companyTaxId("0105555123456")
                .quotationNo("QTN-" + quotationId.toString().substring(0, 8).toUpperCase())
                .quotationDate(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .expiryDate(LocalDate.now().plusDays(30).format(DateTimeFormatter.ISO_LOCAL_DATE))
                .customerName("\u0e1a\u0e23\u0e34\u0e29\u0e31\u0e17 \u0e15\u0e31\u0e27\u0e2d\u0e22\u0e48\u0e32\u0e07 \u0e08\u0e33\u0e01\u0e31\u0e14")
                .customerAddress("456 \u0e16\u0e19\u0e19\u0e2a\u0e38\u0e02\u0e38\u0e21\u0e27\u0e34\u0e17 \u0e41\u0e02\u0e27\u0e07\u0e1a\u0e32\u0e07\u0e19\u0e32 \u0e40\u0e02\u0e15\u0e1a\u0e32\u0e07\u0e19\u0e32 \u0e01\u0e23\u0e38\u0e07\u0e40\u0e17\u0e1e 10260")
                .customerPhone("08-1234-5678")
                .jobNo("JB-2026-0001")
                .licensePlate("\u0e01\u0e0a 1234 \u0e01\u0e23\u0e38\u0e07\u0e40\u0e17\u0e1e")
                .carModel("Toyota Camry 2020")
                .subtotal(new BigDecimal("10000.00"))
                .taxRate(new BigDecimal("0.07"))
                .taxAmount(new BigDecimal("700.00"))
                .discount(BigDecimal.ZERO)
                .grandTotal(new BigDecimal("10700.00"))
                .amountInWordsTh("\u0e2b\u0e19\u0e36\u0e48\u0e07\u0e2b\u0e21\u0e37\u0e48\u0e19\u0e40\u0e08\u0e47\u0e14\u0e23\u0e49\u0e2d\u0e22\u0e1a\u0e32\u0e17\u0e16\u0e49\u0e27\u0e19")
                .amountInWordsEn("Ten Thousand Seven Hundred Baht Only")
                .remark("\u0e23\u0e32\u0e04\u0e32\u0e19\u0e35\u0e49\u0e23\u0e27\u0e21\u0e20\u0e32\u0e29\u0e35\u0e21\u0e39\u0e25\u0e04\u0e48\u0e32\u0e40\u0e1e\u0e34\u0e48\u0e21\u0e41\u0e25\u0e49\u0e27")
                .createdBy("\u0e1e\u0e19\u0e31\u0e01\u0e07\u0e32\u0e19\u0e02\u0e32\u0e22")
                .items(Collections.singletonList(
                        QuotationReportDTO.QuotationItemDTO.builder()
                                .lineNo(1)
                                .partCode("OIL-001")
                                .description("\u0e19\u0e49\u0e33\u0e21\u0e31\u0e19\u0e40\u0e04\u0e23\u0e37\u0e48\u0e2d\u0e07\u0e41\u0e25\u0e30\u0e01\u0e23\u0e2d\u0e07\u0e01\u0e23\u0e30\u0e40\u0e1a\u0e37\u0e49\u0e2d\u0e07\u0e01\u0e23\u0e30\u0e14\u0e39\u0e01\u0e1b\u0e35\u0e01\u0e40\u0e25\u0e47\u0e01")
                                .quantity(4)
                                .unitPrice(new BigDecimal("2500.00"))
                                .totalPrice(new BigDecimal("10000.00"))
                                .build()
                ))
                .build();
    }

    @Override
    public InvoiceReportDTO getInvoiceData(UUID invoiceId) {
        return InvoiceReportDTO.builder()
                .companyName("ICMON Auto Repair")
                .companyAddress("123/45 \u0e16\u0e19\u0e19\u0e1e\u0e31\u0e13\u0e11\u0e19\u0e32 \u0e41\u0e02\u0e27\u0e07\u0e1a\u0e32\u0e07\u0e01\u0e30\u0e1b\u0e34 \u0e40\u0e02\u0e15\u0e1a\u0e32\u0e07\u0e01\u0e30\u0e1b\u0e34 \u0e01\u0e23\u0e38\u0e07\u0e40\u0e17\u0e1e 10110")
                .invoiceNo("INV-" + invoiceId.toString().substring(0, 8).toUpperCase())
                .invoiceDate(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .customerName("\u0e1a\u0e23\u0e34\u0e29\u0e31\u0e17 \u0e15\u0e31\u0e27\u0e2d\u0e22\u0e48\u0e32\u0e07 \u0e08\u0e33\u0e01\u0e31\u0e14")
                .subtotal(new BigDecimal("10000.00"))
                .taxAmount(new BigDecimal("700.00"))
                .grandTotal(new BigDecimal("10700.00"))
                .paymentStatus("\u0e22\u0e31\u0e07\u0e44\u0e21\u0e48\u0e0a\u0e33\u0e23\u0e30")
                .items(Collections.singletonList(
                        InvoiceReportDTO.InvoiceItemDTO.builder()
                                .description("\u0e1a\u0e23\u0e34\u0e01\u0e32\u0e23\u0e40\u0e1b\u0e25\u0e35\u0e48\u0e22\u0e19\u0e19\u0e49\u0e33\u0e21\u0e31\u0e19\u0e40\u0e04\u0e23\u0e37\u0e48\u0e2d\u0e07\u0e41\u0e25\u0e30\u0e01\u0e23\u0e2d\u0e07\u0e01\u0e23\u0e30\u0e40\u0e1a\u0e37\u0e49\u0e2d\u0e07")
                                .quantity(1)
                                .unitPrice(new BigDecimal("10000.00"))
                                .totalPrice(new BigDecimal("10000.00"))
                                .build()
                ))
                .build();
    }

    @Override
    public PurchaseOrderReportDTO getPurchaseOrderData(UUID poId) {
        return PurchaseOrderReportDTO.builder()
                .companyName("ICMON Auto Repair")
                .poNo("PO-" + poId.toString().substring(0, 8).toUpperCase())
                .poDate(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .supplierName("\u0e1a\u0e23\u0e34\u0e29\u0e31\u0e17 \u0e15\u0e31\u0e27\u0e41\u0e17\u0e19\u0e08\u0e33\u0e2b\u0e19\u0e48\u0e32\u0e22 \u0e08\u0e33\u0e01\u0e31\u0e14")
                .supplierAddress("789 \u0e16\u0e19\u0e19\u0e1e\u0e23\u0e30\u0e23\u0e32\u0e21 9 \u0e41\u0e02\u0e27\u0e07\u0e2b\u0e31\u0e27\u0e02\u0e27\u0e32\u0e07 \u0e40\u0e02\u0e15\u0e1a\u0e32\u0e07\u0e01\u0e2d\u0e01\u0e40\u0e1b\u0e37\u0e2d \u0e01\u0e23\u0e38\u0e07\u0e40\u0e17\u0e1e 10330")
                .supplierPhone("02-987-6543")
                .supplierTaxId("0105554987654")
                .deliveryDate(LocalDate.now().plusDays(7).format(DateTimeFormatter.ISO_LOCAL_DATE))
                .paymentTerms("\u0e40\u0e07\u0e34\u0e19\u0e2a\u0e14 30 \u0e27\u0e31\u0e19")
                .subtotal(new BigDecimal("50000.00"))
                .discount(new BigDecimal("5000.00"))
                .taxAmount(new BigDecimal("3150.00"))
                .grandTotal(new BigDecimal("48150.00"))
                .items(Collections.singletonList(
                        PurchaseOrderReportDTO.POItemDTO.builder()
                                .lineNo(1)
                                .partCode("BRAKE-001")
                                .description("\u0e1c\u0e49\u0e32\u0e40\u0e1a\u0e23\u0e01\u0e04\u0e38\u0e13\u0e20\u0e32\u0e1e\u0e2a\u0e39\u0e07 \u0e41\u0e17\u0e48\u0e07\u0e25\u0e30 4 \u0e0a\u0e34\u0e49\u0e19")
                                .quantity(10)
                                .unitPrice(new BigDecimal("5000.00"))
                                .totalPrice(new BigDecimal("50000.00"))
                                .build()
                ))
                .build();
    }

    @Override
    public PartPickingReportDTO getPartPickingData(UUID pickingId) {
        return PartPickingReportDTO.builder()
                .pickingNo("PK-" + pickingId.toString().substring(0, 8).toUpperCase())
                .jobNo("JB-2026-0001")
                .requestDate(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .requestedBy("\u0e2a\u0e21\u0e0a\u0e32\u0e22 \u0e23\u0e31\u0e01\u0e29\u0e4c\u0e14\u0e35")
                .mechanicName("\u0e21\u0e32\u0e19\u0e35\u0e15\u0e22 \u0e02\u0e22\u0e31\u0e19\u0e41\u0e23\u0e07")
                .licensePlate("\u0e01\u0e0a 1234 \u0e01\u0e23\u0e38\u0e07\u0e40\u0e17\u0e1e")
                .items(Collections.singletonList(
                        PartPickingReportDTO.PickingItemDTO.builder()
                                .partCode("OIL-001")
                                .partName("\u0e19\u0e49\u0e33\u0e21\u0e31\u0e19\u0e40\u0e04\u0e23\u0e37\u0e48\u0e2d\u0e07\u0e41\u0e25\u0e30\u0e01\u0e23\u0e2d\u0e07\u0e01\u0e23\u0e30\u0e40\u0e1a\u0e37\u0e49\u0e2d\u0e07")
                                .requestedQty(4)
                                .pickedQty(4)
                                .location("A-01-02")
                                .build()
                ))
                .build();
    }

    @Override
    public ReceiptReportDTO getReceiptData(UUID receiptId) {
        return ReceiptReportDTO.builder()
                .companyName("ICMON Auto Repair")
                .receiptNo("RCP-" + receiptId.toString().substring(0, 8).toUpperCase())
                .receiptDate(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .customerName("\u0e19\u0e32\u0e22 \u0e2a\u0e21\u0e0a\u0e32\u0e22 \u0e23\u0e31\u0e01\u0e29\u0e4c\u0e14\u0e35")
                .paymentMethod("\u0e40\u0e07\u0e34\u0e19\u0e2a\u0e14")
                .amount(new BigDecimal("10700.00"))
                .amountInWords("\u0e2b\u0e19\u0e36\u0e48\u0e07\u0e2b\u0e21\u0e37\u0e48\u0e19\u0e40\u0e08\u0e47\u0e14\u0e23\u0e49\u0e2d\u0e22\u0e1a\u0e32\u0e17\u0e16\u0e49\u0e27\u0e19")
                .cashierName("\u0e19\u0e32\u0e07 \u0e27\u0e31\u0e19\u0e14\u0e35 \u0e43\u0e08\u0e14\u0e35")
                .items(Collections.singletonList(
                        ReceiptReportDTO.ReceiptItemDTO.builder()
                                .description("\u0e1a\u0e23\u0e34\u0e01\u0e32\u0e23\u0e40\u0e1b\u0e25\u0e35\u0e48\u0e22\u0e19\u0e19\u0e49\u0e33\u0e21\u0e31\u0e19\u0e40\u0e04\u0e23\u0e37\u0e48\u0e2d\u0e07")
                                .total(new BigDecimal("10700.00"))
                                .build()
                ))
                .build();
    }

    @Override
    public CreditNoteReportDTO getCreditNoteData(UUID creditNoteId) {
        return CreditNoteReportDTO.builder()
                .companyName("ICMON Auto Repair")
                .creditNoteNo("CN-" + creditNoteId.toString().substring(0, 8).toUpperCase())
                .creditNoteDate(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .referenceInvoiceNo("INV-2026-0001")
                .customerName("\u0e1a\u0e23\u0e34\u0e29\u0e31\u0e17 \u0e15\u0e31\u0e27\u0e2d\u0e22\u0e48\u0e32\u0e07 \u0e08\u0e33\u0e01\u0e31\u0e14")
                .reason("\u0e2a\u0e34\u0e19\u0e04\u0e49\u0e32\u0e0a\u0e33\u0e23\u0e38\u0e14 \u0e04\u0e37\u0e19\u0e2d\u0e30\u0e44\u0e2b\u0e25\u0e48")
                .totalAmount(new BigDecimal("5000.00"))
                .approvedBy("\u0e1c\u0e39\u0e49\u0e08\u0e31\u0e14\u0e01\u0e32\u0e23")
                .items(Collections.singletonList(
                        CreditNoteReportDTO.NoteItemDTO.builder()
                                .lineNo(1)
                                .description("\u0e04\u0e37\u0e19\u0e2d\u0e30\u0e44\u0e2b\u0e25\u0e48\u0e2d\u0e32\u0e44\u0e2b\u0e25\u0e48\u0e40\u0e2a\u0e35\u0e22\u0e2b\u0e32\u0e22 4 \u0e0a\u0e34\u0e49\u0e19")
                                .amount(new BigDecimal("5000.00"))
                                .build()
                ))
                .build();
    }

    @Override
    public DebitNoteReportDTO getDebitNoteData(UUID debitNoteId) {
        return DebitNoteReportDTO.builder()
                .companyName("ICMON Auto Repair")
                .debitNoteNo("DN-" + debitNoteId.toString().substring(0, 8).toUpperCase())
                .debitNoteDate(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .referenceInvoiceNo("INV-2026-0001")
                .customerName("\u0e1a\u0e23\u0e34\u0e29\u0e31\u0e17 \u0e15\u0e31\u0e27\u0e2d\u0e22\u0e48\u0e32\u0e07 \u0e08\u0e33\u0e01\u0e31\u0e14")
                .reason("\u0e1b\u0e23\u0e31\u0e1a\u0e1b\u0e23\u0e38\u0e07\u0e23\u0e32\u0e04\u0e32\u0e2d\u0e30\u0e44\u0e2b\u0e25\u0e48\u0e40\u0e1e\u0e34\u0e48\u0e21")
                .totalAmount(new BigDecimal("2000.00"))
                .approvedBy("\u0e1c\u0e39\u0e49\u0e08\u0e31\u0e14\u0e01\u0e32\u0e23")
                .items(Collections.singletonList(
                        DebitNoteReportDTO.DebitNoteItemDTO.builder()
                                .lineNo(1)
                                .description("\u0e1b\u0e23\u0e31\u0e1a\u0e23\u0e32\u0e04\u0e32\u0e04\u0e48\u0e32\u0e41\u0e23\u0e07\u0e40\u0e1e\u0e34\u0e48\u0e21")
                                .amount(new BigDecimal("2000.00"))
                                .build()
                ))
                .build();
    }

    @Override
    public DeliverySheetReportDTO getDeliverySheetData(UUID deliveryId) {
        return DeliverySheetReportDTO.builder()
                .companyName("ICMON Auto Repair")
                .deliveryNo("DLV-" + deliveryId.toString().substring(0, 8).toUpperCase())
                .deliveryDate(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .customerName("\u0e1a\u0e23\u0e34\u0e29\u0e31\u0e17 \u0e15\u0e31\u0e27\u0e2d\u0e22\u0e48\u0e32\u0e07 \u0e08\u0e33\u0e01\u0e31\u0e14")
                .customerAddress("456 \u0e16\u0e19\u0e19\u0e2a\u0e38\u0e02\u0e38\u0e21\u0e27\u0e34\u0e17 \u0e01\u0e23\u0e38\u0e07\u0e40\u0e17\u0e1e")
                .customerPhone("08-1234-5678")
                .referenceNo("SO-2026-0001")
                .deliveryPerson("\u0e1e\u0e19\u0e31\u0e01\u0e02\u0e31\u0e1a\u0e2a\u0e48\u0e07")
                .items(Collections.singletonList(
                        DeliverySheetReportDTO.DeliveryItemDTO.builder()
                                .lineNo(1)
                                .description("\u0e22\u0e32\u0e07\u0e23\u0e16\u0e22\u0e19\u0e15\u0e4c Bridgestone 205/55R16 \u0e08\u0e33\u0e19\u0e27\u0e19 4 \u0e40\u0e2a\u0e49\u0e19")
                                .quantity(4)
                                .unit("\u0e40\u0e2a\u0e49\u0e19")
                                .note("")
                                .build()
                ))
                .build();
    }

    @Override
    public JobCardReportDTO getJobCardData(UUID jobId) {
        return JobCardReportDTO.builder()
                .companyName("ICMON Auto Repair")
                .jobNo("JB-" + jobId.toString().substring(0, 8).toUpperCase())
                .jobDate(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .customerName("\u0e19\u0e32\u0e22 \u0e2a\u0e21\u0e0a\u0e32\u0e22 \u0e23\u0e31\u0e01\u0e29\u0e4c\u0e14\u0e35")
                .customerPhone("08-1234-5678")
                .licensePlate("\u0e01\u0e0a 1234 \u0e01\u0e23\u0e38\u0e07\u0e40\u0e17\u0e1e")
                .carModel("Toyota Camry 2020")
                .carColor("\u0e2a\u0e35\u0e14\u0e33")
                .mileage("85,432 \u0e01\u0e21.")
                .mechanicName("\u0e21\u0e32\u0e19\u0e35\u0e15\u0e22 \u0e02\u0e22\u0e31\u0e19\u0e41\u0e23\u0e07")
                .serviceAdvisor("\u0e19\u0e32\u0e07 \u0e27\u0e31\u0e19\u0e14\u0e35 \u0e43\u0e08\u0e14\u0e35")
                .complaint("\u0e40\u0e04\u0e23\u0e37\u0e48\u0e2d\u0e07\u0e40\u0e2a\u0e35\u0e22\u0e07\u0e1c\u0e34\u0e14\u0e1b\u0e01\u0e15\u0e34 \u0e41\u0e25\u0e30\u0e19\u0e49\u0e33\u0e21\u0e31\u0e19\u0e40\u0e04\u0e23\u0e37\u0e48\u0e2d\u0e07\u0e23\u0e31\u0e48\u0e27")
                .totalParts(new BigDecimal("8500.00"))
                .totalLabor(new BigDecimal("1500.00"))
                .grandTotal(new BigDecimal("10000.00"))
                .items(java.util.Arrays.asList(
                        JobCardReportDTO.JobCardItemDTO.builder()
                                .type("\u0e2d\u0e30\u0e44\u0e2b\u0e25\u0e48")
                                .description("\u0e19\u0e49\u0e33\u0e21\u0e31\u0e19\u0e40\u0e04\u0e23\u0e37\u0e48\u0e2d\u0e07\u0e41\u0e25\u0e30\u0e01\u0e23\u0e2d\u0e07\u0e01\u0e23\u0e30\u0e40\u0e1a\u0e37\u0e49\u0e2d\u0e07")
                                .quantity(4)
                                .unitPrice(new BigDecimal("2125.00"))
                                .totalPrice(new BigDecimal("8500.00"))
                                .build(),
                        JobCardReportDTO.JobCardItemDTO.builder()
                                .type("\u0e04\u0e48\u0e32\u0e41\u0e23\u0e07")
                                .description("\u0e04\u0e48\u0e32\u0e41\u0e23\u0e07\u0e40\u0e1b\u0e25\u0e35\u0e48\u0e22\u0e19\u0e19\u0e49\u0e33\u0e21\u0e31\u0e19\u0e40\u0e04\u0e23\u0e37\u0e48\u0e2d\u0e07\u0e41\u0e25\u0e30\u0e01\u0e23\u0e2d\u0e07\u0e01\u0e23\u0e30\u0e40\u0e1a\u0e37\u0e49\u0e2d\u0e07")
                                .quantity(1)
                                .unitPrice(new BigDecimal("1500.00"))
                                .totalPrice(new BigDecimal("1500.00"))
                                .build()
                ))
                .build();
    }

    @Override
    public DailySalesReportDTO getDailySalesData(String date) {
        return DailySalesReportDTO.builder()
                .companyName("ICMON Auto Repair")
                .reportDate(date)
                .totalRevenue(new BigDecimal("107000.00"))
                .totalInvoices(10)
                .totalCustomers(8)
                .avgPerInvoice(new BigDecimal("10700.00"))
                .items(Collections.singletonList(
                        DailySalesReportDTO.SalesItemDTO.builder()
                                .customerName("\u0e1a\u0e23\u0e34\u0e29\u0e31\u0e17 \u0e15\u0e31\u0e27\u0e2d\u0e22\u0e48\u0e32\u0e07 \u0e08\u0e33\u0e01\u0e31\u0e14")
                                .invoiceNo("INV-2026-0001")
                                .amount(new BigDecimal("10700.00"))
                                .build()
                ))
                .build();
    }

    @Override
    public InventorySummaryReportDTO getInventorySummaryData() {
        return InventorySummaryReportDTO.builder()
                .companyName("ICMON Auto Repair")
                .reportDate(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .totalItems(150)
                .totalValue(new BigDecimal("2500000.00"))
                .lowStockCount(5)
                .items(Collections.emptyList())
                .build();
    }

    @Override
    public CustomerListReportDTO getCustomerListData() {
        return CustomerListReportDTO.builder()
                .companyName("ICMON Auto Repair")
                .reportDate(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .totalCustomers(200)
                .items(Collections.emptyList())
                .build();
    }
}
