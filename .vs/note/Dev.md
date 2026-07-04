# เอกสารออกแบบระบบครบวงจร (ระบบ User, สินค้า, คลังสินค้า, ขนส่ง, รายงาน, ประวัติการใช้งาน)
**พร้อม Template Code, Data Flow, Workflow, และ Template รายงาน**

> **Tech Stack:** Java 17+ / Spring Boot 3.2 | JPA (Hibernate) | PostgreSQL | Redis (Cache) | Kafka (Event Bus) | ELK (Elasticsearch, Logstash, Kibana) | Grafana | n8n | Docker Compose | Jenkins (CI/CD) | AWS EC2/S3 | Robot Framework

---




### โมดูลหลักของระบบ

```

├── 🔑 Authentication & Permission     ← เข้าสู่ระบบ/จัดการสิทธิ์
├── 🚗 Job Card Management             ← ใบงานซ่อมรถ
├── 👥 Customer Management             ← ข้อมูลลูกค้า
├── 📋 Quotation                       ← ใบเสนอราคา
├── 🛒 Purchase Order                  ← ใบสั่งซื้อ
├── 📦 Inventory Management            ← คลังสินค้า/อะไหล่
├── 💰 Payment Management              ← การชำระเงิน
├── 📅 Booking Management              ← การนัดหมาย
├── 👨‍💼 Staff Management                ← จัดการพนักงาน
├── 🏢 Company & Supplier              ← บริษัท/Supplier
├── 📊 Dashboard & Reports             ← รายงานและ Dashboard
├── 📧 Email Service                   ← ส่ง Email อัตโนมัติ
├── 📄 Document Management             ← จัดการเอกสาร
├── 🔍 OCR (Image to Text)             ← อ่านข้อความจากรูปภาพ
├── ⏱️  Batch Jobs (6 jobs)             ← งาน Scheduled อัตโนมัติ
├── 🌏 Multi-Language (18 ภาษา)        ← รองรับหลายภาษา
│
└── Web Order System (WOS)
    ├── 📚 Catalogue Management         ← สินค้าใน Catalogue
    ├── 🛒 Shopping Cart               ← ตะกร้าสินค้า
    └── 💵 Sales Price                  ← ราคาขาย
```
# Database :PostgreSQL 
เพิ่ม ระบบ dashboard center  
เพิ่ม ระบบ  GPS Tacking ,
เพิ่ม ระบบ  Iot ,mqtt,influxdb,device history log,device access control management system,autuo report
เพิ่ม ระบบ  Kafka Queue process
เพิ่ม Batch Jobs Schedule
| Job | Cron Expression | เวลาทำงาน |
|-----|----------------|----------|
| batch001 | `0 30 6 ? * *` | 06:30 น. ทุกวัน |
| batch002 | `0 45 6 ? * *` | 06:45 น. ทุกวัน |
| batch003 | `0 30 6 ? * *` | 06:30 น. ทุกวัน |
| batch004 | `0 0 3 ? * *` | 03:00 น. ทุกวัน |
| batch005 | `0 0/30 * * * ?` | ทุก 30 นาที |
| batch006 | `0 30 6 ? * *` | 06:30 น. ทุกวัน |


## ส่วนที่ 1: เอกสารระบบ (System Document)

### [TH] 1.1 สถาปัตยกรรมระบบโดยรวม
ระบบถูกออกแบบด้วย **Layered Architecture** (Controller → Service → Repository) ร่วมกับ **Event-Driven** ผ่าน Kafka เพื่อแยกการทำงานหนัก (Heavy Processing) ออกจาก REST API หลัก
*   **Controller Layer:** รับ HTTP Request, ตรวจสอบ JWT, Validate DTO
*   **Service Layer:** จัดการ Business Logic, จัดการ Transaction, เรียกใช้ Cache
*   **Repository Layer:** ใช้ Spring Data JPA เชื่อมต่อ PostgreSQL
*   **Event Publisher:** เมื่อเกิดการเปลี่ยนแปลงสถานะ (เช่น ขนส่งสำเร็จ) จะส่ง Event ไปยัง Kafka
*   **Event Consumer:** ระบบย่อย (เช่น ระบบรายงาน, ระบบแจ้งเตือน) จะดึง Event ไปอัปเดต Elasticsearch และแจ้งเตือนไลน์/อีเมล
*   **Monitoring:** Grafana ดึง Metrics จาก Actuator / Micrometer, ELK จัดการ Logs

### [EN] 1.1 Overall System Architecture
The system utilizes **Layered Architecture** (Controller → Service → Repository) combined with **Event-Driven** via Kafka to decouple heavy processing from the main REST API.
*   **Controller Layer:** Handles HTTP Requests, validates JWT, validates DTOs.
*   **Service Layer:** Manages Business Logic, handles Transactions, utilizes Redis Cache.
*   **Repository Layer:** Uses Spring Data JPA to connect to PostgreSQL.
*   **Event Publisher:** Publishes events to Kafka upon state changes (e.g., Delivery Completed).
*   **Event Consumer:** Subsystems (Reporting, Notification) consume events to update Elasticsearch and send LINE/Email alerts.
*   **Monitoring:** Grafana pulls metrics from Actuator/Micrometer; ELK handles centralized logging.

---

## Table of Contents

1. [Complete Project Folder Structure](#1-complete-project-folder-structure)
2. [Quotation Module](#2-quotation-module)
3. [Purchase Order Module](#3-purchase-order-module)
4. [Invoice Module](#4-invoice-module)
5. [Credit/Debit Note Module](#5-creditdebit-note-module)
6. [Delivery Sheet Module](#6-delivery-sheet-module)
7. [Job Order Module](#7-job-order-module)
8. [Part Picking Module](#8-part-picking-module)
9. [Supporting Modules](#9-supporting-modules)

**Template:** `static/template/jrxml/quatation.jrxml`

### 2.2 Module Purpose

The Quotation module manages **Quotation documents** for vehicle repair services. After diagnosing the issue, a quotation is generated to propose part and service costs to the customer for approval before repair work begins.

### 2.3 Workflow

```
Customer brings vehicle for repair
       │
       ▼
  Create Job Card (TJob)
       │
       ▼
  Diagnose problem / Add repair items
       │
       ▼
  Create Quotation (TQuotation)
  ├── Add part items (TQuotationPart)
  └── Add service items (TQuotationService)
       │
       ▼
  Calculate totals (subtotal, tax, total)
  ├── Convert amount to text (ConvertBath/ConvertDollar)
  └── Round values (Round)
       │
       ▼
  Approve/Reject Quotation
       │
       ├── Approved → Create Purchase Order
       │              → Pick Parts (Part Picking)
       │              → Start Repair
       │
       └── Rejected → Close Job / Create new Quotation
```

### 2.4 Module Dependencies

| Connected To | Relationship |
|-------------|-------------|
| **Job Card** | Quotation is created from Job Card (1:1, but a Job can have multiple Quotations) |
| **Purchase Order** | After Quotation approval → create PO to order parts |
| **Part Picking** | Uses Quotation data to pick parts |
| **Invoice** | Quotation totals serve as base for Invoicing |
| **Inventory** | Check stock availability for quoted parts |

### 2.5 Main API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | `/quotation/list` | List all quotations |
| POST | `/quotation/create` | Create new quotation |
| PUT | `/quotation/update` | Update quotation |
| GET | `/quotation/{id}` | View quotation details |
| GET | `/quotation/report/{id}` | Generate Quotation PDF |
| POST | `/quotation/part/create` | Add part items |
| GET | `/quotation/list/{jobId}` | List quotations for a Job |

--- 


**Template:** `static/template/jrxml/purchaseOrder.jrxml`

### 3.2 Module Purpose

The Purchase Order module manages **Purchase Orders (PO)** sent to suppliers to order parts needed for repairs. It serves as a formal business document in the procurement process.

### 3.3 Workflow

```
Quotation approved
       │
       ▼
  Create Purchase Order (TPurchaseOrderHeader)
  └── Add line items (TPurchaseOrderDetail)
       │
       ▼
  Check stock (InventoryService)
  ├── Available → Pick from warehouse
  └── Not available → Order from Supplier (PO)
       │
       ▼
  Send PO to Supplier
  ├── Attach PDF (purchaseOrder.jrxml)
  └── Send Email (MailService)
       │
       ▼
  Supplier confirms PO
       │
       ▼
  Receive goods
  ├── Update Stock (TInventory)
  └── Update PO status
```

### 3.4 Module Dependencies

| Connected To | Relationship |
|-------------|-------------|
| **Quotation** | PO is created from approved Quotation (1:many) |
| **Inventory** | Receive goods into stock after supplier delivery |
| **Supplier** | Select supplier from Master Data (MSupplier) |
| **Part Master** | Part data from MPartMaster |
| **Email** | Send PO to supplier via Email |
| **Part Picking** | PO data used for part picking |

### 3.5 Main API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | `/po/list` | List purchase orders |
| POST | `/po/create` | Create new purchase order |
| PUT | `/po/update/{id}` | Update purchase order |
| GET | `/po/{id}` | View PO details |
| GET | `/po/report/{id}` | Generate PO PDF |
| POST | `/po/email/{id}` | Send PO via Email |
| GET | `/po/suggestion/{jobId}` | Suggest PO from Job |

---

## 4. Invoice Module

### 4.1 Module Folder Structure

```
com.denso.shop
├── controllers/
│   ├── InvoiceTabController.java         ← Invoice list view
│   ├── InvoiceAdjustmentController.java  ← Main Invoice + PDF
│   ├── InvoiceAdjustmentPartController.java   ← Manage part items in Invoice
│   ├── InvoiceAdjustmentServiceController.java ← Manage service items
│   └── ReceiptController.java            ← Receipt
│
├── domain/
│   ├── TInvoiceAdjustment.java           ← Entity Invoice Header
│   ├── TInvoiceAdjustmentPart.java       ← Entity Part Detail
│   ├── TInvoiceAdjustmentService.java    ← Entity Service Detail
│   ├── TReceivedAmount.java              ← Entity for received payments
│   ├── VPreview.java                     ← View for preview
│   ├── VPreviewReceipt.java              ← View for receipt
│   ├── VHeaderReport.java                ← View for report Header
│   ├── VCreditDebitDetail.java           ← View for Credit/Debit Note
│   │
│   └── dto/
│       ├── InvoiceAdjustmentDTO.java       ← Main DTO
│       ├── InvoiceAdjustmentPartDTO.java   ← DTO for parts
│       ├── InvoiceAdjustmentServiceDTO.java ← DTO for services
│       ├── InvoiceTabListDTO.java          ← DTO for tab list
│       └── SummaryInvoiceDTO.java          ← DTO for invoice summary
│
├── repositories/
│   ├── InvoiceRepository.java
│   ├── InvoiceAdjustmentRepository.java
│   ├── InvoiceAdjustmentPartRepository.java
│   ├── InvoiceAdjustmentServiceRepository.java
│   ├── custom/
│   │   └── InvoiceRepositoryCustom.java
│   └── impl/
│       ├── InvoiceRepositoryImpl.java
│       └── InvoiceAdjustmentRepositoryImpl.java
│
├── services/
│   ├── interfaces/
│   │   ├── InvoiceTabService.java
│   │   ├── InvoiceAdjustmentService.java
│   │   ├── InvoiceAdjustmentPartService.java
│   │   ├── InvoiceAdjustmentServiceService.java
│   │   └── ReceiptService.java
│   ├── InvoiceTabServiceImpl.java
│   ├── InvoiceAdjustmentServiceImpl.java
│   ├── InvoiceAdjustmentPartServiceImpl.java
│   └── InvoiceAdjustmentServiceServiceImpl.java
│
└── utilities/
    └── (shared with ConvertBath, ConvertDollar, Round)
```

**Templates:**
- `static/template/jrxml/icmon_Invoice.jrxml` — Invoice
- `static/template/jrxml/taxInvoice.jrxml` — Tax Invoice
- `static/template/jrxml/summary_invoice.jrxml` — Invoice Summary
- `static/template/jrxml/receipt.jrxml` — Receipt

### 4.2 Module Purpose

The Invoice module manages **Invoice documents** for billing customers after repair work is complete. It includes part charges, service charges, tax calculations, and receipt generation.

### 4.3 Workflow

```
Repair completed / Quotation approved
       │
       ▼
  Create Invoice (TInvoiceAdjustment)
  ├── Add part items (TInvoiceAdjustmentPart)
  ├── Add service items (TInvoiceAdjustmentService)
  └── Calculate totals (subtotal, tax, total)
       │
       ▼
  Verify accuracy
  ├── Currency conversion (ConvertExchange)
  └── Set Remark (DocumentRemark)
       │
       ▼
  Issue Invoice
  ├── Generate PDF (icmon_Invoice.jrxml / taxInvoice.jrxml)
  └── Send to customer
       │
       ▼
  Customer pays
  ├── Record payment (TReceivedAmount)
  └── Issue Receipt (receipt.jrxml)
```

### 4.4 Module Dependencies

| Connected To | Relationship |
|-------------|-------------|
| **Job Card** | Invoice is created from a completed Job |
| **Quotation** | Price data from Quotation is used in Invoice |
| **Payment** | Record payment (TReceivedAmount) |
| **Credit/Debit Note** | Adjust issued Invoices |
| **Document** | Configure Remark from MDocumentRemark |

### 4.5 Main API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | `/invoice/tab/list` | List invoices |
| POST | `/invoice/create` | Create new invoice |
| PUT | `/invoice/update/{id}` | Update invoice |
| GET | `/invoice/report/{id}` | Generate Invoice PDF |
| GET | `/invoice/summary/{jobId}` | Invoice summary |
| POST | `/invoice/part/create` | Add part items |
| POST | `/invoice/service/create` | Add service items |
| GET | `/invoice/receipt/{id}` | Generate Receipt PDF |
| GET | `/data/convert-amount` | Currency conversion |

---

## 5. Credit/Debit Note Module

### 5.1 Module Folder Structure

```
com.denso.shop
├── controllers/
│   └── InvoiceAdjustmentController.java  ← (shared with Invoice module)
│       ├── createCreditNote()
│       ├── createDebitNote()
│       ├── getCreditNoteReport()
│       └── getDebitNoteReport()
│
├── domain/
│   ├── TInvoiceAdjustment.java           ← (shared with Invoice - adds type field)
│   ├── TInvoiceAdjustmentPart.java       ← Parts to credit/debit
│   ├── TInvoiceAdjustmentService.java    ← Services to credit/debit
│   ├── VCreditDebitDetail.java           ← View for Credit/Debit report
│   │
│   └── dto/
│       ├── InvoiceAdjustmentDTO.java     ← Shared DTO (type=CREDIT/DEBIT)
│       ├── InvoiceAdjustmentPartDTO.java
│       └── InvoiceAdjustmentServiceDTO.java
│
├── repositories/
│   └── (shared with Invoice repositories)
│
├── services/
│   └── (shared with Invoice services)
│
└── utilities/
    └── (shared with ConvertBath, ConvertDollar, Round)
```

**Templates:**
- `static/template/jrxml/creditNote.jrxml` — Credit Note
- `static/template/jrxml/debitNote.jrxml` — Debit Note

### 5.2 Module Purpose

The Credit/Debit Note module manages **Credit Notes** (invoice reduction) and **Debit Notes** (invoice increase) used to adjust issued invoices:
- **Credit Note:** Reduce the amount payable (refund/discount)
- **Debit Note:** Increase the amount payable (additional charges)

### 5.3 Workflow

```
Need to adjust issued Invoice
       │
       ├── Credit Note
       │   │
       │   ▼
       │   Create TInvoiceAdjustment (type = CREDIT)
       │   ├── Add items to reduce (TInvoiceAdjustmentPart/Service)
       │   └── Calculate reduction amount
       │       │
       │       ▼
       │   Generate PDF (creditNote.jrxml)
       │   └── Send to customer
       │
       └── Debit Note
           │
           ▼
           Create TInvoiceAdjustment (type = DEBIT)
           ├── Add items to charge (TInvoiceAdjustmentPart/Service)
           └── Calculate additional amount
               │
               ▼
           Generate PDF (debitNote.jrxml)
           └── Send to customer
```

### 5.4 Module Dependencies

| Connected To | Relationship |
|-------------|-------------|
| **Invoice** | Credit/Debit Note adjusts an existing Invoice |
| **Job Card** | References the same Job ID as the original Invoice |

### 5.5 Main API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| POST | `/invoice/credit-note/create` | Create Credit Note |
| POST | `/invoice/debit-note/create` | Create Debit Note |
| GET | `/invoice/credit-note/report/{id}` | Generate Credit Note PDF |
| GET | `/invoice/debit-note/report/{id}` | Generate Debit Note PDF |
| GET | `/invoice/credit-debit/detail/{id}` | View Credit/Debit Note details |

---

## 6. Delivery Sheet Module

### 6.1 Module Folder Structure

```
com.denso.shop
├── controllers/
│   └── DeliveryController.java           ← PDF generation for Delivery Sheet
│
├── domain/
│   ├── VHeaderReport.java                ← View Header report
│   ├── VPreviewJobCardHeaderReport.java  ← View Job Card Header for report
│   ├── VPreviewJobCardHeaderPage.java    ← View Header Page
│   └── VPreviewJobCardDetialsParts.java  ← View part details
│
├── repositories/
│   └── (shared with Job repositories)
│
├── services/
│   ├── interfaces/
│   │   ├── JobOrderService.java          ← Fetch Job data
│   │   ├── PreviewPdfService.java        ← Fetch PDF data
│   │   └── PdfService.java               ← Generate PDF
│   ├── JobOrderServiceImpl.java
│   └── PreviewPdfServiceImpl.java
│
└── utilities/
    └── (shared)
```

**Template:** `static/template/jrxml/deliverySheet.jrxml`

### 6.2 Module Purpose

The Delivery Sheet module manages **Delivery Sheets** — documents used when delivering parts or goods to a customer or another department, typically accompanying a Job Card.

### 6.3 Workflow

```
Job Card requires delivery
       │
       ▼
  Fetch Job data (JobOrderService)
  ├── VHeaderReport (Header information)
  ├── VPreviewJobCardHeaderReport (car/customer details)
  └── VPreviewJobCardDetialsParts (part items)
       │
       ▼
  Generate PDF (deliverySheet.jrxml)
  ├── Header (date, document no., customer name)
  ├── Part list (Part No, Description, Qty)
  └── Sender/Receiver signatures
       │
       ▼
  Deliver to customer/department
```

### 6.4 Module Dependencies

| Connected To | Relationship |
|-------------|-------------|
| **Job Card** | Delivery Sheet references Job Card |
| **Part Picking** | Part data from picking request |
| **Inventory** | Check stock before delivery |

### 6.5 Main API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | `/delivery/pdf` | Generate Delivery Sheet PDF |
| GET | `/delivery/preview/{jobId}` | Preview Delivery Sheet |

---

## 7. Job Order Module

### 7.1 Module Folder Structure

```
com.denso.shop
├── controllers/
│   ├── JobController.java                ← Main Job Card CRUD
│   ├── JobOrderController.java           ← Job Order + PDF + Status management
│   ├── JobStatusController.java          ← Job status management
│   ├── JobPartSaleController.java        ← Part sales from Job
│   └── JobServiceItemController.java (if exists) ← Service items
│
├── domain/
│   ├── TJob.java                         ← Main entity (Job Card)
│   ├── TJobService.java                  ← Service items entity
│   ├── TJobServiceItem.java              ← Sub-service items entity
│   ├── TJobServiceCarSymptom.java        ← Car symptom entity
│   ├── TJobPartSales.java                ← Part sales entity
│   ├── TJobDiagTroubleCode.java          ← Diagnostic trouble code entity
│   ├── TJobSubMechanic.java              ← Sub-mechanic entity
│   ├── JobStatusConstant.java            ← Job status constants
│   ├── VJobCardDetail.java               ← View Job Card details
│   ├── VJobStatusListService.java        ← View services by status
│   ├── VPreviewJobCardHeaderPage.java    ← View Header Page for PDF
│   ├── VPreviewJobCardHeaderReport.java  ← View Header Report
│   ├── VPreviewJobCardDetialsParts.java  ← View part items
│   │
│   └── dto/
│       ├── JobDTO.java                   ← Main DTO
│       ├── JobOrderQuatationDataDTO.java ← DTO for Quotation data in Job
│       ├── JobServiceItemQuotationDTO.java ← DTO for service items
│       └── JobOrderResponse.java         ← Response class
│
├── repositories/
│   ├── JobRepository.java
│   ├── JobServiceRepository.java
│   ├── JobPartSalesRepository.java
│   ├── custom/
│   │   ├── JobRepositoryCustom.java
│   │   └── JobServiceRepositoryCustom.java
│   └── impl/
│       ├── JobRepositoryImpl.java
│       └── JobServiceRepositoryImpl.java
│
├── services/
│   ├── interfaces/
│   │   ├── JobService.java               ← Main service interface
│   │   ├── JobOrderService.java          ← Job Order service interface
│   │   ├── JobStatusService.java         ← Status service interface
│   │   └── JobCarSymptomService.java     ← Car symptom service interface
│   ├── JobServiceImpl.java
│   ├── JobOrderServiceImpl.java
│   ├── JobStatusServiceImpl.java
│   └── JobCarSymptomServiceImpl.java
│
└── utilities/
    └── (shared)
```

**Templates:**
- `static/template/jrxml/jobOrder.jrxml` — Job Order

### 7.2 Module Purpose

The Job Order/Card module is the **core module** of the entire system. It manages **Job Cards** from customer arrival to job closure and serves as the central hub connecting all other modules.

### 7.3 Job Card Statuses (JobStatusConstant)

```
OPEN → IN_PROGRESS → QUOTATION_PENDING → QUOTATION_APPROVED →
PART_PICKING → REPAIR_IN_PROGRESS → REPAIR_DONE →
INVOICE_PENDING → INVOICE_CREATED → PAYMENT_RECEIVED → CLOSED

Other statuses:
- CANCELLED
- ON_HOLD
- WAITING_PARTS
```

### 7.4 End-to-End Workflow

```
Customer arrives (Walk-in / Booking)
       │
       ▼
  Create Job Card (TJob)
  ├── Select Customer (MCustomer)
  ├── Select Car (MCar)
  ├── Select Service Items (MService)
  └── Specify Car Symptoms (TJobServiceCarSymptom)
       │
       ▼
  Mechanic proceeds (IN_PROGRESS)
  ├── Add repair items (TJobServiceItem)
  ├── Add required parts
  └── Diagnose issues (TJobDiagTroubleCode)
       │
       ▼
  Create Quotation (→ Quotation module)
       │
       ▼
  Customer approves Quotation
       │
       ▼
  Pick parts (Part Picking) / Order (Purchase Order)
       │
       ▼
  Perform repair work
  ├── Update job status
  └── Log time (MechanicLeave, PitClosed)
       │
       ▼
  Work completed (REPAIR_DONE)
       │
       ▼
  Issue Invoice (→ Invoice module)
       │
       ▼
  Customer pays (Payment)
       │
       ▼
  Close Job (CLOSED)
```

### 7.5 Module Dependencies

| Connected To | Relationship |
|-------------|-------------|
| **Quotation** | Created from Job (1:many) |
| **Purchase Order** | Order parts for Job |
| **Invoice** | Invoice from completed Job |
| **Part Picking** | Pick parts for Job |
| **Delivery Sheet** | Deliver parts from Job |
| **Inventory** | Check stock for Job |
| **Booking** | Create Job from booking |
| **Customer/Car** | Customer and vehicle data |
| **Staff/Mechanic** | Assigned mechanics |

### 7.6 Main API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | `/job/list` | List all Job Cards |
| POST | `/job/create` | Create new Job Card |
| PUT | `/job/update/{id}` | Update Job Card |
| GET | `/job/{id}` | View Job Card details |
| PUT | `/job/status/{id}` | Change Job status |
| GET | `/job/history` | View vehicle repair history |
| GET | `/job/order/report/{id}` | Generate Job Order PDF |
| GET | `/job/customer/{jobId}` | Get customer data from Job |
| POST | `/job/part-sale` | Sell parts from Job |

---

## 8. Part Picking Module

### 8.1 Module Folder Structure

```
com.denso.shop
├── controllers/
│   ├── PartPickingRequestController.java ← PDF generation for picking
│   ├── PartPreparationController.java    ← Picking preparation
│   ├── PartMasterController.java         ← CRUD Part Master data
│   ├── PartSearchConstroller.java        ← Part search
│   └── ItemMasterController.java         ← Item Master management
│
├── domain/
│   ├── MPartMaster.java                  ← Part Master Data
│   ├── MPartMasterOeNumber.java          ← Part OE Number
│   ├── MPartMasterTa.java                ← Part TA Data
│   ├── MPartMasterCarApplication.java    ← Part-to-car compatibility
│   ├── MStockLocation.java               ← Stock location
│   ├── TInventory.java                   ← Stock transaction
│   ├── TInventoryAdjustmentHeader.java   ← Stock adjustment Header
│   ├── TInventoryAdjustmentDetail.java   ← Stock adjustment Detail
│   ├── VPartPickingRequestHeader.java    ← View Picking Header
│   ├── VPartPickingRequestDetail.java    ← View Picking Detail
│   │
│   └── dto/
│       ├── PartMasterDTO.java
│       ├── PartMasterCarApplicationDTO.java
│       ├── InventoryDTO.java
│       ├── StockAdjustmentDTO.java
│       └── ...
│
├── repositories/
│   ├── PartMasterRepository.java
│   ├── InventoryRepository.java
│   ├── custom/
│   │   ├── PartMasterRepositoryCustom.java
│   │   └── InventoryRepositoryCustom.java
│   └── impl/
│       ├── PartMasterRepositoryImpl.java
│       └── InventoryRepositoryImpl.java
│
├── services/
│   ├── interfaces/
│   │   ├── PartPickingRequestService.java
│   │   ├── PreparationPartService.java
│   │   ├── PartMasterService.java
│   │   ├── PartSearchService.java
│   │   ├── InventoryService.java
│   │   ├── StockAdjustmentService.java
│   │   ├── StockLocationService.java
│   │   ├── StockSummaryService.java
│   │   └── StockTakeService.java
│   ├── PartPickingRequestServiceImpl.java
│   ├── PreparationPartServiceImpl.java
│   ├── PartMasterServiceImpl.java
│   ├── PartSearchServiceImpl.java
│   ├── InventoryServiceImpl.java
│   ├── StockAdjustmentServiceImpl.java
│   ├── StockLocationServiceImpl.java
│   ├── StockSummaryServiceImpl.java
│   └── StockTakeServiceImpl.java
│
└── utilities/
    └── (shared)
```

**Template:** `static/template/jrxml/partPicking.jrxml`

### 8.2 Module Purpose

The Part Picking module manages **Part Picking Requests** — documents used to withdraw parts from inventory for use in vehicle repairs. It serves as the bridge between Job Cards and the Inventory system.

### 8.3 Workflow

```
Job Card requires parts
       │
       ▼
  Create Part Picking Request
  ├── Select parts from Quotation
  ├── Specify required quantity
  └── Reference Job ID
       │
       ▼
  Check Stock (InventoryService)
  ├── Sufficient stock → Picking allowed
  └── Insufficient → Order more (Purchase Order)
       │
       ▼
  Prepare parts (PartPreparation)
  ├── Search parts (PartSearch)
  ├── Check storage location (StockLocation)
  └── Pick parts from shelf
       │
       ▼
  Generate picking PDF (partPicking.jrxml)
  ├── Header: Document No, Date, Job ID
  └── Detail: Part No, Description, Qty, Location
       │
       ▼
  Update Stock (TInventory)
  ├── Reduce stock (issue)
  └── Record transaction
```

### 8.4 Module Dependencies

| Connected To | Relationship |
|-------------|-------------|
| **Job Card** | Picking request references Job Card |
| **Quotation** | Part list from Quotation |
| **Inventory** | Deduct/check stock |
| **Purchase Order** | Order when stock is low |
| **Delivery Sheet** | Part data for delivery |
| **Part Master** | Part Master Data |

### 8.5 Main API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | `/part-picking/pdf` | Generate Part Picking PDF |
| POST | `/part-picking/create` | Create picking request |
| GET | `/part-picking/list` | List picking requests |
| GET | `/part-preparation/list` | List preparation items |
| POST | `/part-preparation/confirm` | Confirm part preparation |
| GET | `/inventory/list` | List stock items |
| POST | `/inventory/receive` | Receive stock |
| POST | `/inventory/issue` | Issue stock |
| GET | `/stock-summary` | Stock summary |
| GET | `/stock/location` | Get stock locations |

---

## 9. Supporting Modules

### 9.1 Authentication & Permission

```
controllers/
├── AuthController.java               ← Login/Logout
├── LoginController.java              ← Login page
├── UserController.java               ← CRUD User
├── PermissionController.java         ← Permission management
└── BaseController.java               ← Base class for all Controllers

config/
├── PermissionInterceptor.java        ← Permission check on every request
└── WebConfig.java                    ← CORS, Interceptor config

domain/
├── MUser.java                        ← User entity
├── MUserMenu.java                    ← User menu permissions
├── MUserJobRole.java                 ← Job roles
└── dto/UserDTO.java

repositories/
├── UserRepository.java
└── custom/UserRepositoryCustom.java

services/
├── interfaces/UserService.java
├── interfaces/PermissionService.java
├── UserServiceImpl.java
└── PermissionServiceImpl.java
```

### 9.2 Master Data Management

```
domain/
├── MCompany.java                     ← Company info
├── MCustomer.java                    ← Customer info
├── MCar.java                         ← Car info
├── MSupplier.java                    ← Supplier
├── MService.java                     ← Service items
├── MCategory.java                    ← Categories
├── MPartMaster.java                  ← Parts master
├── MPaymentMethod.java               ← Payment methods
├── MPaymentTerms.java                ← Payment terms
├── MCurrency.java                    ← Currencies
├── MExchangeRate.java                ← Exchange rates
├── MCountry.java / MCity.java / MProvince.java ← Geographic data
├── MShopProfile.java                 ← Shop information
└── MMenu.java                        ← System menus

controllers/
├── CompanyController.java
├── CustomerController.java
├── CarController.java
├── SupplierController.java
├── CategoryController.java
├── PartMasterController.java
├── DataMasterController.java
├── PaymentMethodController.java
├── PaymentTermsController.java
├── CurrencyController.java
├── CountryController.java
├── CityController.java
├── ProvinceController.java
└── ShopController.java
```

### 9.3 Dashboard & Reports

```
domain/
├── DSalesOverview.java               ← Sales overview
├── DInventoryOverview.java           ← Stock overview
├── DPartsBrand.java                  ← Sales by brand
├── DPartsCategory.java               ← Sales by category
├── DServiceCarDetail.java            ← Service detail
├── DServiceCarIntake.java            ← Car intake count
├── DServiceCategory.java             ← Sales by service type
├── DAccumulateCarBrand.java          ← Accumulated car brand
├── DAccumulateCarName.java           ← Accumulated car name
└── DAccumulateFinance.java           ← Accumulated financials

controllers/
├── DashBoardController.java          ← Dashboard API
├── ReportController.java             ← PDF Reports (JasperReports)
└── ExportDataController.java         ← Export Excel/PDF

services/
├── DashBoardServiceImpl.java
├── ReportGetDataServiceImpl.java
└── ExportDataServiceImpl.java
```

### 9.4 Stock/Inventory Management

```
controllers/
├── InventoryController.java          ← Main Stock management
├── StockAdjustmentController.java    ← Stock adjustment
├── StockLocationController.java      ← Stock location
├── StockSummaryController.java       ← Stock summary
├── StockTakeController.java          ← Stock take/count
└── PitLocationController.java        ← Pit Location (bay)

domain/
├── TInventory.java                   ← Stock transaction
├── TInventoryAdjustmentHeader.java   ← Adjustment Header
├── TInventoryAdjustmentDetail.java   ← Adjustment Detail
├── TStocktakeHeader.java             ← Stock take Header
├── TStocktakeDetail.java             ← Stock take Detail
├── MStockLocation.java               ← Storage location
├── MPitLocation.java                 ← Pit Location
└── VStocktakePartList.java           ← View stock take parts

repositories/
├── InventoryRepository.java
├── StockAdjustmentRepository.java
├── StockLocationRepository.java
├── StockSummaryRepository.java
├── StockTakeRepository.java
└── custom/ + impl/ (for each)

services/
├── InventoryServiceImpl.java
├── StockAdjustmentServiceImpl.java
├── StockLocationServiceImpl.java
├── StockSummaryServiceImpl.java
├── StockTakeServiceImpl.java
└── PitLocationServiceImpl.java
```

### 9.5 Email Service

```
email/
├── EmailService.java                 ← Send Email via SMTP
└── EmailProperties.java              ← SMTP config

controllers/
├── EmailListController.java          ← Manage Email list
└── EmailPromotionController.java (if exists)

domain/
├── TEmailPromotion.java              ← Promotion Emails
└── TEmailReminder.java               ← Reminder Emails

services/
├── MailServiceImpl.java
├── EmailPromotionServiceImpl.java
└── EmailReminderServiceImpl.java
```

### 9.6 Batch Jobs

```
batch/schedule/
└── Schedule.java                     ← 6 Cron Jobs

┌─────────┬────────────────────┬──────────────────────────────┐
│  Batch  │  Time              │  Purpose                     │
├─────────┼────────────────────┼──────────────────────────────┤
│ batch01 │ 06:30 Daily       │ Daily task #1               │
│ batch02 │ 06:45 Daily       │ Daily task #2               │
│ batch03 │ 06:30 Daily       │ Daily task #3               │
│ batch04 │ 03:00 Daily       │ Cleanup/Sync (nightly)       │
│ batch05 │ Every 30 min      │ Real-time sync               │
│ batch06 │ 06:30 Daily       │ Daily task #6               │
└─────────┴────────────────────┴──────────────────────────────┘
```

### 9.7 Utilities

```
utilities/
├── Utility.java                      ← General helper functions
├── ValidateClass.java                ← Data validation
├── VALIDATE_CASES.java               ← Validation cases
├── ExcelGenerator.java               ← Generate Excel files
├── SettingDateTime.java              ← Date/Time management
├── DataTable.java                    ← DataTable helper
├── CustomMultipartFile.java          ← Custom file upload
├── ConvertDollar.java                ← Amount to English text
├── ConvertBath.java                  ← Amount to Thai text
├── BreakWordByBreakIterator.java     ← Word break utility
├── Round.java                        ← Number rounding
├── Params.java                       ← Query parameters helper
└── GENERIC_CLASS.java                ← Generic helper class
```

---

## Module Dependency Map


```
                          ┌─────────────────┐
                          │   BOOKING        │
                          └────────┬────────┘
                                   │
                                   ▼
┌──────────────┐          ┌─────────────────┐
│  CUSTOMER    │◄─────────│    JOB CARD     │
│              │          │  (Job Order)    │
└──────────────┘          └────────┬────────┘
                                   │
              ┌────────────────────┼────────────────────┐
              │                    │                    │
              ▼                    ▼                    ▼
     ┌────────────────┐  ┌────────────────┐  ┌────────────────┐
     │  QUOTATION     │  │  JOB SERVICE   │  │  JOB PART      │
     │                │  │                │  │  (Part Sale)   │
     └───────┬────────┘  └────────────────┘  └────────────────┘
             │
    ┌────────┴────────┐
    │                 │
    ▼                 ▼
┌──────────────┐ ┌──────────────┐
│ PURCHASE     │ │ PART PICKING │
│ ORDER        │ │ (Picking)    │
└──────┬───────┘ └──────┬───────┘
       │                │
       │                ▼
       │         ┌──────────────┐
       └────────►│  INVENTORY   │
                 │ (Warehouse)   │
                 └──────┬───────┘
                        │
         ┌──────────────┼──────────────┐
         │              │              │
         ▼              ▼              ▼
  ┌────────────┐ ┌────────────┐ ┌────────────┐
  │ STOCK      │ │ STOCK      │ │ STOCK TAKE │
  │ ADJUSTMENT │ │ LOCATION   │ │ (Count)    │
  └────────────┘ └────────────┘ └────────────┘
         │
         ▼
  ┌──────────────┐
  │   INVOICE    │
  └──────┬───────┘
         │
    ┌────┴────┐
    │         │
    ▼         ▼
┌────────┐ ┌────────┐
│ CREDIT │ │ DEBIT  │
│ NOTE   │ │ NOTE   │
│        │ │        │
└────────┘ └────────┘
    │         │
    └────┬────┘
         │
         ▼
  ┌──────────────┐     ┌──────────────┐
  │   PAYMENT    │────►│   RECEIPT    │
  └──────────────┘     └──────────────┘
         │
         ▼
  ┌──────────────┐
  │   DELIVERY   │
  │   SHEET      │
  └──────────────┘
```

## File Count Summary by Layer

| Layer | Count | Description |
|-------|-------|-------------|
| **Controllers** | 63 | REST API endpoints |
| **Services (Impl)** | 66 | Business logic |
| **Services (Interface)** | 68 | Service contracts |
| **Repositories (JPA)** | 100+ | Database access |
| **Repositories (Custom)** | 47 | Custom queries |
| **Repositories (Impl)** | 50+ | Custom query implementations |
| **Domain (M*)** | 44 | Master Data entities |
| **Domain (T*)** | 35 | Transaction entities |
| **Domain (V*)** | 17 | View/Report entities |
| **Domain (D*)** | 10 | Dashboard entities |
| **DTOs** | 150+ | Data Transfer Objects |
| **Response** | 31 | Response wrapper classes |
| **Utilities** | 13 | Helper classes |
| **Config** | 4 | Configuration classes |
| **Email** | 2 | Email service |
| **Batch** | 1 | Scheduler (6 jobs) |
| **JasperReport Templates** | 16 | PDF report templates |
| **i18n Messages** | 25 | 18+ languages |

---