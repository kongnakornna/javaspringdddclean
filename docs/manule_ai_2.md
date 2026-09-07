# 🧠 คู่มือการใช้ AI พัฒนา Java Spring Boot + DDD ฉบับสมบูรณ์
## คู่มือปฏิบัติสำหรับนักพัฒนาสมัยใหม่

---

## 📌 สารบัญ

1. [ภาพรวมของระบบและเครื่องมือ](#1-ภาพรวมของระบบและเครื่องมือ)
2. [การติดตั้งและเตรียมพร้อม](#2-การติดตั้งและเตรียมพร้อม)
3. [รายการ Skills ทั้งหมด (32 Skills)](#3-รายการ-skills-ทั้งหมด-32-skills)
4. [Workflow การพัฒนาด้วย AI](#4-workflow-การพัฒนาด้วย-ai)
   - 4.1 Normal Workflow
   - 4.2 Bug Fix Workflow
5. [Scenario-Based Prompt Templates](#5-scenario-based-prompt-templates)
6. [ตัวอย่างการใช้งานจริง (Case Study)](#6-ตัวอย่างการใช้งานจริง-case-study)
7. [Mandatory Gate Checklist & Quick Reference](#7-mandatory-gate-checklist--quick-reference)
8. [บทสรุปและเคล็ดลับ](#8-บทสรุปและเคล็ดลับ)

---

## 1. ภาพรวมของระบบและเครื่องมือ

คู่มือนี้รวบรวม **32 Skills** จากสองแหล่งหลักเพื่อให้คุณพัฒนาแอปพลิเคชัน Java Spring Boot ด้วยสถาปัตยกรรม DDD (Domain-Driven Design) อย่างมีประสิทธิภาพ โดยใช้ AI เป็นผู้ช่วยในทุกขั้นตอน

### 1.1 แหล่งที่มาของ Skills

| ชื่อ Repository | จำนวน Skills | จุดเด่น |
| :--- | :---: | :--- |
| **decebals/claude-code-java** | 18 Skills | ครอบคลุม Workflow, Code Quality, Architecture, Framework & Data |
| **Amplicode/spring-skills** | 14 Skills | เน้นการปฏิบัติ: สำรวจโปรเจกต์, วางแผน, สร้างโค้ด, ทดสอบ, ดีบัก |

### 1.2 หลักการทำงาน

- **Skills** คือคำสั่งสำเร็จรูปที่ AI เข้าใจและเรียกใช้งานอัตโนมัติ
- คุณสามารถเรียกใช้ผ่าน **Prompt** ที่ออกแบบตามสถานการณ์ (Scenario-Based)
- การทำงานจะเป็นไปตาม **Chain of Skills** — AI จะเลือกใช้ Skill ที่เหมาะสมตามลำดับ

---

## 2. การติดตั้งและเตรียมพร้อม

### 2.1 ติดตั้ง Amplicode Spring Agent Toolkit (Full Experience)

1. **ติดตั้ง IntelliJ Plugin**
   - เปิด IntelliJ IDEA → Settings → Plugins → Marketplace
   - ค้นหา "Amplicode" → Install

2. **เปิดโปรเจกต์ Spring Boot** และคลิก **"Настроить Spring Agent"** บน Welcome Screen

3. **ติดตั้ง Skills ทั้งสองชุด**
   ```bash
   # ติดตั้งทั้งหมดจาก decebals/claude-code-java
   npx skills add decebals/claude-code-java

   # ติดตั้งทั้งหมดจาก Amplicode/spring-skills (Global)
   npx skills add Amplicode/spring-skills -g
   ```

4. **Restart MCP Client**

### 2.2 ติดตั้งเฉพาะบาง Skill

```bash
npx skills add decebals/claude-code-java --skill java-code-review
npx skills add decebals/claude-code-java --skill git-commit
```

### 2.3 การใช้งานกับ Claude Code หรือ Cursor

```bash
cd ~/projects/your-java-project
claude
# Skills จะโหลดอัตโนมัติตาม Context
# หรือเรียกใช้โดยตรง: /git-commit, /java-code-review
```

---

## 3. รายการ Skills ทั้งหมด (32 Skills)

### 3.1 Skills จาก decebals/claude-code-java (18 Skills)

#### 🧩 Workflow Skills

| # | Skill | คำอธิบาย |
| :---: | :--- | :--- |
| 1 | `git-commit` | สร้าง Commit Message แบบ Conventional Commits |
| 2 | `changelog-generator` | สร้าง Changelog จาก Git Commits |
| 3 | `issue-triage` | จัดการและจัดลำดับความสำคัญของ GitHub Issues |

#### 🔍 Code Quality Skills

| # | Skill | คำอธิบาย |
| :---: | :--- | :--- |
| 4 | `java-code-review` | ตรวจสอบโค้ด Java (Null Safety, Exception, Concurrency) |
| 5 | `api-contract-review` | ตรวจสอบ REST API Contract (HTTP, Versioning, Backward Compatibility) |
| 6 | `concurrency-review` | ตรวจสอบ Thread Safety, Race Conditions, @Async, Virtual Threads |
| 7 | `performance-smell-detection` | ตรวจจับ Performance Smells (Streams, Boxing, Regex) |
| 8 | `test-quality` | แนวทางการเขียน JUnit 5 + AssertJ Tests |
| 9 | `maven-dependency-audit` | ตรวจสอบ Dependencies (เวอร์ชันล้าสมัย, ช่องโหว่, ความขัดแย้ง) |
| 10 | `security-audit` | ตรวจสอบความปลอดภัยตาม OWASP Top 10 |

#### 🏗️ Architecture & Design Skills

| # | Skill | คำอธิบาย |
| :---: | :--- | :--- |
| 11 | `architecture-review` | วิเคราะห์สถาปัตยกรรมระดับ Macro (packages, modules, layers) |
| 12 | `solid-principles` | ตรวจสอบ SOLID Principles พร้อมตัวอย่าง Java |
| 13 | `design-patterns` | Factory, Builder, Strategy, Observer, Decorator |
| 14 | `clean-code` | DRY, KISS, YAGNI, การตั้งชื่อ, การ Refactor |

#### ⚙️ Framework & Data Skills

| # | Skill | คำอธิบาย |
| :---: | :--- | :--- |
| 15 | `spring-boot-patterns` | Best Practices สำหรับ Spring Boot |
| 16 | `java-migration` | การอัปเกรด Java (8→11→17→21→25) |
| 17 | `jpa-patterns` | JPA/Hibernate Patterns (N+1, Lazy Loading, Transactions) |
| 18 | `logging-patterns` | Structured Logging (JSON), SLF4J, MDC |

---

### 3.2 Skills จาก Amplicode/spring-skills (14 Skills)

#### 🔍 Project Exploration & Planning

| # | Skill | คำอธิบาย |
| :---: | :--- | :--- |
| 19 | `spring-explore` | สำรวจโปรเจกต์ Spring Boot: Tech Stack, Modules, Entities, REST Endpoints |
| 20 | `spring-planning` | สร้าง Implementation Plan ใน `docs/plans/` |
| 21 | `amplicode-install` | ติดตั้ง Amplicode IntelliJ Plugin |

#### 🏗️ Code Generation Skills

| # | Skill | คำอธิบาย |
| :---: | :--- | :--- |
| 22 | `spring-data-jpa` | สร้าง/แก้ไข JPA Entities, Repositories, Projections |
| 23 | `spring-data-jdbc` | สร้าง/แก้ไข Spring Data JDBC Entities, Aggregates |
| 24 | `crud-rest-controller` | สร้าง CRUD REST Controller |
| 25 | `dto-creator` | สร้าง DTO Class |
| 26 | `mapper-creator` | สร้าง Mapper (MapStruct หรือ Custom) |
| 27 | `kafka-configuration` | ตั้งค่า Kafka Producer/Consumer |
| 28 | `connekt-script-writer` | เขียน `.connekt.kts` สำหรับ HTTP Automation |

#### 🧪 Testing & Quality Skills

| # | Skill | คำอธิบาย |
| :---: | :--- | :--- |
| 29 | `coverage` | วัด Test Coverage (Jacoco) |
| 30 | `mutation-testing` | Mutation Testing ด้วย PIT |

#### 🛠️ Development Tools

| # | Skill | คำอธิบาย |
| :---: | :--- | :--- |
| 31 | `java-debug` | ดีบักผ่าน IntelliJ Debugger |
| 32 | `codefmt` | จัดรูปแบบโค้ดตาม Project Code Style |

---

## 4. Workflow การพัฒนาด้วย AI

### 4.1 Normal Workflow (การพัฒนาฟีเจอร์ใหม่)

เปรียบเทียบการทำงานแบบเดิม (ไม่มี AI) และแบบใหม่ (มี AI):

| ขั้นตอน | แบบเดิม | แบบใหม่ (ใช้ AI Skills) |
| :---: | :--- | :--- |
| **Step 1** | รับ Story → ประชุม → จดโน้ต → ตีความเอง (อาจเข้าใจผิด) | ใช้ `spring-explore` + `issue-triage` เพื่อวิเคราะห์และแปลงเป็น Technical Task ทันที |
| **Step 2** | ถกเถียงทีม ไม่มีโครงสร้าง ใช้เวลานาน | ใช้ `spring-planning` สร้าง Implementation Plan พร้อม Subtask |
| **Step 3.1** | วางแผนในหัว ลุยเลย | ใช้ `tb-brainstorming` ทำความเข้าใจ Task ตั้งคำถามกับตัวเอง |
| **Step 3.2** | try & error แก้ซ้ำหลายรอบ | ใช้ `tb-writing-plans` วางแผนเป็นลายลักษณ์อักษร |
| **Step 3.3** | ลงมือทำโดยไม่มีแนวทาง | ใช้ `tb-executing-plans` ดำเนินการตามแผนทีละขั้น |
| **Step 3.4** | ตรวจสอบด้วยตัวเอง ไม่มี Checklist | ใช้ **Mandatory Gate** (ดูหัวข้อ 7) |
| **Step 3.5** | Proof of Work: บันทึกเอง | AI สร้างรายงานสรุปผลอัตโนมัติ |
| **Step 4** | QA พบ Bug → ย้อนกลับไปแก้ เสียเวลา | ใช้ Bug Fix Workflow แยก Path ชัดเจน |

---

#### 📋 Normal Workflow ขั้นตอนละเอียด

```
┌──────────────────────────────────────────────────────────────────────────┐
│  Step 1: รับ Story และวิเคราะห์                                          │
│  Prompt: "ใช้ spring-explore skill สำรวจโปรเจกต์ และใช้ issue-triage   │
│          skill วิเคราะห์ Story นี้: [วาง Story]"                         │
└──────────────────────────────────────────────────────────────────────────┘
                                   │
                                   ▼
┌──────────────────────────────────────────────────────────────────────────┐
│  Step 2: วางแผน                                                        │
│  Prompt: "ใช้ spring-planning skill สร้าง Implementation Plan           │
│          ตาม AC ที่ได้"                                                  │
└──────────────────────────────────────────────────────────────────────────┘
                                   │
                                   ▼
┌──────────────────────────────────────────────────────────────────────────┐
│  Step 3.1: Brainstorming (ทำความเข้าใจ Task)                           │
│  Prompt: "/tb-brainstorming [โจทย์/Requirement]"                        │
│  ● วิเคราะห์ Assumption ที่ผิดพลาด                                      │
│  ● ตั้งคำถามกับตัวเองก่อนลงมือ                                          │
└──────────────────────────────────────────────────────────────────────────┘
                                   │
                                   ▼
┌──────────────────────────────────────────────────────────────────────────┐
│  Step 3.2: Writing Plans                                               │
│  Prompt: "/tb-writing-plans"                                           │
│  ● วางแผน Implementation แบ่งเป็น Subtask ชัดเจน                       │
│  ● แสดงทางเลือก แนวทางตัดสินใจ                                         │
└──────────────────────────────────────────────────────────────────────────┘
                                   │
                                   ▼
┌──────────────────────────────────────────────────────────────────────────┐
│  Step 3.3: Executing Plans                                             │
│  Prompt: "/tb-executing-plans"                                         │
│  ● ลงมือ Implement ตามแผนทีละขั้นตอน                                   │
│  ● ไม่ข้ามหรือย่นขั้นตอนโดยพลการ                                        │
└──────────────────────────────────────────────────────────────────────────┘
                                   │
                                   ▼
┌──────────────────────────────────────────────────────────────────────────┐
│  Step 3.4: Mandatory Gate (ตรวจสอบบังคับ)                              │
│  ดู Checklist ในหัวข้อ 7                                                │
└──────────────────────────────────────────────────────────────────────────┘
                                   │
                                   ▼
┌──────────────────────────────────────────────────────────────────────────┐
│  Step 3.5: Proof of Work                                               │
│  AI สร้างสรุป: อะไรไปแล้วบ้าง + กระบวนการทดสอบผล                        │
└──────────────────────────────────────────────────────────────────────────┘
                                   │
                                   ▼
┌──────────────────────────────────────────────────────────────────────────┐
│  Step 4: ส่งให้ QA                                                     │
│  หากพบ Bug → ใช้ Bug Fix Workflow                                     │
└──────────────────────────────────────────────────────────────────────────┘
```

---

### 4.2 Bug Fix Workflow

เมื่อพบ Bug ให้แยกเป็น 2 Path ตามความซับซ้อน:

```
🔍 Step 1 (เริ่มเสมอ): tb-investigate-error
   Prompt: "/tb-investigate-error" + [Stack Trace หรืออาการ]
   ● วิเคราะห์ Root Cause (ใช้เทคนิค 5 Whys)
   ● จัดลำดับสาเหตุ และออก Report
   ● ไม่วางแผนแก้จนกว่าจะเข้าใจสาเหตุชัดเจน
                              │
              ┌───────────────┴───────────────┐
              │                               │
              ▼                               ▼
   ┌──────────────────────┐       ┌──────────────────────┐
   │  Path A: แก้หลายจุด   │       │  Path B: แก้จุดเดียว │
   │  (ซับซ้อน)           │       │  (ตรงไปตรงมา)       │
   └──────────────────────┘       └──────────────────────┘
              │                               │
              ▼                               ▼
   ┌──────────────────────┐       ┌──────────────────────┐
   │  Step 2:             │       │  Step 2:             │
   │  /tb-writing-plans   │       │  /tb-test-driven-    │
   │  วางแผนแก้หลายจุด    │       │  development         │
   │                      │       │  เขียน Test ก่อน      │
   └──────────────────────┘       └──────────────────────┘
              │                               │
              ▼                               ▼
   ┌──────────────────────┐       ┌──────────────────────┐
   │  Step 3:             │       │  Step 3:             │
   │  /tb-executing-plans │       │  เขียนโค้ดให้ Test   │
   │  ทำตามแผนทีละขั้น    │       │  ผ่าน                │
   └──────────────────────┘       └──────────────────────┘
              │                               │
              └───────────────┬───────────────┘
                              │
                              ▼
            ┌─────────────────────────────────┐
            │  🛡 MANDATORY GATE              │
            │  tb-scrutinize                  │
            │  ● ตรวจสอบโค้ด Logic            │
            │  ● ตรวจสอบ Side Effects        │
            │  ● ต้องผ่านทุกครั้ง              │
            └─────────────────────────────────┘
```

---

## 5. Scenario-Based Prompt Templates

ใช้คัดลอกและวางตามสถานการณ์จริง แทนที่ `[ข้อความในวงเล็บ]`

---

### 🚀 Scenario 1: เริ่มต้นฟีเจอร์ใหม่ / วางแผน

| องค์ประกอบ | รายละเอียด |
| :--- | :--- |
| **เป้าหมาย** | สำรวจโปรเจกต์, แตก AC (Gherkin), วางแผน Implementation |
| **Skills ที่ใช้** | `spring-explore` + `issue-triage` + `spring-planning` (อัตโนมัติ) |

**📋 Prompt:**
```
ใช้ spring-explore skill เพื่อสำรวจโปรเจกต์ปัจจุบันและทำความเข้าใจ Tech Stack
จากนั้น帮我แปลง Story นี้เป็น Gherkin Acceptance Criteria (Given-When-Then)
และใช้ spring-planning skill สร้าง Implementation Plan บันทึกใน docs/plans/:

**Story:** [วางข้อความ Story หรือ Task จาก PO ที่นี่]
**ขอบเขต:** [ระบุขอบเขต เช่น เฉพาะ User Module / ส่งผลต่อ Order ด้วย]
**ข้อจำกัด:** [เช่น ต้องรองรับ 1000 TPS หรือ ต้อง Compatible กับ DB Version เดิม]
```

---

### 🏗️ Scenario 2: สร้าง CRUD + DTO + Mapper (DDD)

| องค์ประกอบ | รายละเอียด |
| :--- | :--- |
| **เป้าหมาย** | สร้างชุดโค้ด CRUD ตาม DDD + Clean Architecture |
| **Skills ที่ใช้** | `spring-data-jpa` + `dto-creator` + `mapper-creator` + `crud-rest-controller` |

**📋 Prompt:**
```
สร้าง CRUD สำหรับ Entity ชื่อ [EntityName] (เช่น Product) โดยใช้ DDD Pattern พร้อมกันทุกชั้น:
- Entity: Fields [เช่น id, name, price, createdAt] และความสัมพันธ์ [เช่น @OneToMany กับ OrderItem]
- DTO: ใช้ [Java Record / Lombok] สร้าง RequestDTO และ ResponseDTO
- Mapper: ใช้ [MapStruct] สร้าง Mapper
- Controller: สร้าง REST CRUD (GET, POST, PUT, DELETE) พร้อม Pagination, Exception Handling และ Swagger Annotations
- Validation: เพิ่ม @Valid และข้อความ Error เป็นภาษาไทย
```

---

### 🧪 Scenario 3: เขียน Unit Test + ตรวจสอบ Coverage

| องค์ประกอบ | รายละเอียด |
| :--- | :--- |
| **เป้าหมาย** | สร้าง Unit Test (JUnit 5 + AssertJ) และวัดคุณภาพ Test |
| **Skills ที่ใช้** | `test-quality` + `coverage` + `mutation-testing` |

**📋 Prompt:**
```
ใช้ test-quality skill ช่วยเขียน Unit Test สำหรับคลาส [ชื่อคลาสหลัก]
โดยครอบคลุมทั้ง Happy Path และ Edge Cases (เช่น Data Not Found, Duplicate Key)
จากนั้นใช้ coverage skill วัดว่า Coverage ถึงเกณฑ์ 80% หรือไม่
และถ้ายัง ให้ใช้ mutation-testing skill ตรวจสอบว่ามี Mutation ใดรอด (Survived) บ้าง
```

---

### 🔍 Scenario 4: Code Review + Security + Performance (Pre-Merge)

| องค์ประกอบ | รายละเอียด |
| :--- | :--- |
| **เป้าหมาย** | ตรวจสอบโค้ดรอบด้านก่อน Merge |
| **Skills ที่ใช้** | `codefmt` + `java-code-review` + `security-audit` + `performance-smell-detection` + `concurrency-review` + `api-contract-review` |

**📋 Prompt:**
```
ก่อน Merge โค้ดในแพ็คเกจ [ชื่อแพ็คเกจ] หรือไฟล์ [ชื่อไฟล์] ช่วยทำ Full Quality Check:
1. ใช้ codefmt skill จัดรูปแบบโค้ด
2. ใช้ java-code-review skill ตรวจสอบ Null Safety, Exception Handling, Collections
3. ใช้ security-audit skill ตรวจสอบ OWASP Top 10 (SQL Injection, Hardcoded Secret)
4. ใช้ performance-smell-detection skill ตรวจจับ Regex compile ใน loop หรือ String concat
5. ใช้ concurrency-review skill ตรวจสอบ @Async หรือ shared state
6. ใช้ api-contract-review skill ตรวจสอบ Controller ว่าใช้ HTTP Verb และ Status Code ถูกต้อง
```

---

### 🐛 Scenario 5: แก้ Bug (TDD)

| องค์ประกอบ | รายละเอียด |
| :--- | :--- |
| **เป้าหมาย** | วิเคราะห์ Root Cause, เขียน Test ที่ล้มเหลวก่อน (Red), แล้วแก้โค้ดให้ผ่าน (Green) |
| **Skills ที่ใช้** | (Prompt-Based Analysis) + `test-quality` + `java-debug` |

**📋 Prompt:**
```
ช่วยแก้ Bug นี้โดยใช้ TDD (Test-Driven Development):

**Error Log / อาการ:** [วาง Stack Trace หรือข้อความ Error ที่เจอ]
**เกิดที่ Environment:** [Local / Dev / Staging / Production]
**ผู้ใช้งานที่พบ:** [ID User หรือ Role]

ขั้นตอน:
1. ใช้เทคนิค 5 Whys วิเคราะห์ Root Cause
2. ใช้ test-quality skill เขียน JUnit Test ที่ต้องล้มเหลว (Red) ก่อน
3. ช่วยแก้โค้ดให้ Test ผ่าน (Green)
4. ใช้ java-debug skill (ถ้าจำเป็น) เพื่อตั้ง Breakpoint ตรวจสอบค่าตัวแปร
```

---

### 📦 Scenario 6: Commit + สร้าง Changelog

| องค์ประกอบ | รายละเอียด |
| :--- | :--- |
| **เป้าหมาย** | สร้าง Commit Message ตาม Standard และอัปเดต Changelog |
| **Skills ที่ใช้** | `git-commit` + `changelog-generator` |

**📋 Prompt:**
```
ใช้ git-commit skill สร้าง Commit Message สำหรับการเปลี่ยนแปลงที่เกี่ยวกับ [สรุปสั้นๆ]
และใช้ changelog-generator skill อัปเดต CHANGELOG.md สำหรับ Version [เวอร์ชันใหม่]

ประเภทย่อย (Scope): [เช่น user, order, security]
Breaking Change: [Yes / No]
Ticket / Issue: [เช่น JIRA-1234]
```

---

### ⚙️ Scenario 7: ตั้งค่า Kafka / Event-Driven

| องค์ประกอบ | รายละเอียด |
| :--- | :--- |
| **เป้าหมาย** | สร้าง Configuration และ Producer/Consumer Code |
| **Skills ที่ใช้** | `kafka-configuration` |

**📋 Prompt:**
```
ใช้ kafka-configuration skill ตั้งค่า Kafka สำหรับโปรเจกต์นี้:
- Topic: [ชื่อ Topic เช่น order-events]
- Type: [Producer / Consumer / Both]
- Key/Value Serde: [String / Json / Avro]
- Config Method: [ใช้ application.yml / ใช้ @Configuration Class]
```

---

## 6. ตัวอย่างการใช้งานจริง (Case Study)

### 📌 โจทย์: สร้าง API POST /employee สำหรับเพิ่มพนักงาน

**Requirement:**
- `id` autogenerate เป็น UUID
- `full_name` (required)
- `email` (required, unique)
- `created_at` = เวลาสร้าง
- `updated_at` = เวลาสร้าง (ตอนสร้าง)

---

### ✅ การใช้งานด้วย AI (ใช้ Copilot + Skills)

#### Step 1: ใช้ `/tb-brainstorming`

**Prompt:**
```
/tb-brainstorming

GET http://localhost:8080/employees ดึงข้อมูลพนักงานทั้งหมด
Table Employee
Field: id string (UUID), full_name string, email string, created_at datetime, updated_at datetime

Business logic: สร้าง API POST http://localhost:8080/employee สำหรับเพิ่มข้อมูลพนักงาน

รายละเอียด:
- id autogenerate UUID
- full_name require
- email require และห้ามซ้ำ
- created_at = เวลาสร้าง
- updated_at = เวลาสร้าง

โครงสร้าง Folder:
[วางโครงสร้างตามตัวอย่าง]

หลักการทำงาน, Affected ส่วนไหน, แสดงรายการกระบวนการ, ข้อกำหนด/ข้อห้าม
- ไม่แก้โครงสร้างเดิม
- ไม่แก้ Function เดิมที่ใช้งานร่วมกัน
- ข้อควรระวัง/ข้อดี/ข้อเสีย

ออกแบบ workflow วาดรูป dataflow ลักษณะ flowchart เพื่ออธิบายกระบวนการ
```

**AI จะตอบกลับมาด้วย:**
- การวิเคราะห์ Assumption และคำถามที่ต้องถามตัวเอง
- Data Flow Diagram (Flowchart)
- รายการ Affected Components
- ข้อดี/ข้อเสีย/ข้อควรระวัง
- ถามว่าต้องการปรับ Flow ตรงไหนไหม

---

#### Step 2: ใช้ `/tb-writing-plans`

**Prompt:**
```
/tb-writing-plans

หลังจากได้ Data Flow แล้ว ให้ช่วยวางแผน Implementation โดยแบ่งเป็น Subtask
และแสดงทางเลือกในการออกแบบ พร้อมข้อดี/ข้อเสีย
```

**AI จะสร้าง:**
- แผนงานที่มี Subtask แยกตาม Layer (Handler, Service, Repository)
- ทางเลือกการออกแบบ (เช่น ใช้ Map หรือ DB, ใช้ UUID v4 หรือ v7)
- คำแนะนำในการตัดสินใจ

---

#### Step 3: ใช้ `/tb-executing-plans`

**Prompt:**
```
/tb-executing-plans

เริ่ม Implement ตามแผนที่วางไว้
```

**AI จะลงมือเขียนโค้ด:**
- `handler.go` (Gin Handler)
- `service.go` (Business Logic)
- `repository.go` (GORM Queries)
- DTO Structs
- ไฟล์ Test (ถ้ารวมในแผน)

---

#### Step 4: Mandatory Gate

**Prompt:**
```
ใช้ java-code-review skill ตรวจสอบโค้ดที่สร้าง
ใช้ security-audit skill ตรวจสอบ SQL Injection และ Hardcoded Secret
ใช้ test-quality skill ช่วยเขียน Unit Test เพิ่มเติม (ถ้ายังไม่ครบ)
```

---

#### Step 5: Proof of Work

**Prompt:**
```
สรุปผลการดำเนินการ: อะไรไปบ้างแล้ว และบอกกระบวนการทดสอบผล
```

**AI จะตอบ:**
- รายการไฟล์ที่สร้าง/แก้ไข
- ผลลัพธ์การทดสอบ (ผ่าน/ไม่ผ่าน)
- Coverage Report
- คำแนะนำเพิ่มเติม

---

## 7. Mandatory Gate Checklist & Quick Reference

### ✅ Mandatory Gate Checklist (ก่อน Merge ทุกครั้ง)

- [ ] **`codefmt`** — โค้ดจัดรูปแบบเรียบร้อย
- [ ] **`java-code-review`** — ไม่พบ Critical/High Issue
- [ ] **`security-audit`** — ไม่พบ Vulnerability
- [ ] **`coverage`** — Coverage ≥ 80%
- [ ] **`mutation-testing`** — Mutation Score ≥ 70%
- [ ] **`mvn clean install`** — Compile ผ่าน
- [ ] **`mvn test`** — Tests ผ่านทั้งหมด

---

### 🏷️ Quick Reference: เมื่อไหร่ควรใช้ Skill ไหน?

| สถานการณ์ | Skill ที่แนะนำ |
| :--- | :--- |
| เริ่มโปรเจกต์ใหม่ | `spring-explore` → `spring-planning` |
| สร้าง Entity | `spring-data-jpa` หรือ `spring-data-jdbc` |
| สร้าง DTO | `dto-creator` |
| สร้าง Mapper | `mapper-creator` |
| สร้าง REST API | `crud-rest-controller` |
| ตั้งค่า Kafka | `kafka-configuration` |
| เขียน Test | `test-quality` → `coverage` → `mutation-testing` |
| ตรวจสอบโค้ด | `java-code-review` + `security-audit` + `performance-smell-detection` |
| ตรวจสอบ API | `api-contract-review` |
| ตรวจสอบ Architecture | `architecture-review` + `solid-principles` |
| Refactor | `clean-code` + `design-patterns` |
| Debug | `java-debug` |
| Commit | `git-commit` |
| Release | `changelog-generator` |

---

### 📋 Cheat Sheet สำหรับ Daily Use

| ถ้าคุณต้องการ... | ใช้ Prompt สั้นๆ นี้ |
| :--- | :--- |
| **เริ่มงานใหม่** | `ใช้ spring-explore และ spring-planning เพื่อเริ่มฟีเจอร์: [ชื่อ]` |
| **สร้าง CRUD** | `ใช้ dto-creator, mapper-creator, spring-data-jpa และ crud-rest-controller สร้าง CRUD สำหรับ [Entity]` |
| **เขียน Test** | `ใช้ test-quality เขียน Unit Test ให้ [คลาส]` |
| **ตรวจสอบโค้ด** | `ใช้ java-code-review, security-audit, performance-smell-detection ตรวจสอบ [ไฟล์/แพ็คเกจ]` |
| **แก้ Bug** | `ใช้ 5 Whys วิเคราะห์ Error นี้ แล้วใช้ test-quality เขียน Test ก่อนแก้: [Error Log]` |
| **Commit** | `ใช้ git-commit สร้าง Commit Message สำหรับ [งานที่ทำ]` |

---

## 8. บทสรุปและเคล็ดลับ

### 💡 เคล็ดลับการใช้งานให้เกิดประโยชน์สูงสุด

1. **ใช้ Scenario-Based Prompts** — คัดลอกและปรับตามสถานการณ์ ไม่ต้องจำคำสั่งทั้งหมด
2. **เรียกใช้ Mandatory Gate ทุกครั้ง** — ก่อน Merge ทุกครั้งเพื่อลด Bug ใน Production
3. **รวมหลาย Scenario ใน Prompt เดียว** — เช่น:
   > `ใช้ spring-explore สำรวจโปรเจกต์, ใช้ dto-creator และ crud-rest-controller สร้าง API, และใช้ java-code-review ตรวจสอบโค้ดที่สร้างให้เสร็จภายในรอบเดียว`
4. **ใช้ Bug Fix Workflow อย่างเคร่งครัด** — เริ่มด้วย `tb-investigate-error` เสมอ อย่าข้าม
5. **ปรับแต่ง Prompt ตามบริบท** — เพิ่มรายละเอียดเฉพาะของโปรเจกต์ เช่น ชื่อแพ็คเกจ, Dependencies, ข้อจำกัด

---

### 📚 แหล่งข้อมูลเพิ่มเติม

- [Agent Skills Specification](https://agentskills.io/specification)
- [decebals/claude-code-java](https://github.com/decebals/claude-code-java)
- [Amplicode/spring-skills](https://github.com/Amplicode/spring-skills)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)

---

**✨ สรุป:** การพัฒนา Java Spring Boot ด้วย DDD และ AI Skills จะช่วยลดเวลาในการคิดและเขียนโค้ด ลดความผิดพลาด และเพิ่มคุณภาพของซอฟต์แวร์ ด้วยกระบวนการที่มีโครงสร้างและตรวจสอบได้ทุกขั้นตอน

---

> **Version:** 1.0  
> **ปรับปรุงล่าสุด:** 2026-09-07