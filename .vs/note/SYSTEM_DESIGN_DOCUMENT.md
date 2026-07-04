# เอกสารออกแบบระบบครบวงจร (Complete System Design Document)
## ระบบจัดการซ่อมรถยนต์ / Vehicle Repair Management System

**พร้อม Template Code, Data Flow, Workflow, และ Template รายงาน**

> **Tech Stack:** Java 17+ / Spring Boot 3.2 | JPA (Hibernate) | PostgreSQL | Redis (Cache) | Kafka (Event Bus) | ELK (Elasticsearch, Logstash, Kibana) | Grafana | n8n | Docker Compose | Jenkins (CI/CD) | AWS EC2/S3 | Robot Framework

---

## สารบัญ (Table of Contents)

1. [สถาปัตยกรรมระบบโดยรวม (System Architecture)](#1-สถาปัตยกรรมระบบโดยรวม-system-architecture)
2. [โครงสร้างโปรเจกต์เต็มรูปแบบ (Complete Project Folder Structure)](#2-โครงสร้างโปรเจกต์เต็มรูปแบบ-complete-project-folder-structure)
3. [โมดูลหลักของระบบ (Core Modules)](#3-โมดูลหลักของระบบ-core-modules)
   - 3.1 [Authentication & Permission](#31-authentication--permission)
   - 3.2 [Job Card Management](#32-job-card-management)
   - 3.3 [Customer Management](#33-customer-management)
   - 3.4 [Quotation Module](#34-quotation-module)
   - 3.5 [Purchase Order Module](#35-purchase-order-module)
   - 3.6 [Invoice Module](#36-invoice-module)
   - 3.7 [Credit/Debit Note Module](#37-creditdebit-note-module)
   - 3.8 [Delivery Sheet Module](#38-delivery-sheet-module)
   - 3.9 [Part Picking Module](#39-part-picking-module)
   - 3.10 [Inventory Management](#310-inventory-management)
   - 3.11 [Payment Management](#311-payment-management)
   - 3.12 [Booking Management](#312-booking-management)
   - 3.13 [Staff Management](#313-staff-management)
   - 3.14 [Company & Supplier](#314-company--supplier)
   - 3.15 [Dashboard & Reports](#315-dashboard--reports)
   - 3.16 [Email Service](#316-email-service)
   - 3.17 [Document Management](#317-document-management)
   - 3.18 [OCR (Image to Text)](#318-ocr-image-to-text)
   - 3.19 [Batch Jobs](#319-batch-jobs)
   - 3.20 [Multi-Language (i18n)](#320-multi-language-i18n)
4. [โมดูลขยายใหม่ (New Extended Modules)](#4-โมดูลขยายใหม่-new-extended-modules)
   - 4.1 [Dashboard Center System](#41-dashboard-center-system)
   - 4.2 [GPS Tracking System](#42-gps-tracking-system)
   - 4.3 [IoT System (MQTT, InfluxDB, Device Management)](#43-iot-system-mqtt-influxdb-device-management)
   - 4.4 [Kafka Queue Processing System](#44-kafka-queue-processing-system)
5. [Web Order System (WOS)](#5-web-order-system-wos)
6. [Module Dependency Map](#6-module-dependency-map)
7. [Database Design Overview](#7-database-design-overview)
8. [API Design Standards](#8-api-design-standards)
9. [Security & Authentication](#9-security--authentication)
10. [Monitoring & Observability](#10-monitoring--observability)
11. [Deployment Architecture](#11-deployment-architecture)
12. [File Count Summary](#12-file-count-summary)
13. [JasperReport Templates](#13-jasperreport-templates)

---

## 1. สถาปัตยกรรมระบบโดยรวม (System Architecture)

### [TH] 1.1 Overall System Architecture
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

### 1.2 Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                            EXTERNAL SYSTEMS                                  │
│  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────────────┐  │
│  │  Mobile  │ │   Web    │ │  Third   │ │   IoT    │ │   Email/SMS      │  │
│  │   App    │ │  Portal  │ │  Party   │ │ Devices  │ │   Gateway        │  │
│  └────┬─────┘ └────┬─────┘ └────┬─────┘ └────┬─────┘ └────────┬─────────┘  │
└───────┼─────────────┼─────────────┼─────────────┼──────────────┼───────────┘
        │             │             │             │              │
        ▼             ▼             ▼             ▼              ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                           API GATEWAY / LOAD BALANCER                        │
│                              (Spring Cloud Gateway / NGINX)                  │
└─────────────────────────────────┬───────────────────────────────────────────┘
                                  │
                                  ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                         SPRING BOOT APPLICATION                              │
│  ┌─────────────────────────────────────────────────────────────────────┐    │
│  │                        CONTROLLER LAYER                              │    │
│  │  REST Controllers │ WebSocket │ GraphQL │ Admin API │ Health Check  │    │
│  └─────────────────────────────────┬────────────────────────────────────┘    │
│                                    │                                         │
│  ┌─────────────────────────────────▼────────────────────────────────────┐    │
│  │                        SERVICE LAYER                                  │    │
│  │  Business Logic │ Transaction Mgmt │ Cache │ Validation │ Events     │    │
│  └─────────────────────────────────┬────────────────────────────────────┘    │
│                                    │                                         │
│  ┌─────────────────────────────────▼────────────────────────────────────┐    │
│  │                       REPOSITORY LAYER                                │    │
│  │  Spring Data JPA │ Custom Queries │ Specifications │ Projections     │    │
│  └─────────────────────────────────┬────────────────────────────────────┘    │
└──────────────────────────────────┼──────────────────────────────────────────┘
                                   │
               ┌───────────────────┼───────────────────┐
               ▼                   ▼                   ▼
      ┌──────────────┐    ┌──────────────┐    ┌──────────────┐
      │  PostgreSQL  │    │    Redis     │    │    Kafka     │
      │  (Primary DB)│    │   (Cache)    │    │ (Event Bus)  │
      └──────────────┘    └──────────────┘    └──────┬───────┘
                                                      │
          ┌───────────────────────────────────────────┼───────────────────┐
          ▼                                           ▼                   ▼
   ┌─────────────┐                            ┌─────────────┐      ┌─────────────┐
   │ Elasticsearch│                            │  InfluxDB   │      │   Grafana   │
   │   (ELK)     │                            │  (IoT Data) │      │  (Metrics)  │
   └─────────────┘                            └─────────────┘      └─────────────┘
```

---

## 2. โครงสร้างโปรเจกต์เต็มรูปแบบ (Complete Project Folder Structure)

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── densoshop/
│   │           ├── DensoShopApplication.java
│   │           │
│   │           ├── config/                          # Configuration Classes
│   │           │   ├── WebConfig.java               # CORS, Interceptor, Static Resources
│   │           │   ├── SecurityConfig.java          # Spring Security, JWT
│   │           │   ├── DatabaseConfig.java          # DataSource, JPA, Hibernate
│   │           │   ├── RedisConfig.java             # Redis Cache Config
│   │           │   ├── KafkaConfig.java             # Kafka Producer/Consumer Config
│   │           │   ├── ElasticsearchConfig.java     # ELK Config
│   │           │   ├── InfluxDBConfig.java          # InfluxDB Config (IoT)
│   │           │   ├── MQTTConfig.java              # MQTT Broker Config
│   │           │   ├── SchedulerConfig.java         # @EnableScheduling
│   │           │   ├── AsyncConfig.java             # @EnableAsync Thread Pool
│   │           │   ├── JasperReportsConfig.java     # JasperReports Config
│   │           │   ├── SwaggerConfig.java           # OpenAPI Documentation