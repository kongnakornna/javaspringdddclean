# 🧠 คู่มือการใช้ AI พัฒนา Java Spring Boot + DDD ฉบับสมบูรณ์
## (Complete Skills Reference & Workflow Template)

---

## 📌 สารบัญ

1. [ภาพรวมของสอง Repository](#1-ภาพรวมของสอง-repository)
2. [รายการ Skills ทั้งหมด (32 Skills)](#2-รายการ-skills-ทั้งหมด-32-skills)
3. [คำอธิบายแต่ละ Skill แบบละเอียด](#3-คําอธิบายแต่ละ-skill-แบบละเอียด)
4. [Workflow Template ครบวงจร](#4-workflow-template-ครบวงจร)
5. [Quick Reference Cards](#5-quick-reference-cards)
6. [การติดตั้งและใช้งาน](#6-การติดตั้งและใช้งาน)

---

## 1. ภาพรวมของสอง Repository

### 1.1 decebals/claude-code-java

Repository นี้เป็น **โครงสร้างพื้นฐานสำหรับการพัฒนา Java ด้วย AI** ประกอบด้วย **18 Skills** ที่ครอบคลุม:

| หมวดหมู่ | จำนวน Skills |
|---------|-------------|
| Workflow | 3 |
| Code Quality | 8 |
| Architecture & Design | 4 |
| Framework & Data | 3 |

Skills ทั้งหมดผ่านการทดสอบ Routing (20/20) และเป็นไปตาม [Agent Skills Specification](https://agentskills.io/specification)

### 1.2 Amplicode/spring-skills

Repository นี้คือ **Spring Agent Toolkit** — ชุด Skills เฉพาะสำหรับการพัฒนา Spring Boot ประกอบด้วย **14 Skills** ที่เน้นงานปฏิบัติจริง:

- สำรวจโปรเจกต์ Spring Boot (`spring-explore`)
- วางแผนการพัฒนา (`spring-planning`)
- สร้างและแก้ไข JPA Entities, DTOs, Mappers, REST Controllers
- ดีบักผ่าน IntelliJ (`java-debug`)
- ตรวจสอบ Test Coverage และ Mutation Testing

---

## 2. รายการ Skills ทั้งหมด (32 Skills)

### 2.1 Skills จาก decebals/claude-code-java (18 Skills)

#### 🧩 Workflow Skills

| # | Skill | คำอธิบายสั้น |
|---|-------|------------|
| 1 | `git-commit` | สร้าง Commit Message แบบ Conventional Commits |
| 2 | `changelog-generator` | สร้าง Changelog จาก Git Commits |
| 3 | `issue-triage` | จัดการและจัดลำดับความสำคัญของ GitHub Issues |

#### 🔍 Code Quality Skills

| # | Skill | คำอธิบายสั้น |
|---|-------|------------|
| 4 | `java-code-review` | ตรวจสอบโค้ด Java อย่างเป็นระบบ (Null Safety, Exception Handling, Concurrency) |
| 5 | `api-contract-review` | ตรวจสอบ REST API Contract (HTTP semantics, versioning, backward compatibility) |
| 6 | `concurrency-review` | ตรวจสอบ Thread Safety, Race Conditions, @Async, Virtual Threads |
| 7 | `performance-smell-detection` | ตรวจจับ Performance Smells (Streams, Boxing, Regex) |
| 8 | `test-quality` | แนวทางการเขียน JUnit 5 + AssertJ Tests |
| 9 | `maven-dependency-audit` | ตรวจสอบ Dependencies (เวอร์ชันล้าสมัย, ช่องโหว่, ความขัดแย้ง) |
| 10 | `security-audit` | ตรวจสอบความปลอดภัยตาม OWASP Top 10 |

#### 🏗️ Architecture & Design Skills

| # | Skill | คำอธิบายสั้น |
|---|-------|------------|
| 11 | `architecture-review` | วิเคราะห์สถาปัตยกรรมระดับ Macro (packages, modules, layers) |
| 12 | `solid-principles` | ตรวจสอบ SOLID Principles พร้อมตัวอย่าง Java |
| 13 | `design-patterns` | Factory, Builder, Strategy, Observer, Decorator |
| 14 | `clean-code` | DRY, KISS, YAGNI, การตั้งชื่อ, การ Refactor |

#### ⚙️ Framework & Data Skills

| # | Skill | คำอธิบายสั้น |
|---|-------|------------|
| 15 | `spring-boot-patterns` | Best Practices สำหรับ Spring Boot |
| 16 | `java-migration` | การอัปเกรด Java (8→11→17→21→25) |
| 17 | `jpa-patterns` | JPA/Hibernate Patterns (N+1, Lazy Loading, Transactions) |
| 18 | `logging-patterns` | Structured Logging (JSON), SLF4J, MDC |

---

### 2.2 Skills จาก Amplicode/spring-skills (14 Skills)

#### 🔍 Project Exploration & Planning

| # | Skill | คำอธิบายสั้น |
|---|-------|------------|
| 19 | `spring-explore` | สำรวจโปรเจกต์ Spring Boot: Tech Stack, Modules, Entities, REST Endpoints |
| 20 | `spring-planning` | สร้าง Implementation Plan ใน `docs/plans/` |
| 21 | `amplicode-install` | ติดตั้ง Amplicode IntelliJ Plugin |

#### 🏗️ Code Generation Skills

| # | Skill | คำอธิบายสั้น |
|---|-------|------------|
| 22 | `spring-data-jpa` | สร้าง/แก้ไข JPA Entities, Repositories, Projections |
| 23 | `spring-data-jdbc` | สร้าง/แก้ไข Spring Data JDBC Entities, Aggregates |
| 24 | `crud-rest-controller` | สร้าง CRUD REST Controller |
| 25 | `dto-creator` | สร้าง DTO Class |
| 26 | `mapper-creator` | สร้าง Mapper (MapStruct หรือ Custom) |
| 27 | `kafka-configuration` | ตั้งค่า Kafka Producer/Consumer |
| 28 | `connekt-script-writer` | เขียน `.connekt.kts` สำหรับ HTTP Automation |

#### 🧪 Testing & Quality Skills

| # | Skill | คำอธิบายสั้น |
|---|-------|------------|
| 29 | `coverage` | วัด Test Coverage (Jacoco) |
| 30 | `mutation-testing` | Mutation Testing ด้วย PIT |

#### 🛠️ Development Tools

| # | Skill | คำอธิบายสั้น |
|---|-------|------------|
| 31 | `java-debug` | ดีบักผ่าน IntelliJ Debugger |
| 32 | `codefmt` | จัดรูปแบบโค้ดตาม Project Code Style |

---

## 3. คำอธิบายแต่ละ Skill แบบละเอียด

### 3.1 Workflow Skills (decebals)

#### `git-commit` — สร้าง Commit Message
- **รูปแบบ:** `(scope): `
- **ประเภท:** `feat`, `fix`, `refactor`, `test`, `docs`, `perf`, `build`, `chore`
- **ตัวอย่าง:** `fix(plugin-loader): prevent NPE when plugin directory is missing`

#### `changelog-generator` — สร้าง Changelog
- รองรับ Semantic Versioning (x.y.z), Two-component (x.y), CalVer (YYYY.MM)
- ตรวจจับ Versioning Style จาก `CLAUDE.md`, Git Tags, หรือ `CHANGELOG.md`

#### `issue-triage` — จัดการ Issues
- จำแนกประเภท: Bug, Feature Request, Question, Duplicate, Invalid
- จัดลำดับความสำคัญ: Critical (P0), High (P1), Medium (P2), Low (P3)

---

### 3.2 Code Quality Skills (decebals)

#### `java-code-review` — ตรวจสอบโค้ด
**หัวข้อที่ตรวจสอบ:**
- **Null Safety:** หลีกเลี่ยง NPE, ใช้ `@Nullable`/`@NonNull`, `Optional`
- **Exception Handling:** ไม่ swallow exceptions, ใช้ specific exception types
- **Collections & Streams:** หลีกเลี่ยง `ConcurrentModificationException`, ใช้ `removeIf`

#### `api-contract-review` — ตรวจสอบ REST API
**ประเด็นที่ตรวจสอบ:**
- HTTP Verb ที่ถูกต้อง (GET, POST, PUT, PATCH, DELETE)
- API Versioning (`/api/v1/users`)
- Response Consistency, Error Handling

#### `performance-smell-detection` — ตรวจจับ Performance Smells
- **High Severity:** Regex compile in loop, Unbounded collection
- **Medium:** String concat in loop, Stream in tight loop
- **Low:** Missing collection capacity

#### `test-quality` — แนวทางการเขียน Test
- **Framework:** JUnit 5 + AssertJ
- **Structure:** Arrange-Act-Assert (AAA)
- **Naming:** `should_expectedBehavior_when_condition`

#### `maven-dependency-audit` — ตรวจสอบ Dependencies
- ตรวจสอบ Outdated Dependencies: `mvn versions:display-dependency-updates`
- ตรวจสอบ Conflicts: `mvn dependency:tree`
- ตรวจสอบ Vulnerabilities: OWASP Dependency-Check

#### `security-audit` — ตรวจสอบความปลอดภัย
**OWASP Top 10 ที่ครอบคลุม:**
- A01: Broken Access Control, A02: Cryptographic Failures, A03: Injection
- A04: Insecure Design, A05: Security Misconfiguration, A06: Vulnerable Components
- A07: Authentication Failures, A08: Data Integrity Failures, A09: Logging Failures, A10: SSRF

---

### 3.3 Architecture & Design Skills (decebals)

#### `architecture-review` — วิเคราะห์สถาปัตยกรรม
**Package Organization Strategies:**
- Package-by-Layer (Traditional): `controller/`, `service/`, `repository/`, `model/`
- Package-by-Feature (Recommended): `user/`, `order/`, `product/`
- Hexagonal/Clean Architecture: `domain/`, `application/`, `adapter/`

#### `solid-principles` — ตรวจสอบ SOLID
- **S**ingle Responsibility: หนึ่งคลาส = หนึ่งเหตุผลในการเปลี่ยนแปลง
- **O**pen/Closed: เปิดสำหรับ Extension, ปิดสำหรับ Modification
- **L**iskov Substitution: Subtypes ต้องใช้แทน Base Types ได้
- **I**nterface Segregation: หลาย Interface เฉพาะทาง > หนึ่ง Interface ใหญ่
- **D**ependency Inversion: ขึ้นอยู่กับ Abstraction ไม่ใช่ Concretion

#### `design-patterns` — Design Patterns
- **Creational:** Builder, Factory Method, Singleton
- **Structural:** Decorator, Adapter
- **Behavioral:** Strategy, Observer, Template Method

#### `clean-code` — Clean Code Principles
- **DRY:** Don't Repeat Yourself
- **KISS:** Keep It Simple, Stupid
- **YAGNI:** You Aren't Gonna Need It

---

### 3.4 Framework & Data Skills (decebals)

#### `spring-boot-patterns` — Spring Boot Best Practices
- **Project Structure:** `config/`, `controller/`, `service/`, `repository/`, `dto/`
- **Controller Patterns:** Versioned API (`/api/v1/users`), ใช้ HTTP Methods ถูกต้อง
- **Service Patterns:** Interface + Implementation

#### `java-migration` — การอัปเกรด Java
**Migration Paths:** Java 8 → 11 → 17 → 21 → 25
- **8→11:** JAXB, JAX-WS, Java EE modules ถูกลบ
- **11→17:** Sealed classes, Strong encapsulation
- **17→21:** Pattern matching changes, `finalize()` deprecated
- **21→25:** Security Manager removed, Unsafe methods removed

#### `jpa-patterns` — JPA/Hibernate Patterns
**ปัญหาที่พบบ่อย:**
- **N+1 Problem:** ใช้ `JOIN FETCH`, `@EntityGraph`, `@BatchSize`
- **LazyInitializationException:** ใช้ `JOIN FETCH`, DTO Projection
- **Optimistic Locking:** ใช้ `@Version`

#### `logging-patterns` — Structured Logging
- **JSON Format:** ดีสำหรับ AI Analysis (Parsing ง่าย, Token น้อย)
- **Key Fields:** `requestId`, `traceId`, `step`, `duration_ms`
- **Spring Boot 3.4+:** Built-in Structured Logging

---

### 3.5 Project Exploration & Planning (Amplicode)

#### `spring-explore` — สำรวจโปรเจกต์
**ขั้นตอนการทำงาน (6 Steps):**
- **Step 0:** ทำนายสิ่งที่เกี่ยวข้องจาก Request
- **Step 1-5:** เก็บรวบรวมบริบท: Tech Stack, Modules, Entities, Repositories, Services, Controllers
- **Requires:** Spring MCP Server (Amplicode IntelliJ Plugin)

#### `spring-planning` — สร้าง Implementation Plan
- สร้างแผนใน `docs/plans/yyyymmdd-<description>.md`
- ใช้ `AskUserQuestion` เพื่อเก็บข้อมูล: Purpose, Scope, Constraints, Testing Approach

#### `amplicode-install` — ติดตั้ง Amplicode Plugin
- ตรวจจับ IntelliJ IDEA (Ultimate/Community) และ GigaIDE
- ติดตั้ง Plugin ผ่าน JetBrains CLI
- **สำคัญ:** ต้องคลิก "Настроить Spring Agent" บน Welcome Screen

---

### 3.6 Code Generation Skills (Amplicode)

#### `spring-data-jpa` — สร้าง/แก้ไข JPA Entities
- ตรวจจับ Entity Conventions (命名, annotations)
- ปฏิบัติตาม `entity-rules-impl.md` และ `transaction-rules-impl.md`

#### `spring-data-jdbc` — สร้าง/แก้ไข JDBC Entities
- ใช้ `@Table` (from `spring-data-relational`), `@MappedCollection`, `@Embedded`
- **Detection Guard:** ถ้าเห็น `jakarta.persistence.*` → ใช้ `spring-data-jpa` แทน

#### `crud-rest-controller` — สร้าง CRUD REST Controller
- สร้าง Controller พร้อม CRUD Endpoints
- รองรับ DTO Mapping, Pagination, Filtering, Patch Support

#### `dto-creator` — สร้าง DTO
- รองรับ: Java Class, Java Record, Java+Lombok, Kotlin Data Class
- เลือก Attributes, Constructors, Getters/Setters, equals/hashCode

#### `mapper-creator` — สร้าง Mapper
- **MapStruct:** Interface/Abstract Class
- **Custom:** Converter Class
- รองรับ `partialUpdate` และ `partialUpdateNullStrategy`

#### `kafka-configuration` — ตั้งค่า Kafka
- **Path A:** ใช้ `application.properties` (default)
- **Path B:** ใช้ `@Configuration` Class

#### `connekt-script-writer` — เขียน Connekt Script
- ใช้ `.connekt.kts` extension
- ใช้ `connekt.env.json` สำหรับ Base URLs และ Secrets
- **สำคัญ:** ใช้ `pathParam` แทนการ interpolate ค่า

---

### 3.7 Testing & Quality Skills (Amplicode)

#### `coverage` — วัด Test Coverage
- **ทั้งหมด:** `jacocoTestReport` (Unit + Integration)
- **Unit:** `jacocoTestReport -x testIntgr`
- **Integration:** `jacocoTestReport -x test`

#### `mutation-testing` — Mutation Testing ด้วย PIT
- ตรวจสอบ Quality ของ Tests (ต่างจาก Coverage)
- ตั้งค่า PIT ใน Build → ปรับ Exclusions → Run → Report Score

---

### 3.8 Development Tools (Amplicode)

#### `java-debug` — ดีบักผ่าน IntelliJ
- **Safety Rules:** ระวัง Suspended-Process Trap (App หยุดตอบสนองเมื่อ Hit Breakpoint)
- **Tools:** `toggle_breakpoint`, `evaluate_expression`, `get_stack_trace`

#### `codefmt` — จัดรูปแบบโค้ด
- **Path 1 (IDE):** ใช้ IntelliJ Formatter
- **Path 2 (CLI):** ใช้ Spotless / google-java-format / palantir-java-format

---

## 4. Workflow Template ครบวงจร

### 🚀 Phase 0: ติดตั้งและเตรียมพร้อม

```
ใช้ amplicode-install skill เพื่อติดตั้ง Amplicode IntelliJ Plugin
→ เปิดโปรเจกต์ → คลิก "Настроить Spring Agent" บน Welcome Screen
```

---

### 📋 Phase 1: รับ Requirement และสำรวจโปรเจกต์

| Step | Action | Skill / Prompt |
|------|--------|----------------|
| 1.1 | สำรวจโปรเจกต์ | `ใช้ spring-explore skill เพื่อสำรวจโปรเจกต์ของฉัน` |
| 1.2 | วิเคราะห์ Requirement | `ใช้ issue-triage skill ช่วยวิเคราะห์และจัดลำดับความสำคัญของงานนี้` |
| 1.3 | สร้าง Acceptance Criteria | `ช่วยแปลง Story นี้เป็น Gherkin (Given-When-Then)` |

**Output:** Project Context + AC + Priority

---

### 📝 Phase 2: วางแผน

| Step | Action | Skill / Prompt |
|------|--------|----------------|
| 2.1 | วางแผน Implementation | `ใช้ spring-planning skill สร้าง Implementation Plan` |
| 2.2 | ตรวจสอบ Design | `ใช้ architecture-review skill ตรวจสอบว่า Design สอดคล้องกับสถาปัตยกรรมหรือไม่` |

**Output:** `docs/plans/yyyymmdd-<description>.md`

---

### 🏗️ Phase 3: สร้างโค้ด (Code Generation)

#### 3.1 สร้าง Entity + Repository

```
ใช้ spring-data-jpa skill สร้าง Entity และ Repository
```

#### 3.2 สร้าง DTO

```
ใช้ dto-creator skill สร้าง RequestDTO และ ResponseDTO
```

#### 3.3 สร้าง Mapper

```
ใช้ mapper-creator skill สร้าง Mapper ระหว่าง Entity และ DTO
```

#### 3.4 สร้าง REST Controller

```
ใช้ crud-rest-controller skill สร้าง CRUD REST Controller
```

#### 3.5 (Optional) ตั้งค่า Kafka

```
ใช้ kafka-configuration skill ตั้งค่า Kafka Producer/Consumer
```

#### 3.6 (Optional) เขียน Test Script

```
ใช้ connekt-script-writer skill สร้าง Connekt Script สำหรับทดสอบ API
```

---

### 🔍 Phase 4: ตรวจสอบคุณภาพ (Quality Assurance)

| Step | Action | Skill / Prompt |
|------|--------|----------------|
| 4.1 | จัดรูปแบบโค้ด | `ใช้ codefmt skill จัดรูปแบบโค้ดที่สร้าง` |
| 4.2 | Code Review | `ใช้ java-code-review skill ตรวจสอบโค้ดทั้งหมด` |
| 4.3 | ตรวจสอบ Performance | `ใช้ performance-smell-detection skill ตรวจจับ Performance Smells` |
| 4.4 | ตรวจสอบ Security | `ใช้ security-audit skill ตรวจสอบความปลอดภัย` |
| 4.5 | ตรวจสอบ Concurrency | `ใช้ concurrency-review skill ตรวจสอบ Thread Safety` |
| 4.6 | ตรวจสอบ API Contract | `ใช้ api-contract-review skill ตรวจสอบ REST API` |
| 4.7 | ตรวจสอบ SOLID | `ใช้ solid-principles skill ตรวจสอบ Design` |

---

### 🧪 Phase 5: Testing

| Step | Action | Skill / Prompt |
|------|--------|----------------|
| 5.1 | เขียน Unit Test | `ใช้ test-quality skill ช่วยเขียน JUnit 5 + AssertJ Tests` |
| 5.2 | วัด Coverage | `ใช้ coverage skill ตรวจสอบ Test Coverage` |
| 5.3 | Mutation Testing | `ใช้ mutation-testing skill ตรวจสอบคุณภาพของ Tests` |

---

### 🐛 Phase 6: Debug (ถ้ามีปัญหา)

```
ใช้ java-debug skill ช่วยดีบักปัญหาที่เกิดขึ้น
```

---

### 📦 Phase 7: Commit & Release

| Step | Action | Skill / Prompt |
|------|--------|----------------|
| 7.1 | สร้าง Commit | `ใช้ git-commit skill สร้าง Commit Message` |
| 7.2 | สร้าง Changelog | `ใช้ changelog-generator skill สร้าง Changelog` |

---

## 5. Quick Reference Cards

### 🏷️ Card 1: เมื่อไหร่ควรใช้ Skill ไหน?

| สถานการณ์ | Skill ที่แนะนำ |
|-----------|---------------|
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

### 🏷️ Card 2: Mandatory Gate Checklist (ก่อน Merge)

- [ ] `codefmt` — โค้ดจัดรูปแบบเรียบร้อย
- [ ] `java-code-review` — ไม่พบ Critical/High Issue
- [ ] `security-audit` — ไม่พบ Vulnerability
- [ ] `coverage` — Coverage ≥ 80%
- [ ] `mutation-testing` — Mutation Score ≥ 70%
- [ ] `mvn clean install` — Compile ผ่าน
- [ ] `mvn test` — Tests ผ่านทั้งหมด

---

### 🏷️ Card 3: การติดตั้ง Skills

**ติดตั้งทั้งหมดจาก decebals/claude-code-java:**
```bash
npx skills add decebals/claude-code-java
```

**ติดตั้งเฉพาะบาง Skill:**
```bash
npx skills add decebals/claude-code-java --skill java-code-review
npx skills add decebals/claude-code-java --skill git-commit
```

**ติดตั้งทั้งหมดจาก Amplicode/spring-skills (Global):**
```bash
npx skills add Amplicode/spring-skills -g
```

**ดูรายการ Skills ก่อนติดตั้ง:**
```bash
npx skills add Amplicode/spring-skills --list
```

---

## 6. การติดตั้งและใช้งาน

### 6.1 การติดตั้ง Amplicode Spring Agent Toolkit (Full Experience)

1. **ติดตั้ง IntelliJ Plugin:**
   - เปิด IntelliJ IDEA → Settings → Plugins → Marketplace
   - ค้นหา "Amplicode" → Install

2. **เปิดโปรเจกต์และคลิก "Настроить Spring Agent":**
   - เปิดโปรเจกต์ Spring Boot
   - คลิกปุ่ม "Настроить Spring Agent" บน Welcome Screen

3. **ติดตั้ง Skills:**
   ```bash
   npx skills add Amplicode/spring-skills -g
   ```

4. **Restart MCP Client**

### 6.2 การติดตั้ง Claude Code Java Skills (Standalone)

```bash
# Clone repository
git clone https://github.com/decebals/claude-code-java.git ~/projects/claude-code-java
cd ~/projects/claude-code-java
chmod +x scripts/*.sh

# Setup ในโปรเจกต์ Java ของคุณ
./scripts/setup-project.sh ~/projects/your-java-project
```

หรือติดตั้งเฉพาะ Skills:
```bash
mkdir -p your-project/.claude/skills
cp -r ~/projects/claude-code-java/skills/java-code-review your-project/.claude/skills/
```

### 6.3 การใช้งานกับ Claude Code

```bash
cd ~/projects/your-java-project
claude
# Skills จะโหลดอัตโนมัติตาม Context
# หรือเรียกใช้โดยตรง: /git-commit, /java-code-review
```

---

## 📊 สรุปตาราง Mapping Skills กับ Workflow

| Workflow Phase | Skills ที่แนะนำ | แหล่งที่มา |
|---------------|----------------|-----------|
| ติดตั้ง | `amplicode-install` | Amplicode |
| สำรวจโปรเจกต์ | `spring-explore` | Amplicode |
| วางแผน | `spring-planning`, `architecture-review` | Amplicode / decebals |
| สร้าง Entity | `spring-data-jpa`, `spring-data-jdbc` | Amplicode |
| สร้าง DTO | `dto-creator` | Amplicode |
| สร้าง Mapper | `mapper-creator` | Amplicode |
| สร้าง Controller | `crud-rest-controller` | Amplicode |
| ตั้งค่า Kafka | `kafka-configuration` | Amplicode |
| เขียน Test Script | `connekt-script-writer` | Amplicode |
| จัดรูปแบบโค้ด | `codefmt` | Amplicode |
| Code Review | `java-code-review` | decebals |
| Performance | `performance-smell-detection` | decebals |
| Security | `security-audit` | decebals |
| Concurrency | `concurrency-review` | decebals |
| API Contract | `api-contract-review` | decebals |
| SOLID | `solid-principles` | decebals |
| Clean Code | `clean-code` | decebals |
| Design Patterns | `design-patterns` | decebals |
| Spring Boot | `spring-boot-patterns` | decebals |
| JPA | `jpa-patterns` | decebals |
| Logging | `logging-patterns` | decebals |
| Java Migration | `java-migration` | decebals |
| Dependency | `maven-dependency-audit` | decebals |
| Test | `test-quality` | decebals |
| Coverage | `coverage` | Amplicode |
| Mutation Testing | `mutation-testing` | Amplicode |
| Debug | `java-debug` | Amplicode |
| Commit | `git-commit` | decebals |
| Changelog | `changelog-generator` | decebals |
| Issue | `issue-triage` | decebals |

---

> **💡 เคล็ดลับ:** Skills จาก **Amplicode/spring-skills** เหมาะสำหรับการ **สร้างโค้ด** (Code Generation) และ **การพัฒนา** ส่วน Skills จาก **decebals/claude-code-java** เหมาะสำหรับการ **ตรวจสอบคุณภาพ** (Quality Assurance) — ใช้ร่วมกันจะได้ผลลัพธ์ที่ดีที่สุด! 🚀