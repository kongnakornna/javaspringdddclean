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

 เข้าใจแล้วครับ! คุณต้องการ **“Prompt Template”** ที่นำไปใช้ได้จริงในชีวิตประจำวัน แยกตาม **สถานการณ์การทำงาน (Scenario)** แทนที่จะเรียงตามชื่อ Skill เพราะนักพัฒนาเวลาทำงานจะนึกถึง “ฉันกำลังทำอะไร” มากกว่า “ฉันจะใช้ Skill อะไร”

ผมจึงขอจัดทำ **“🧩 Scenario-Based Prompt Templates”** ฉบับสมบูรณ์ พร้อม **ตัวแปรให้กรอก (Placeholder)** และบอกว่ามันจะเรียกใช้ Skill ไหนบ้างโดยอัตโนมัติ (Automated Skill Chaining) คัดลอกไปวางใน Claude Code, Cursor หรือ Claude Desktop ได้เลย

---

# 🧩 Java Spring Boot DDD + AI: Scenario-Based Prompt Templates

> **วิธีใช้:** คัดลอก Prompt ในช่อง
> `📋 Prompt Template` ไปวาง แล้วแทนที่ `[ข้อความในวงเล็บ]` ด้วยข้อมูลจริงของคุณ

---

## 🚀 Scenario 1: เริ่มต้นฟีเจอร์ใหม่ / วางแผน

**📌 ใช้เมื่อ:** ได้รับ Story จาก PO หรือต้องการเริ่มเขียนฟีเจอร์ใหม่

| องค์ประกอบ | รายละเอียด |
| :--- | :--- |
| **🎯 เป้าหมาย** | สำรวจโปรเจกต์, แตก Acceptance Criteria (Gherkin), และวางแผน Technical Implementation |
| **🧠 Skills ที่ใช้** | `spring-explore` + `issue-triage` + `spring-planning` (เรียกใช้อัตโนมัติ) |

**📋 Prompt Template (คัดลอกไปใช้):**
> `ใช้ spring-explore skill เพื่อสำรวจโปรเจกต์ปัจจุบันและทำความเข้าใจ Tech Stack จากนั้น帮我แปลง Story นี้เป็น Gherkin Acceptance Criteria (Given-When-Then) และใช้ spring-planning skill สร้าง Implementation Plan บันทึกใน docs/plans/:`
> 
> **Story:** [วางข้อความ Story หรือ Task จาก PO ที่นี่]
> **ขอบเขต:** [ระบุขอบเขต เช่น เฉพาะ User Module / ส่งผลต่อ Order ด้วย]
> **ข้อจำกัด:** [เช่น ต้องรองรับ 1000 TPS หรือ ต้อง Compatible กับ DB Version เดิม]

**✅ Expected Output (สิ่งที่ควรได้กลับมา):**
- สรุป Tech Stack + Dependencies
- รายการ Acceptance Criteria (AC) แบบ Gherkin พร้อม Edge Cases
- ไฟล์แผนงาน `docs/plans/yyyymmdd-[feature-name].md` (มี Sub-task แยกตาม DDD Layers)

---

## 🏗️ Scenario 2: สร้าง CRUD + DTO + Mapper (DDD)

**📌 ใช้เมื่อ:** ต้องการสร้าง Entity, Repository, DTO, Mapper และ REST Controller ครบชุดในครั้งเดียว

| องค์ประกอบ | รายละเอียด |
| :--- | :--- |
| **🎯 เป้าหมาย** | สร้างชุดโค้ด CRUD ตาม DDD + Clean Architecture โดยไม่ต้องไล่สร้างทีละไฟล์ |
| **🧠 Skills ที่ใช้** | `spring-data-jpa` + `dto-creator` + `mapper-creator` + `crud-rest-controller` (Chain อัตโนมัติ) |

**📋 Prompt Template (คัดลอกไปใช้):**
> `สร้าง CRUD สำหรับ Entity ชื่อ [EntityName] (เช่น Product) โดยใช้ DDD Pattern พร้อมกันทุกชั้นดังนี้:`
> - **Entity:** มี Fields [เช่น id, name, price, createdAt] และความสัมพันธ์ [เช่น @OneToMany กับ OrderItem]
> - **DTO:** ใช้ [Java Record / Lombok] สร้าง RequestDTO และ ResponseDTO
> - **Mapper:** ใช้ [MapStruct] สร้าง Mapper
> - **Controller:** สร้าง REST CRUD (GET, POST, PUT, DELETE) พร้อม Pagination, Exception Handling และ Swagger Annotations
> - **Validation:** เพิ่ม @Valid และข้อความ Error เป็นภาษาไทย

**✅ Expected Output (สิ่งที่ควรได้กลับมา):**
- `[Entity].java` (JPA)
- `[Entity]Repository.java`
- `[Entity]RequestDTO.java` / `[Entity]ResponseDTO.java`
- `[Entity]Mapper.java` (MapStruct)
- `[Entity]Controller.java` (CRUD API)

---

## 🧪 Scenario 3: เขียน Unit Test + ตรวจสอบ Coverage

**📌 ใช้เมื่อ:** เขียนโค้ดเสร็จแล้ว ต้องการเขียน Test และวัดคุณภาพ Test

| องค์ประกอบ | รายละเอียด |
| :--- | :--- |
| **🎯 เป้าหมาย** | สร้าง Unit Test (JUnit 5 + AssertJ) และตรวจสอบ Coverage / Mutation Score |
| **🧠 Skills ที่ใช้** | `test-quality` + `coverage` + `mutation-testing` (Chain อัตโนมัติ) |

**📋 Prompt Template (คัดลอกไปใช้):**
> `ใช้ test-quality skill ช่วยเขียน Unit Test สำหรับคลาส [ชื่อคลาสหลัก เช่น ProductService] โดยครอบคลุมทั้ง Happy Path และ Edge Cases (เช่น Data Not Found, Duplicate Key) จากนั้นใช้ coverage skill วัดว่า Coverage ถึงเกณฑ์ 80% หรือไม่ และถ้ายัง ให้ใช้ mutation-testing skill ตรวจสอบว่ามี Mutation ใดรอด (Survived) บ้าง`

**✅ Expected Output (สิ่งที่ควรได้กลับมา):**
- ไฟล์ `[Class]Test.java` (JUnit 5 + AssertJ)
- รายงาน Coverage (%) แยกตาม Package
- รายงาน PIT Mutation Score และรายการ Mutation ที่รอด (พร้อมคำแนะนำให้แก้ Test)

---

## 🔍 Scenario 4: Code Review + Security + Performance (Pre-Merge)

**📌 ใช้เมื่อ:** ก่อนทำ Pull Request (PR) หรือ Merge เข้า Main Branch (Mandatory Gate)

| องค์ประกอบ | รายละเอียด |
| :--- | :--- |
| **🎯 เป้าหมาย** | ตรวจสอบโค้ดรอบด้าน ทั้ง Clean Code, Security, Performance, Concurrency ก่อน Merge |
| **🧠 Skills ที่ใช้** | `codefmt` + `java-code-review` + `security-audit` + `performance-smell-detection` + `concurrency-review` + `api-contract-review` (Run พร้อมกัน) |

**📋 Prompt Template (คัดลอกไปใช้):**
> `ก่อน Merge โค้ดในแพ็คเกจ [ชื่อแพ็คเกจ] หรือไฟล์ [ชื่อไฟล์] ช่วยทำ Full Quality Check ให้หน่อย:`
> 1. `ใช้ codefmt skill จัดรูปแบบโค้ด`
> 2. `ใช้ java-code-review skill ตรวจสอบ Null Safety, Exception Handling, Collections`
> 3. `ใช้ security-audit skill ตรวจสอบ OWASP Top 10 (โดยเฉพาะ SQL Injection, Hardcoded Secret)`
> 4. `ใช้ performance-smell-detection skill ตรวจจับ Regex compile ใน loop หรือ String concat`
> 5. `ใช้ concurrency-review skill ตรวจสอบ @Async หรือ shared state`
> 6. `ใช้ api-contract-review skill ตรวจสอบ Controller ว่าใช้ HTTP Verb และ Status Code ถูกต้อง`

**✅ Expected Output (สิ่งที่ควรได้กลับมา):**
- รายงานสรุปเป็นตาราง: `[Severity] | [Issue] | [Location] | [Suggestion]`
- ถ้าพบ Critical → แนะนำให้แก้ก่อน Merge
- ถ้าผ่านทั้งหมด → ขึ้นสถานะ `✅ Ready for Merge`

---

## 🐛 Scenario 5: แก้ Bug (วิเคราะห์ + TDD)

**📌 ใช้เมื่อ:** เจอ Bug จาก QA, Production Log, หรือระหว่างพัฒนา (แยกเป็น 2 Path อัตโนมัติ)

| องค์ประกอบ | รายละเอียด |
| :--- | :--- |
| **🎯 เป้าหมาย** | วิเคราะห์ Root Cause, เขียน Test ที่ล้มเหลวก่อน (Red), แล้วแก้โค้ดให้ผ่าน (Green) |
| **🧠 Skills ที่ใช้** | (Prompt-Based Analysis) + `test-quality` + `java-debug` |

**📋 Prompt Template (คัดลอกไปใช้):**
> **ช่วยแก้ Bug นี้โดยใช้ TDD (Test-Driven Development):**
> 
> **Error Log / อาการ:** [วาง Stack Trace หรือข้อความ Error ที่เจอ]
> **เกิดที่ Environment:** [Local / Dev / Staging / Production]
> **ผู้ใช้งานที่พบ:** [ID User หรือ Role]
> 
> **ขั้นตอนที่ให้ AI ทำ:**
> 1. `ใช้เทคนิค 5 Whys วิเคราะห์ Root Cause`
> 2. `ใช้ test-quality skill เขียน JUnit Test ที่ต้องล้มเหลว (Red) ก่อน`
> 3. `ช่วยแก้โค้ดให้ Test ผ่าน (Green)`
> 4. `ใช้ java-debug skill (ถ้าจำเป็น) เพื่อตั้ง Breakpoint ตรวจสอบค่าตัวแปร`

**✅ Expected Output (สิ่งที่ควรได้กลับมา):**
- รายงาน Root Cause Analysis (สาเหตุหลัก + สาเหตุรอง + ผลกระทบ)
- ไฟล์ Test ที่เขียนใหม่ (ต้อง Fail ก่อน)
- Diff ของโค้ดที่แก้ไข (ให้ Test ผ่าน)
- (ถ้า Path A แก้หลายจุด) จะได้ไฟล์ Implementation Plan เพิ่มเติม

---

## 📦 Scenario 6: Commit + สร้าง Changelog

**📌 ใช้เมื่อ:** เสร็จงานและต้องการ Commit อย่างเป็นระบบ และเตรียม Release

| องค์ประกอบ | รายละเอียด |
| :--- | :--- |
| **🎯 เป้าหมาย** | สร้าง Commit Message ตาม Standard และอัปเดต Changelog |
| **🧠 Skills ที่ใช้** | `git-commit` + `changelog-generator` |

**📋 Prompt Template (คัดลอกไปใช้):**
> `ใช้ git-commit skill สร้าง Commit Message สำหรับการเปลี่ยนแปลงที่เกี่ยวกับ [สรุปสั้นๆ ว่าแก้/เพิ่มอะไร] และใช้ changelog-generator skill อัปเดต CHANGELOG.md สำหรับ Version [เวอร์ชันใหม่]`
> 
> **ประเภทย่อย (Scope):** [เช่น user, order, security]
> **Breaking Change:** [Yes / No]
> **Ticket / Issue:** [เช่น JIRA-1234]

**✅ Expected Output (สิ่งที่ควรได้กลับมา):**
- Commit Message แบบ `feat(scope): subject` + Body อธิบาย
- ไฟล์ `CHANGELOG.md` ที่เพิ่มรายการใหม่เข้าไปในส่วน Unreleased หรือ Version ใหม่

---

## ⚙️ Scenario 7: ตั้งค่า Kafka / Event-Driven

**📌 ใช้เมื่อ:** ต้องการส่ง Event หรือ Consume Message ผ่าน Kafka

| องค์ประกอบ | รายละเอียด |
| :--- | :--- |
| **🎯 เป้าหมาย** | สร้าง Configuration และ Producer/Consumer Code |
| **🧠 Skills ที่ใช้** | `kafka-configuration` |

**📋 Prompt Template (คัดลอกไปใช้):**
> `ใช้ kafka-configuration skill ตั้งค่า Kafka สำหรับโปรเจกต์นี้ โดย:`
> - **Topic:** [ชื่อ Topic เช่น order-events]
> - **Type:** [Producer / Consumer / Both]
> - **Key/Value Serde:** [String / Json / Avro]
> - **Config Method:** [ใช้ application.yml / ใช้ @Configuration Class]

**✅ Expected Output (สิ่งที่ควรได้กลับมา):**
- ไฟล์ `KafkaConfig.java` หรือ Properties ใน `application.yml`
- Bean `KafkaTemplate` (Producer) หรือ `@KafkaListener` (Consumer)
- ตัวอย่างการส่ง/รับ Message

---

## 🛠️ Scenario 8: Debug ปัญหาค้าง (Suspended Process)

**📌 ใช้เมื่อ:** แอปค้าง หรือ Debug Mode ติด Loop ไม่รู้จบ

| องค์ประกอบ | รายละเอียด |
| :--- | :--- |
| **🎯 เป้าหมาย** | ตั้ง Breakpoint ที่ถูกต้อง และ Evaluate Expression เพื่อหาสาเหตุ |
| **🧠 Skills ที่ใช้** | `java-debug` |

**📋 Prompt Template (คัดลอกไปใช้):**
> `ใช้ java-debug skill ช่วยดีบักปัญหาที่เกิดขึ้นในคลาส [ชื่อคลาส] Method [ชื่อMethod] โดยอาการคือ [อธิบายอาการ เช่น Infinite Loop หรือ NPE ที่บรรทัด X]`
> 
> **สิ่งที่ต้องการ:** `ช่วยแนะนำการตั้ง Breakpoint, ดู Call Stack, และ Evaluate Expression เพื่อหาค่าที่ผิดปกติ`

**✅ Expected Output (สิ่งที่ควรได้กลับมา):**
- คำแนะนำตำแหน่ง Breakpoint ที่เหมาะสม
- ค่าตัวแปรที่ควรตรวจสอบ (Evaluate Expression)
- สรุปสาเหตุและแนวทางแก้ไข

---

# 📋 สรุป Quick Reference (Cheat Sheet) สำหรับ Daily Use

| ถ้าคุณต้องการ... | ใช้ Prompt สั้นๆ นี้ได้เลย |
| :--- | :--- |
| **เริ่มงานใหม่** | `ใช้ spring-explore และ spring-planning เพื่อเริ่มฟีเจอร์: [ชื่อ]` |
| **สร้าง CRUD** | `ใช้ dto-creator, mapper-creator, spring-data-jpa และ crud-rest-controller สร้าง CRUD สำหรับ [Entity]` |
| **เขียน Test** | `ใช้ test-quality เขียน Unit Test ให้ [คลาส]` |
| **ตรวจสอบโค้ด** | `ใช้ java-code-review, security-audit, performance-smell-detection ตรวจสอบ [ไฟล์/แพ็คเกจ]` |
| **แก้ Bug** | `ใช้ 5 Whys วิเคราะห์ Error นี้ แล้วใช้ test-quality เขียน Test ก่อนแก้: [Error Log]` |
| **Commit** | `ใช้ git-commit สร้าง Commit Message สำหรับ [งานที่ทำ]` |

---

💡 **เคล็ดลับเพิ่มเติม:** คุณสามารถ **รวมหลาย Scenario** ใน Prompt เดียวได้ เช่น:  
> `ใช้ spring-explore สำรวจโปรเจกต์, ใช้ dto-creator และ crud-rest-controller สร้าง API, และใช้ java-code-review ตรวจสอบโค้ดที่สร้างให้เสร็จภายในรอบเดียว`
 