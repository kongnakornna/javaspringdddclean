# 🚀 Spring Boot DDD Template
####  http://localhost:1080/api/swagger-ui.html
This project was developed with a focus on scalability, maintainability, and productivity in enterprise application development, by applying **Domain-Driven Design (DDD)** and **Clean Architecture** principles.

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.1-green.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## 📋 Table of Contents

- [🎯 Overview](#-overview)
- [🏗️ Architecture](#️-architecture)
- [🎨 Generic Structures](#-generic-structures)
- [💾 Databases](#-databases)
- [🔧 Configuration and Installation](#-configuration-and-installation)
- [🚀 Execution](#-execution)
- [📊 Execution Profiles](#-execution-profiles)
- [🔍 Monitoring and Logs](#-monitoring-and-logs)
- [📝 Swagger/OpenAPI](#-swaggeropenapi)
- [🧪 Tests](#-tests)
- [🤝 Contributing](#-contributing)

## 🎯 Overview

This template was carefully designed to accelerate backend application development following software engineering best practices. With a solid architecture based on DDD and Clean Architecture, it offers:

- **Highly reusable generic structures** for CRUD operations
- **Robust error handling system** with automatic logging
- **Advanced monitoring** with AOP (Aspect-Oriented Programming)
- **Multi-database support** (PostgreSQL, MongoDB, Neo4j)
- **Flexible configuration** with multiple execution profiles
- **Automatic documentation** with Swagger/OpenAPI

## 🏗️ Architecture

### Domain-Driven Design (DDD) + Clean Architecture

The project follows a well-defined structure that clearly separates responsibilities:

```
src/main/java/com/icmon/app/
├── _shared/                    # Shared components
│   ├── application/           # Generic application services
│   ├── domain/               # Generic domain entities
│   └── infrastructure/       # Generic infrastructure
├── modules/                  # 🎯 BUSINESS LOGIC
│   └── [your_modules]/      # Specific domain contexts
├── configuration/           # Spring configurations
├── exception/              # Global exception system
├── logging/               # Logging system
└── utils/                # Utilities
```

### Architectural Principles

1. **Separation of Concerns**: Each layer has a specific responsibility
2. **Dependency Inversion**: Domain doesn't depend on infrastructure
3. **Code Reuse**: Generic structures reduce duplication
4. **Testability**: Architecture facilitates unit test creation

## 🎨 Generic Structures

### Generic Entity System

The template uses a hierarchical entity system that maximizes code reuse:

#### Domain Hierarchy
```java
GenericClass                    // Base entity for all domains
└── GenericBusinessClass       // For business entities
    └── [YourEntity]          // Your specific entities
```

#### Infrastructure Hierarchy
```java
GenericEntity                   // Base JPA entity
└── GenericBusinessEntity      // For business entities with JPA
    └── [YourEntitySchema]    // Your specific database schemas
```

### Generic Repositories

The repository system offers complete CRUD operations:

```java
// Generic interface with all operations
GenericBusinessRepository<E>

// Implementation with advanced features
GenericBusinessRepositoryImpl<E, S>
```

**Included features:**
- ✅ Synchronous and asynchronous CRUD operations
- ✅ Paginated queries
- ✅ Automatic soft delete
- ✅ Change auditing
- ✅ User/company access control

### Generic Services

Service layer with pre-implemented functionality:

```java
// Generic service with basic operations
GenericServiceImpl<E, R>
```

**Available resources:**
- 🔐 Automatic authentication via MDC
- 🛡️ Standardized exception handling
- 📊 Optimized read operations
- ⚡ Asynchronous operation support

## 💾 Databases

The template comes pre-configured with three databases for different needs:

### PostgreSQL (Main Database)
- **Usage**: Transactional and relational data
- **Port**: 5432
- **Configuration**: JPA/Hibernate with schema validation

### MongoDB (Logging)
- **Usage**: System logs, auditing and analytics
- **Port**: 27017
- **Configuration**: Spring Data MongoDB

### Neo4j (Graphs)
- **Usage**: Complex relationships and graph analysis
- **Ports**: 7474 (HTTP), 7687 (Bolt)
- **Configuration**: Spring Data Neo4j

### Docker Compose

Run all databases with one command:

```bash
docker-compose up -d
```

**Included services:**
- PostgreSQL + PgAdmin (port 5050)
- MongoDB
- Neo4j

## 🔧 Configuration and Installation

### Prerequisites

- Java 21+
- Maven 3.8+
- Docker & Docker Compose

### Installation

1. **Clone the repository:**
```bash
git clone https://github.com/your-user/spring-boot-ddd-template.git
cd spring-boot-ddd-template
```

2. **Configure environment variables:**
```bash
cp .env.example .env
# Edit the .env file with your configurations
```

3. **Start the databases:**
```bash
docker-compose up -d
```

4. **Run the project:**
```bash
mvn spring-boot:run
```

## 🚀 Execution

### Local Execution (Dev)
```bash
# Default profile (development)
mvn spring-boot:run

# Or specifying the profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Production Build
```bash
mvn clean package -Pprod
java -jar target/app-1.0.0-SNAPSHOT.jar --spring.profiles.active=prod
```

### Test Execution
```bash
mvn test -Ptest
```

## 📊 Execution Profiles

The project supports multiple profiles for different environments:

### 🛠️ Dev (Development)
**File**: `application-dev.yml`

```yaml
spring:
  application:
    name: app-dev
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: validate
```

**Characteristics:**
- Detailed logging enabled
- Database schema validation
- Configurations optimized for development

### 🚀 Prod (Production)
**File**: `application-prod.yml`

```yaml
spring:
  application:
    name: app-prod
  jpa:
    hibernate:
      ddl-auto: validate
```

**Characteristics:**
- Performance-optimized configurations
- Minimal logging
- Strict schema validation
- Environment variables for sensitive configurations

### 🧪 Test (Tests)
**File**: `application-test.yml`

```yaml
spring:
  application:
    name: app-test
  jpa:
    hibernate:
      ddl-auto: update
```

**Characteristics:**
- In-memory H2 database
- Auto table creation
- Isolated test configurations

### Profile Configuration

Each profile can be configured by editing its respective YAML file:

```yaml
# Example of environment-specific configuration
server:
  port: ${SERVER_PORT:1080}
  
spring:
  datasource:
    url: ${SPRING_DATASOURCE_URL}
    username: ${SPRING_DATASOURCE_USERNAME}
    password: ${SPRING_DATASOURCE_PASSWORD}
```

## 🔍 Monitoring and Logs

### AOP System (Aspect-Oriented Programming)

The template includes an advanced monitoring system based on AOP:

#### SystemMonitor
- **Automatic monitoring** of all methods in business modules
- **Exception capture** with complete context
- **Asynchronous logging** to not impact performance
- **Call tracking** with user/company information

```java
@Around("execution(* com.template.app.modules..application..*(..))")
public Object domainMonitor(ProceedingJoinPoint joinPoint) throws Throwable {
    // Automatically monitors all application methods
}
```

### Logging System

#### Log Structure
- **ErrorLogSchema**: Error logs with complete stack trace
- **MethodCallLogSchema**: Method call logs
- **RequestLogSchema**: HTTP request logs

#### Storage
All logs are stored in **MongoDB** for:
- 📊 Performance analysis
- 🐛 Advanced debugging
- 📈 Usage metrics
- 🔍 Complete audit

### GlobalExceptionHandler

Robust exception handling system:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    // Captures ALL system exceptions
    // Saves logs automatically
    // Returns standardized responses
}
```

**Features:**
- ✅ Automatic capture of all exceptions
- ✅ Asynchronous logging for performance
- ✅ Standardized responses for the client
- ✅ Sensitive information filtering

## 📝 Swagger/OpenAPI

### Automatic Documentation

The project generates automatic API documentation using OpenAPI 3.0:

**Access**: `http://localhost:1080/api/swagger-ui.html`

### Swagger Configuration

```java
@OpenAPIDefinition(
    info = @Info(title = "Backend API", version = "v1"),
    tags = {
        // Tags organized by business context
    }
)
@SecuritySchemes({
    @SecurityScheme(
        name = "BearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
    )
})
```

### Available Resources

- 🔐 **JWT Authentication** integrated in documentation
- 📋 **Organized tags** by business context
- 🧪 **Direct testing** of APIs via interface
- 📄 **Export** in standard OpenAPI formats

## 🧪 Tests

### Test Structure

The template includes base classes to facilitate test creation:

```java
// Generic test for repositories
GenericBusinessRepositoryTest<E>

// Base configuration for tests
GenericTest
```

### Execution

```bash
# All tests
mvn test

# Specific test profile
mvn test -Ptest

# Tests with coverage
mvn test jacoco:report
```

### Test Resources

- ✅ **TestContainers** for PostgreSQL
- ✅ **H2** for fast tests
- ✅ **Pre-configured mocks**
- ✅ **Automated test data**

## 🛠️ Customization

### Adding New Modules

1. **Create the module structure** in `src/main/java/com/icmon/app/modules/[your_module]/`

```
your_module/
├── application/           # Application services
│   ├── interfaces/       # Service interfaces
│   └── impl/            # Implementations
├── domain/              # Domain entities
└── infrastructure/      # Repositories and adapters
    ├── repository/
    └── mapper/
```

2. **Extend generic classes:**

```java
// Domain
public class YourEntity extends GenericBusinessClass {
    // Your specific fields
}

// Repository
public class YourRepository extends GenericBusinessRepositoryImpl<YourEntity, YourSchema> {
    // Specific implementations
}

// Service
public class YourService extends GenericServiceImpl<YourEntity, YourRepository> {
    // Specific business logic
}
```

### Advanced Configurations

#### Adding New Database
1. Configure in `application-{profile}.yml`
2. Add dependency in `pom.xml`
3. Create Spring configuration in `configuration/data/`

#### Customizing Exceptions
1. Create your specific exception in `exception/models/`
2. Add handling in `GlobalExceptionHandler`

## 🤝 Contributing

### How to Contribute

1. **Fork** the project
2. Create a **branch** for your feature (`git checkout -b feature/AmazingFeature`)
3. **Commit** your changes (`git commit -m 'Add some AmazingFeature'`)
4. **Push** to the branch (`git push origin feature/AmazingFeature`)
5. Open a **Pull Request**

### Code Standards

- Follow **SOLID** principles
- Keep **test coverage** above 80%
- Use **Javadoc** to document public methods
- Follow established **naming conventions**

### Issues and Bug Reports

Use the available **issue templates** for:
- 🐛 Report bugs
- 💡 Suggest features
- 📚 Improve documentation

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🔗 Useful Links

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Domain-Driven Design](https://domainlanguage.com/ddd/)
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Docker Compose](https://docs.docker.com/compose/)

## 👨‍💻 Author

**kongnakorn jantakun**

---


Updated todo list

ดู logging ที่มีอยู่แล้วก่อน:  

Read [](file:///c%3A/github/javaspringicmon/src/main/java/com/icmon/logging)

Read [](file:///c%3A/github/javaspringicmon/src/main/java/com/icmon/configuration/aop)

Read [](file:///c%3A/github/javaspringicmon/src/main/java/com/icmon/logging/LogService.java)

Read [](file:///c%3A/github/javaspringicmon/src/main/java/com/icmon/configuration/aop/SystemMonitor.java)

Read [](file:///c%3A/github/javaspringicmon/src/main/java/com/icmon/logging/infrastrutcture)

Read [](file:///c%3A/github/javaspringicmon/src/main/java/com/icmon/configuration/aop/AopConfiguration.java)

Read [](file:///c%3A/github/javaspringicmon/src/main/java/com/icmon/logging/infrastrutcture/RequestLogSchema.java)

Read [](file:///c%3A/github/javaspringicmon/src/main/java/com/icmon/logging/infrastrutcture/MethodCallLogSchema.java)

Read [](file:///c%3A/github/javaspringicmon/src/main/java/com/icmon/configuration/web)

Read [](file:///c%3A/github/javaspringicmon/src/main/java/com/icmon/logging/infrastrutcture/ErrorLogSchema.java)

ปัญหาที่พบเพิ่มเติม: SystemMonitor.java มี pointcut ผิด (`com.template.app.modules` แทนที่จะเป็น `com.icmon.module`)

สร้าง `LoggingProperties` + แก้ทุกอย่าง: 

Created [](file:///c%3A/github/javaspringicmon/src/main/java/com/icmon/configuration/aop/LoggingProperties.java)

Replacing 88 lines with 147 lines in [](file:///c%3A/github/javaspringicmon/src/main/java/com/icmon/configuration/aop/SystemMonitor.java), [](file:///c%3A/github/javaspringicmon/src/main/resources/application-dev.yml)


```

```

```

```


Checked [](file:///c%3A/github/javaspringicmon/src/main/java/com/icmon/configuration/aop/SystemMonitor.java), [](file:///c%3A/github/javaspringicmon/src/main/java/com/icmon/configuration/aop/LoggingProperties.java), no problems found

Created 5 todos

เรียบร้อย สรุปสิ่งที่สร้าง/แก้ไข:

**ไฟล์ใหม่: LoggingProperties.java**
> bind config `app.logging.*` — มี 4 flags ควบคุมได้แยกกัน

**แก้ SystemMonitor.java**
> - แก้ pointcut ผิด (`com.template.app.modules` → `com.icmon.module`)
> - inject `LoggingProperties` — ถ้า disabled จะ skip ทั้งหมด
> - เพิ่ม log ทุก method call พร้อม elapsed time (ไม่ใช่แค่ error)
> - เพิ่ม `ErrorLogSchema` บันทึก stack trace อัตโนมัติ

**แก้ application-dev.yml**

```yaml
app:
  logging:
    enabled: true        # ปิดทั้งหมดทีเดียวได้ด้วย false
    log-requests: true
    log-method-calls: true
    log-errors: true
```

ปิด logging ทั้งหมดแค่ตั้ง `enabled: false` ตัวเดียว หรือปิดทีละประเภทก็ได้

Made changes.