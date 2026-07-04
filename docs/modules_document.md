## ✅  เอกสาร โคงการ
 

1. **สารบัญ (Table of Contents)** – ครอบคลุมทุกโมดูล
2. **บทนำและภาพรวมระบบ** – สถาปัตยกรรม, Tech Stack, กลุ่มผู้ใช้งาน
3. **โมดูลทั้ง 14** – แต่ละโมดูลมีโครงสร้าง, Database Design, Cache, Rate Limit, API
4. **สรุปฐานข้อมูลทั้งหมด** – รวม DDL ทั้งหมด
5. **สรุป API ทั้งหมด** – รวม Endpoints ทั้งหมด
6. **สรุป Cache Keys** – รายการ Redis Keys ทั้งหมด
7. **สรุป Rate Limit** – ตารางสรุป Rate Limit แต่ละ Endpoint

---

## 📋 สารบัญ (Table of Contents)

### ส่วนที่ 1: บทนำและภาพรวมระบบ
1.1 วัตถุประสงค์และเป้าหมาย
1.2 เทคโนโลยีหลัก (Tech Stack)
1.3 สถาปัตยกรรมภาพรวม
1.4 กลุ่มผู้ใช้งาน
1.5 บทนิยามศัพท์

### ส่วนที่ 2: โครงสร้างโปรเจกต์หลัก
2.1 Root Structure
2.2 Shared Components (`_shared/`)
2.3 Configuration
2.4 Exception Handling
2.5 Logging System (AOP)

### ส่วนที่ 3: โมดูลทั้ง 14
| # | โมดูล | รายละเอียด |
|---|-------|-----------|
| 1 | 🔑 Authentication & Permission | JWT + Role/Permission + Rate Limit + Redis Cache |
| 2 | 🚗 Job Card Management | 14 Statuses + Service/Part + History + Cache |
| 3 | 👥 Customer Management | Customer + Car + History + Cache (ID/Phone/Plate) |
| 4 | 📋 Quotation | Quotation + Part/Service + Approve/Reject + PDF + Cache |
| 5 | 🛒 Purchase Order | PO + Status + Send/Receive + PDF + Email + Cache |
| 6 | 📦 Inventory Management | Part Master + Receive/Issue + Picking + Stock Take + Adjustment + Cache |
| 7 | 💰 Payment Management | Payment Record + Receipt + Outstanding Balance + Refund + Cache |
| 8 | 📊 Dashboard & Reports | Overview + Sales + Inventory + Job Status + Top Parts + Financial + Export |
| 9 | 📄 Document Management | Document Generation + Upload + OCR + Template Management + Storage + Cache |
| 10 | 📧 Email Service | Template-based Email + Attachments + Bulk + Queue + History + Cache |
| 11 | ⏱️ Batch Jobs | 6 Scheduled Jobs + Distributed Lock + History + Manual Trigger + Cache |
| 12 | 🌏 Multi-Language (i18n) | 18 Languages + Translation Management + Resource Bundle + Cache |
| 13 | 📡 IoT & GPS Tracking | Device Management + GPS Tracking + MQTT + InfluxDB + Geofence + Auto Report + Cache |
| 14 | 🛍️ Web Order System (WOS) | Catalogue + Cart + Order + Sales Price + Promotion + Integration + Cache |

---

## 📦 ส่วนที่ 1: บทนำและภาพรวมระบบ

### 1.1 วัตถุประสงค์
ระบบบริหารจัดการอู่ซ่อมรถ (Auto Repair Shop Management System) ถูกพัฒนาขึ้นเพื่อเพิ่มประสิทธิภาพในการดำเนินงานของศูนย์บริการหรืออู่ซ่อมรถ ครอบคลุมตั้งแต่การรับรถเข้าซ่อม การวินิจฉัยปัญหา การเสนอราคา การสั่งซื้ออะไหล่ การเบิกจ่ายสินค้าคงคลัง การออกใบแจ้งหนี้ และการติดตามประวัติการซ่อมบำรุงของลูกค้าและยานพาหนะ

ระบบถูกออกแบบให้มีความยืดหยุ่น รองรับการขยายตัวในอนาคต โดยใช้สถาปัตยกรรมแบบ **Domain‑Driven Design (DDD)** ผสานกับ **Clean Architecture** และ **Event‑Driven** เพื่อแยกความรับผิดชอบและเพิ่มความสามารถในการบำรุงรักษา

### 1.2 เทคโนโลยีหลัก (Tech Stack)

| หมวดหมู่ | เทคโนโลยี | เวอร์ชัน |
|---------|-----------|----------|
| **ภาษา** | Java | 17+ / 21 |
| **Framework** | Spring Boot | 3.4.1 |
| **ORM** | Spring Data JPA (Hibernate) | - |
| **ฐานข้อมูลหลัก** | PostgreSQL | 15+ |
| **Cache** | Redis | 7+ |
| **Message Queue** | Apache Kafka | 3.4+ |
| **Logging & Monitoring** | ELK (Elasticsearch, Logstash, Kibana), Grafana, Micrometer | - |
| **การจัดการเอกสาร** | JasperReports (PDF), Apache POI (Excel) | - |
| **IoT** | MQTT, InfluxDB | - |
| **Documentation** | Swagger/OpenAPI 3.0 | - |
| **Build Tool** | Maven | 3.8+ |

### 1.3 สถาปัตยกรรมภาพรวม

```
┌─────────────────────────────────────────────────────────────────────────────────┐
│                              EXTERNAL SYSTEMS                                    │
└─────────────────────────────────────────────────────────────────────────────────┘
                                        │
                                        ▼
┌─────────────────────────────────────────────────────────────────────────────────┐
│                             API GATEWAY / LOAD BALANCER                          │
└─────────────────────────────────────────────────────────────────────────────────┘
                                        │
                                        ▼
┌─────────────────────────────────────────────────────────────────────────────────┐
│                             SPRING BOOT APPLICATION                              │
│  ┌───────────────────────────────────────────────────────────────────────────┐  │
│  │                           CONTROLLER LAYER                                 │  │
│  └───────────────────────────────────────────────────────────────────────────┘  │
│                                      │                                           │
│  ┌───────────────────────────────────▼───────────────────────────────────────┐  │
│  │                             SERVICE LAYER                                  │  │
│  └───────────────────────────────────┬───────────────────────────────────────┘  │
│                                      │                                           │
│  ┌───────────────────────────────────▼───────────────────────────────────────┐  │
│  │                            REPOSITORY LAYER                                │  │
│  └───────────────────────────────────┬───────────────────────────────────────┘  │
└────────────────────────────────────┼──────────────────────────────────────────┘
                                     │
                 ┌───────────────────┼───────────────────┐
                 ▼                   ▼                   ▼
        ┌──────────────┐    ┌──────────────┐    ┌──────────────┐
        │  PostgreSQL  │    │    Redis     │    │    Kafka     │
        │  (Primary DB)│    │   (Cache)    │    │ (Event Bus)  │
        └──────────────┘    └──────────────┘    └──────┬───────┘
                                                        │
        ┌───────────────────────────────────────────────┼───────────────────┐
        ▼                                               ▼                   ▼
 ┌─────────────┐                                ┌─────────────┐      ┌─────────────┐
 │ Elasticsearch│                                │  InfluxDB   │      │   Grafana   │
 │   (ELK)     │                                │  (IoT Data) │      │  (Metrics)  │
 └─────────────┘                                └─────────────┘      └─────────────┘
```

---

## 🗄️ ส่วนที่ 2: สรุปฐานข้อมูลทั้งหมด (Consolidated Database Schema)

### 2.1 ตาราง Master Data (ทั้งหมด 15 ตาราง)

| ลำดับ | ตาราง | คำอธิบาย | โมดูล |
|-------|-------|----------|-------|
| 1 | `m_user` | ผู้ใช้ระบบ | Auth |
| 2 | `m_user_menu` | เมนูผู้ใช้ | Auth |
| 3 | `m_user_job_role` | บทบาทผู้ใช้ | Auth |
| 4 | `m_user_token` | Token ที่ออกให้ | Auth |
| 5 | `m_role` | บทบาท | Auth |
| 6 | `m_permission` | สิทธิ์การใช้งาน | Auth |
| 7 | `m_user_role` | เชื่อม User-Role | Auth |
| 8 | `m_role_permission` | เชื่อม Role-Permission | Auth |
| 9 | `m_customer` | ข้อมูลลูกค้า | Customer |
| 10 | `m_car` | ข้อมูลรถยนต์ | Customer |
| 11 | `m_supplier` | ข้อมูลผู้จัดจำหน่าย | Master Data |
| 12 | `m_part_master` | ข้อมูลอะไหล่หลัก | Inventory |
| 13 | `m_service` | รายการบริการ | Master Data |
| 14 | `m_category` | หมวดหมู่สินค้า/บริการ | Master Data |
| 15 | `m_stock_location` | ตำแหน่งจัดเก็บสินค้า | Inventory |
| 16 | `m_payment_method` | วิธีการชำระเงิน | Payment |
| 17 | `m_currency` | สกุลเงิน | Master Data |
| 18 | `m_exchange_rate` | อัตราแลกเปลี่ยน | Master Data |
| 19 | `m_country` | ประเทศ | Master Data |
| 20 | `m_city` | อำเภอ/เขต | Master Data |
| 21 | `m_province` | จังหวัด | Master Data |
| 22 | `m_shop_profile` | ข้อมูลร้านค้า | Master Data |
| 23 | `m_staff` | ข้อมูลพนักงาน | Staff |
| 24 | `m_iot_device` | อุปกรณ์ IoT | IoT |
| 25 | `m_geofence` | รั้วรอบขอบ | IoT |
| 26 | `m_document_template` | เทมเพลตเอกสาร | Document |
| 27 | `m_email_template` | เทมเพลตอีเมล | Email |
| 28 | `m_language` | ภาษา | i18n |
| 29 | `m_translation` | ข้อความที่แปลแล้ว | i18n |
| 30 | `m_batch_job` | งาน Batch | Batch |
| 31 | `m_catalogue_category` | หมวดหมู่แคตตาล็อก | WOS |
| 32 | `m_catalogue_item` | สินค้าแคตตาล็อก | WOS |
| 33 | `m_sales_price` | ราคาขาย | WOS |
| 34 | `m_promotion` | โปรโมชัน | WOS |

### 2.2 ตาราง Transaction (ทั้งหมด 25 ตาราง)

| ลำดับ | ตาราง | คำอธิบาย | โมดูล |
|-------|-------|----------|-------|
| 1 | `t_job` | ใบงาน | Job |
| 2 | `t_job_service` | รายการบริการในใบงาน | Job |
| 3 | `t_job_part_sales` | อะไหล่ที่ขายในใบงาน | Job |
| 4 | `t_job_service_car_symptom` | อาการรถ | Job |
| 5 | `t_job_diag_trouble_code` | รหัสข้อผิดพลาด | Job |
| 6 | `t_job_status_history` | ประวัติสถานะใบงาน | Job |
| 7 | `t_quotation` | ใบเสนอราคา | Quotation |
| 8 | `t_quotation_part` | อะไหล่ในใบเสนอราคา | Quotation |
| 9 | `t_quotation_service` | บริการในใบเสนอราคา | Quotation |
| 10 | `t_quotation_status_history` | ประวัติสถานะเสนอราคา | Quotation |
| 11 | `t_purchase_order_header` | ใบสั่งซื้อ | Purchase |
| 12 | `t_purchase_order_detail` | รายการใบสั่งซื้อ | Purchase |
| 13 | `t_purchase_order_status_history` | ประวัติสถานะ PO | Purchase |
| 14 | `t_invoice_adjustment` | ใบแจ้งหนี้/ลด/เพิ่ม | Invoice |
| 15 | `t_invoice_adjustment_part` | อะไหล่ในใบแจ้งหนี้ | Invoice |
| 16 | `t_invoice_adjustment_service` | บริการในใบแจ้งหนี้ | Invoice |
| 17 | `t_received_amount` | ประวัติรับชำระเงิน | Payment |
| 18 | `t_payment` | การชำระเงิน | Payment |
| 19 | `t_receipt` | ใบเสร็จรับเงิน | Payment |
| 20 | `t_payment_history` | ประวัติสถานะชำระเงิน | Payment |
| 21 | `t_outstanding_balance` | ยอดคงค้าง | Payment |
| 22 | `t_inventory` | การเคลื่อนไหวสินค้า | Inventory |
| 23 | `t_inventory_adjustment_header` | การปรับปรุงสต็อก | Inventory |
| 24 | `t_inventory_adjustment_detail` | รายละเอียดปรับปรุง | Inventory |
| 25 | `t_stocktake_header` | การตรวจนับสต็อก | Inventory |
| 26 | `t_stocktake_detail` | รายละเอียดตรวจนับ | Inventory |
| 27 | `t_part_picking_request` | คำขอเบิกอะไหล่ | Inventory |
| 28 | `t_part_picking_detail` | รายละเอียดขอเบิก | Inventory |
| 29 | `t_document` | เอกสารที่สร้างแล้ว | Document |
| 30 | `t_document_history` | ประวัติเอกสาร | Document |
| 31 | `t_ocr_result` | ผลลัพธ์ OCR | Document |
| 32 | `t_email_history` | ประวัติส่งอีเมล | Email |
| 33 | `t_email_queue` | คิวอีเมล | Email |
| 34 | `t_batch_job_history` | ประวัติการรัน Batch | Batch |
| 35 | `t_gps_data` | ข้อมูล GPS | IoT |
| 36 | `t_device_history` | ประวัติอุปกรณ์ IoT | IoT |
| 37 | `t_device_access_log` | บันทึกการเข้าถึงอุปกรณ์ | IoT |
| 38 | `t_geofence_alert` | การแจ้งเตือน Geofence | IoT |
| 39 | `t_auto_report` | รายงานอัตโนมัติ | IoT |
| 40 | `t_shopping_cart` | ตะกร้าสินค้า | WOS |
| 41 | `t_shopping_cart_item` | รายการในตะกร้า | WOS |
| 42 | `t_web_order` | ออเดอร์ออนไลน์ | WOS |
| 43 | `t_web_order_item` | รายการออเดอร์ | WOS |
| 44 | `t_web_order_status_history` | ประวัติสถานะออเดอร์ | WOS |

### 2.3 ตาราง View (Dashboard)

| ลำดับ | View | คำอธิบาย | โมดูล |
|-------|------|----------|-------|
| 1 | `v_dashboard_sales_overview` | ภาพรวมยอดขาย | Dashboard |
| 2 | `v_dashboard_job_status` | สรุปสถานะใบงาน | Dashboard |
| 3 | `v_dashboard_inventory_overview` | ภาพรวมสินค้าคงคลัง | Dashboard |
| 4 | `v_dashboard_top_parts` | อะไหล่ขายดี | Dashboard |
| 5 | `v_dashboard_service_category` | บริการแยกประเภท | Dashboard |
| 6 | `v_dashboard_financial_summary` | สรุปการเงิน | Dashboard |
| 7 | `v_dashboard_revenue_by_period` | รายได้แยกช่วงเวลา | Dashboard |
| 8 | `v_available_languages` | ภาษาที่ใช้งานได้ | i18n |
| 9 | `v_email_analytics` | สถิติการส่งอีเมล | Email |

---

## 🚏 ส่วนที่ 3: สรุป API ทั้งหมด

### 3.1 Authentication (`/api/v1/auth`)

| Method | Path | คำอธิบาย | Rate Limit |
|--------|------|----------|------------|
| POST | `/login` | เข้าสู่ระบบ | 5/300s |
| POST | `/logout` | ออกจากระบบ | 10/60s |
| POST | `/refresh` | ต่ออายุ Token | 20/3600s |
| POST | `/register` | ลงทะเบียนผู้ใช้ใหม่ | 3/3600s |
| GET | `/me` | ข้อมูลผู้ใช้ปัจจุบัน | 50/60s |
| PUT | `/profile` | อัปเดตข้อมูลผู้ใช้ | 10/60s |
| POST | `/change-password` | เปลี่ยนรหัสผ่าน | 5/60s |

### 3.2 Job Card (`/api/v1/jobs`)

| Method | Path | คำอธิบาย | Rate Limit |
|--------|------|----------|------------|
| POST | `/create` | สร้างใบงานใหม่ | 30/60s |
| GET | `/list` | รายการใบงาน | 50/60s |
| GET | `/{id}` | ดูรายละเอียดใบงาน | 100/60s |
| PUT | `/{id}` | แก้ไขใบงาน | 20/60s |
| PUT | `/{id}/status` | เปลี่ยนสถานะ | 60/60s |
| DELETE | `/{id}` | ลบใบงาน | 10/3600s |
| GET | `/{id}/history` | ประวัติสถานะ | 50/60s |
| GET | `/report/{id}` | PDF ใบงาน | 20/300s |
| POST | `/{id}/services` | เพิ่มบริการ | 30/60s |
| POST | `/{id}/parts` | เพิ่มอะไหล่ | 30/60s |

### 3.3 Customer (`/api/v1/customers`)

| Method | Path | คำอธิบาย | Rate Limit |
|--------|------|----------|------------|
| POST | `/create` | สร้างลูกค้าใหม่ | 20/60s |
| GET | `/{id}` | ดูข้อมูลลูกค้า | 100/60s |
| PUT | `/{id}` | แก้ไขลูกค้า | 15/60s |
| DELETE | `/{id}` | ลบลูกค้า | 10/3600s |
| POST | `/search` | ค้นหาลูกค้า | 50/60s |
| GET | `/{id}/history` | ประวัติการซ่อม | 50/60s |
| GET | `/phone/{phone}` | ค้นหาด้วยเบอร์โทร | 60/60s |

### 3.4 Car (`/api/v1/cars`)

| Method | Path | คำอธิบาย | Rate Limit |
|--------|------|----------|------------|
| POST | `/create` | เพิ่มรถใหม่ | 30/60s |
| GET | `/{id}` | ดูข้อมูลรถ | 100/60s |
| PUT | `/{id}` | แก้ไขรถ | 15/60s |
| DELETE | `/{id}` | ลบรถ | 10/3600s |
| GET | `/customer/{customerId}` | รายการรถของลูกค้า | 80/60s |
| GET | `/plate/{licensePlate}` | ค้นหาด้วยทะเบียน | 60/60s |
| POST | `/search` | ค้นหารถ | 40/60s |

### 3.5 Quotation (`/api/v1/quotations`)

| Method | Path | คำอธิบาย | Rate Limit |
|--------|------|----------|------------|
| POST | `/create` | สร้างใบเสนอราคา | 20/60s |
| GET | `/{id}` | ดูรายละเอียด | 100/60s |
| GET | `/job/{jobId}` | ดึงตาม Job ID | 80/60s |
| GET | `/list` | รายการใบเสนอราคา | 50/60s |
| PUT | `/{id}` | แก้ไขใบเสนอราคา | 15/60s |
| DELETE | `/{id}` | ลบใบเสนอราคา | 10/3600s |
| PUT | `/{id}/approve` | อนุมัติ | 20/60s |
| PUT | `/{id}/reject` | ปฏิเสธ | 20/60s |
| GET | `/{id}/pdf` | PDF ใบเสนอราคา | 15/300s |
| POST | `/parts` | เพิ่มอะไหล่ | 30/60s |
| PUT | `/parts/{id}` | แก้ไขอะไหล่ | 20/60s |
| DELETE | `/parts/{id}` | ลบอะไหล่ | 20/60s |

### 3.6 Purchase Order (`/api/v1/purchase-orders`)

| Method | Path | คำอธิบาย | Rate Limit |
|--------|------|----------|------------|
| POST | `/create` | สร้าง PO | 20/60s |
| POST | `/from-quotation/{quotationId}` | สร้างจาก Quotation | 15/60s |
| GET | `/{id}` | ดูรายละเอียด PO | 100/60s |
| GET | `/quotation/{quotationId}` | ดึงตาม Quotation | 60/60s |
| GET | `/list` | รายการ PO | 50/60s |
| PUT | `/{id}` | แก้ไข PO | 15/60s |
| DELETE | `/{id}` | ลบ PO | 10/3600s |
| POST | `/{id}/send` | ส่งให้ Supplier | 10/60s |
| PUT | `/{id}/confirm` | Supplier ยืนยัน | 20/60s |
| POST | `/{id}/receive` | รับสินค้า | 15/60s |
| PUT | `/{id}/cancel` | ยกเลิก PO | 10/3600s |
| GET | `/{id}/pdf` | PDF PO | 15/300s |
| GET | `/suggestions/{jobId}` | แนะนำ PO | 30/60s |

### 3.7 Inventory (`/api/v1/inventory`)

| Method | Path | คำอธิบาย | Rate Limit |
|--------|------|----------|------------|
| POST | `/receive` | รับสินค้าเข้า | 20/60s |
| POST | `/issue` | เบิกจ่ายสินค้า | 30/60s |
| GET | `/part/{partId}` | ดู Inventory ของอะไหล่ | 80/60s |
| GET | `/transactions` | ประวัติการเคลื่อนไหว | 50/60s |
| GET | `/summary` | สรุปสินค้าคงคลัง | 20/60s |

### 3.8 Part Master (`/api/v1/parts`)

| Method | Path | คำอธิบาย | Rate Limit |
|--------|------|----------|------------|
| POST | `/create` | เพิ่มอะไหล่ใหม่ | 20/60s |
| GET | `/{id}` | ดูข้อมูลอะไหล่ | 100/60s |
| GET | `/code/{partCode}` | ค้นหาด้วยรหัส | 80/60s |
| POST | `/search` | ค้นหาอะไหล่ | 50/60s |
| PUT | `/{id}` | แก้ไขอะไหล่ | 15/60s |
| DELETE | `/{id}` | ลบอะไหล่ | 10/3600s |
| GET | `/low-stock` | อะไหล่ต่ำกว่าเกณฑ์ | 30/60s |

### 3.9 Part Picking (`/api/v1/part-picking`)

| Method | Path | คำอธิบาย | Rate Limit |
|--------|------|----------|------------|
| POST | `/create` | สร้างคำขอเบิก | 30/60s |
| GET | `/{id}` | ดูคำขอเบิก | 80/60s |
| GET | `/job/{jobId}` | ดึงตาม Job ID | 60/60s |
| GET | `/list` | รายการคำขอเบิก | 50/60s |
| PUT | `/{id}/confirm` | ยืนยันการเบิก | 20/60s |
| PUT | `/{id}/cancel` | ยกเลิกคำขอเบิก | 10/3600s |
| GET | `/{id}/pdf` | PDF เอกสารเบิก | 15/300s |

### 3.10 Payment (`/api/v1/payments`)

| Method | Path | คำอธิบาย | Rate Limit |
|--------|------|----------|------------|
| POST | `/record` | บันทึกการชำระเงิน | 20/60s |
| GET | `/{id}` | ดูข้อมูลการชำระ | 100/60s |
| GET | `/invoice/{invoiceId}` | ดึงตาม Invoice | 60/60s |
| POST | `/search` | ค้นหาการชำระ | 50/60s |
| GET | `/outstanding/{customerId}` | ยอดคงค้าง | 40/60s |
| GET | `/history/{customerId}` | ประวัติการชำระ | 40/60s |
| POST | `/{id}/refund` | คืนเงิน | 10/3600s |
| PUT | `/{id}/cancel` | ยกเลิกการชำระ | 10/3600s |

### 3.11 Receipt (`/api/v1/receipts`)

| Method | Path | คำอธิบาย | Rate Limit |
|--------|------|----------|------------|
| GET | `/{id}` | ดูข้อมูลใบเสร็จ | 100/60s |
| GET | `/payment/{paymentId}` | ดึงตาม Payment | 60/60s |
| GET | `/{id}/pdf` | PDF ใบเสร็จ | 15/300s |
| PUT | `/{id}/cancel` | ยกเลิกใบเสร็จ | 10/3600s |

### 3.12 Dashboard (`/api/v1/dashboard`)

| Method | Path | คำอธิบาย | Rate Limit |
|--------|------|----------|------------|
| GET | `/overview` | ภาพรวม Dashboard | 30/60s |
| GET | `/sales` | ภาพรวมยอดขาย | 30/60s |
| GET | `/job-status` | สรุปสถานะใบงาน | 30/60s |
| GET | `/inventory` | ภาพรวมสินค้าคงคลัง | 30/60s |
| GET | `/top-parts` | อะไหล่ขายดี | 20/60s |
| GET | `/service-category` | บริการแยกประเภท | 20/60s |
| GET | `/revenue` | รายได้แยกช่วงเวลา | 25/60s |
| GET | `/financial` | สรุปการเงิน | 15/60s |
| POST | `/filtered` | Dashboard แบบกำหนดเอง | 20/60s |

### 3.13 Reports (`/api/v1/reports`)

| Method | Path | คำอธิบาย | Rate Limit |
|--------|------|----------|------------|
| POST | `/daily` | รายงานรายวัน | 10/300s |
| POST | `/monthly` | รายงานรายเดือน | 10/300s |
| POST | `/yearly` | รายงานรายปี | 5/3600s |
| GET | `/status/{reportId}` | สถานะการสร้างรายงาน | 20/60s |

### 3.14 Email (`/api/v1/email`)

| Method | Path | คำอธิบาย | Rate Limit |
|--------|------|----------|------------|
| POST | `/send` | ส่งอีเมล | 20/60s |
| POST | `/send-template` | ส่งอีเมลจากเทมเพลต | 25/60s |
| POST | `/bulk` | ส่งอีเมลจำนวนมาก | 5/300s |
| GET | `/status/{emailId}` | สถานะการส่ง | 50/60s |
| POST | `/resend/{emailId}` | ส่งอีเมลซ้ำ | 10/3600s |

### 3.15 Batch Jobs (`/api/v1/batch-jobs`)

| Method | Path | คำอธิบาย | Rate Limit |
|--------|------|----------|------------|
| GET | `/list` | รายการงาน Batch | 20/60s |
| GET | `/{jobCode}/status` | สถานะงาน | 30/60s |
| POST | `/{jobCode}/trigger` | สั่งรันงาน | 5/3600s |
| POST | `/{jobCode}/stop` | หยุดงาน | 3/3600s |
| PUT | `/{jobCode}/toggle` | เปิด/ปิดใช้งาน | 10/300s |
| GET | `/{jobCode}/history` | ประวัติการรัน | 20/60s |
| GET | `/history/all` | ประวัติทั้งหมด | 15/60s |

### 3.16 IoT Devices (`/api/v1/iot/devices`)

| Method | Path | คำอธิบาย | Rate Limit |
|--------|------|----------|------------|
| POST | `/register` | ลงทะเบียนอุปกรณ์ | 10/300s |
| GET | `/{id}` | ดูข้อมูลอุปกรณ์ | 100/60s |
| GET | `/list` | รายการอุปกรณ์ | 50/60s |
| PUT | `/{id}` | แก้ไขอุปกรณ์ | 15/60s |
| GET | `/{id}/status` | สถานะอุปกรณ์ | 60/60s |
| GET | `/{id}/location` | ตำแหน่งล่าสุด | 60/60s |
| GET | `/{id}/history` | ประวัติตำแหน่ง | 30/60s |
| GET | `/{id}/event-history` | ประวัติเหตุการณ์ | 30/60s |
| DELETE | `/{id}` | ลบอุปกรณ์ | 5/3600s |

### 3.17 Geofence (`/api/v1/iot/geofences`)

| Method | Path | คำอธิบาย | Rate Limit |
|--------|------|----------|------------|
| POST | `/create` | สร้าง Geofence | 10/300s |
| GET | `/{id}` | ดูข้อมูล Geofence | 80/60s |
| GET | `/list` | รายการ Geofence | 40/60s |
| DELETE | `/{id}` | ลบ Geofence | 5/3600s |
| GET | `/alerts` | รายการแจ้งเตือน | 30/60s |

### 3.18 MQTT (`/api/v1/iot/mqtt`)

| Method | Path | คำอธิบาย | Rate Limit |
|--------|------|----------|------------|
| POST | `/publish` | ส่งข้อความ MQTT | 20/60s |

### 3.19 WOS Catalogue (`/api/v1/wos/catalogue`)

| Method | Path | คำอธิบาย | Rate Limit |
|--------|------|----------|------------|
| GET | `/list` | รายการสินค้า | 100/60s |
| GET | `/{id}` | ดูสินค้า | 200/60s |
| GET | `/category/{categoryId}` | สินค้าตามหมวดหมู่ | 100/60s |
| GET | `/search` | ค้นหาสินค้า | 80/60s |
| GET | `/featured` | สินค้าแนะนำ | 100/60s |
| GET | `/categories` | รายการหมวดหมู่ | 50/60s |

### 3.20 WOS Cart (`/api/v1/wos/cart`)

| Method | Path | คำอธิบาย | Rate Limit |
|--------|------|----------|------------|
| POST | `/add` | เพิ่มสินค้าในตะกร้า | 50/60s |
| PUT | `/update` | ปรับจำนวนสินค้า | 50/60s |
| DELETE | `/remove/{itemId}` | ลบสินค้าจากตะกร้า | 30/60s |
| GET | `/view` | ดูตะกร้าสินค้า | 100/60s |
| DELETE | `/clear` | ล้างตะกร้า | 10/300s |
| POST | `/promotion` | ใช้โค้ดส่วนลด | 20/60s |

### 3.21 WOS Orders (`/api/v1/wos/orders`)

| Method | Path | คำอธิบาย | Rate Limit |
|--------|------|----------|------------|
| POST | `/create` | สั่งซื้อ | 20/60s |
| GET | `/{id}` | ดูออเดอร์ | 100/60s |
| GET | `/number/{orderNo}` | ดึงตามเลขที่ | 80/60s |
| GET | `/list` | ประวัติการสั่งซื้อ | 50/60s |
| PUT | `/{id}/status` | เปลี่ยนสถานะ | 20/60s |
| PUT | `/{id}/cancel` | ยกเลิกออเดอร์ | 10/3600s |
| GET | `/{id}/history` | ประวัติสถานะ | 50/60s |
| GET | `/{id}/tracking` | ข้อมูลติดตาม | 60/60s |

---

## 🗄️ ส่วนที่ 4: สรุป Redis Cache Keys

| Cache Name | Key Pattern | TTL | คำอธิบาย | โมดูล |
|------------|-------------|-----|----------|-------|
| `user-permissions` | `{userId}` | 1 ชม. | สิทธิ์ของผู้ใช้ | Auth |
| `user-sessions` | `{userId}` | 15 นาที | Session ผู้ใช้ | Auth |
| `token-blacklist` | `{token}` | 1 วัน | Token ที่ถูกเพิกถอน | Auth |
| `jobs` | `{jobId}` | 30 นาที | ข้อมูลใบงาน | Job |
| `job_status_summary` | `{whitelabelId}` | 5 นาที | สรุปสถานะใบงาน | Job |
| `customers` | `{customerId}` | 1 ชม. | ข้อมูลลูกค้า | Customer |
| `customer_phone` | `{phone}` | 1 ชม. | ลูกค้าตามเบอร์โทร | Customer |
| `cars` | `{carId}` | 1 ชม. | ข้อมูลรถยนต์ | Car |
| `car_plate` | `{licensePlate}` | 1 ชม. | รถตามทะเบียน | Car |
| `quotations` | `{quotationId}` | 30 นาที | ข้อมูลใบเสนอราคา | Quotation |
| `quotation_job` | `{jobId}` | 30 นาที | Quotation ตาม Job | Quotation |
| `purchase_orders` | `{poId}` | 30 นาที | ข้อมูล PO | Purchase |
| `po_quotation` | `{quotationId}` | 30 นาที | PO ตาม Quotation | Purchase |
| `po_suggestion` | `{jobId}` | 15 นาที | แนะนำ PO | Purchase |
| `parts` | `{partId}` | 1 ชม. | ข้อมูลอะไหล่ | Inventory |
| `part_code` | `{partCode}` | 1 ชม. | อะไหล่ตามรหัส | Inventory |
| `stock_summary` | `{partId}` | 15 นาที | สรุปสต็อก | Inventory |
| `payments` | `{paymentId}` | 1 ชม. | ข้อมูลการชำระเงิน | Payment |
| `payment_invoice` | `{invoiceId}` | 1 ชม. | Payment ตาม Invoice | Payment |
| `receipts` | `{receiptId}` | 1 ชม. | ข้อมูลใบเสร็จ | Receipt |
| `receipt_payment` | `{paymentId}` | 1 ชม. | Receipt ตาม Payment | Receipt |
| `dashboard_overview` | `{whitelabelId}` | 5 นาที | ภาพรวม Dashboard | Dashboard |
| `sales_by_period` | `{whitelabelId}:{period}` | 5 นาที | ยอดขายแยกช่วง | Dashboard |
| `top_parts` | `{whitelabelId}` | 5 นาที | อะไหล่ขายดี | Dashboard |
| `documents` | `{documentId}` | 1 ชม. | ข้อมูลเอกสาร | Document |
| `document_ref` | `{refType}:{refId}` | 1 ชม. | เอกสารตาม Reference | Document |
| `templates` | `{templateCode}` | 2 ชม. | เทมเพลตเอกสาร | Document |
| `email_templates` | `{templateCode}:{lang}` | 2 ชม. | เทมเพลตอีเมล | Email |
| `messages` | `{messageKey}:{lang}` | 1 ชม. | ข้อความที่แปลแล้ว | i18n |
| `messages_all` | `{lang}` | 1 ชม. | ข้อความทั้งหมดของภาษา | i18n |
| `languages` | `{languageCode}` | 2 ชม. | ข้อมูลภาษา | i18n |
| `languages_active` | `'all'` | 2 ชม. | ภาษาที่ใช้งาน | i18n |
| `batch_jobs` | `{jobCode}` | 5 นาที | ข้อมูลงาน Batch | Batch |
| `iot_devices` | `{deviceId}` | 1 นาที | ข้อมูลอุปกรณ์ IoT | IoT |
| `iot_device_identifier` | `{identifier}` | 1 นาที | อุปกรณ์ตาม Hardware ID | IoT |
| `device_location` | `{deviceId}` | 30 วินาที | ตำแหน่งล่าสุด | IoT |
| `catalogue` | `{itemId}` | 1 ชม. | สินค้าแคตตาล็อก | WOS |
| `catalogue_code` | `{itemCode}` | 1 ชม. | สินค้าตามรหัส | WOS |
| `catalogue_category` | `{categoryId}` | 1 ชม. | สินค้าตามหมวดหมู่ | WOS |
| `cart` | `{cartId}` | 30 นาที | ตะกร้าสินค้า | WOS |
| `cart_customer` | `{customerId}` | 30 นาที | ตะกร้าตามลูกค้า | WOS |
| `orders` | `{orderId}` | 30 นาที | ข้อมูลออเดอร์ | WOS |

---

## 🗄️ ส่วนที่ 5: สรุป Rate Limit ทั้งหมด

| ประเภท | Limit | Duration | คำอธิบาย |
|--------|-------|----------|----------|
| **Login** | 5 | 300s | ป้องกัน Brute Force |
| **Register** | 3 | 3600s | ป้องกันการสมัครซ้ำ |
| **Create/Update** | 15-30 | 60s | จำกัดการสร้าง/แก้ไขข้อมูล |
| **Read (GET)** | 50-200 | 60s | อนุญาตให้อ่านข้อมูลบ่อย |
| **Delete** | 5-10 | 3600s | ป้องกันการลบข้อมูลโดยไม่ตั้งใจ |
| **PDF Generation** | 15 | 300s | การสร้าง PDF ใช้ทรัพยากรมาก |
| **Bulk Email** | 5 | 300s | ป้องกัน Spam |
| **OCR** | 10 | 60s | OCR ใช้ทรัพยากรมาก |
| **Batch Trigger** | 5 | 3600s | ป้องกันการรันงานซ้ำ |

---

## ✅ เอกสารฉบับสมบูรณ์

ข้าพเจ้าได้รวบรวมข้อมูลทั้งหมด 14 โมดูลเป็นเอกสารฉบับสมบูรณ์แล้ว ประกอบด้วย:

1. ✅ **บทนำและภาพรวมระบบ** – วัตถุประสงค์, Tech Stack, สถาปัตยกรรม
2. ✅ **โมดูลทั้ง 14** – โครงสร้าง, Database, Cache, Rate Limit, API
3. ✅ **สรุปฐานข้อมูลทั้งหมด** – 34 ตาราง Master, 44 ตาราง Transaction, 9 View
4. ✅ **สรุป API ทั้งหมด** – 200+ Endpoints พร้อม Rate Limit
5. ✅ **สรุป Cache Keys** – 40+ Redis Keys พร้อม TTL
6. ✅ **สรุป Rate Limit** – ตารางสรุปการจำกัดการเรียก API

--- 