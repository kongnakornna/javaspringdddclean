# 🧪 Unit Test Documentation – ครบทุกโมดูล
## พร้อมตัวอย่างโค้ดและแนวปฏิบัติ (Best Practices)

---

| รายการ | รายละเอียด |
|--------|-----------|
| **Framework** | JUnit 5 (Jupiter), Mockito, AssertJ, Testcontainers |
| **Coverage Target** | ≥ 80% (Domain + Application), ≥ 70% (Infrastructure) |
| **Test Types** | Unit Tests (Domain, Service), Integration Tests (Repository, Controller) |
| **Build Tool** | Maven / Gradle |

---

## 📋 สารบัญ (Table of Contents)

1. [แนวทางการเขียน Unit Test](#1-แนวทางการเขียน-unit-test)
2. [โครงสร้างโฟลเดอร์ Tests](#2-โครงสร้างโฟลเดอร์-tests)
3. [โมดูลที่ 1: Authentication & Permission](#3-โมดูลที่-1-authentication--permission)
4. [โมดูลที่ 2: Job Card Management](#4-โมดูลที่-2-job-card-management)
5. [โมดูลที่ 3: Customer Management](#5-โมดูลที่-3-customer-management)
6. [โมดูลที่ 4: Quotation](#6-โมดูลที่-4-quotation)
7. [โมดูลที่ 5: Purchase Order](#7-โมดูลที่-5-purchase-order)
8. [โมดูลที่ 6: Inventory Management](#8-โมดูลที่-6-inventory-management)
9. [โมดูลที่ 7: Payment Management](#9-โมดูลที่-7-payment-management)
10. [โมดูลที่ 8: Dashboard & Reports](#10-โมดูลที่-8-dashboard--reports)
11. [โมดูลที่ 9: Document Management](#11-โมดูลที่-9-document-management)
12. [โมดูลที่ 10: Email Service](#12-โมดูลที่-10-email-service)
13. [โมดูลที่ 11: Batch Jobs](#13-โมดูลที่-11-batch-jobs)
14. [โมดูลที่ 12: Multi-Language (i18n)](#14-โมดูลที่-12-multi-language-i18n)
15. [โมดูลที่ 13: IoT & GPS Tracking](#15-โมดูลที่-13-iot--gps-tracking)
16. [โมดูลที่ 14: Web Order System (WOS)](#16-โมดูลที่-14-web-order-system-wos)
17. [การรัน Tests และ CI/CD Integration](#17-การรัน-tests-และ-cicd-integration)
18. [สรุป Coverage Targets](#18-สรุป-coverage-targets)

---

## 1. แนวทางการเขียน Unit Test

### 1.1 หลักการสำคัญ
- **ทดสอบ Business Logic เป็นหลัก** – เน้นที่ Domain และ Application Layer
- **ใช้ Mockito สำหรับ Dependencies** – Mock Repository, Cache, External Services
- **ใช้ AssertJ สำหรับ Assertions** – ให้ข้อความ error ที่ชัดเจน
- **ตั้งชื่อ Test Method ให้สื่อความหมาย** – `shouldThrowExceptionWhenStatusIsClosed()`
- **ใช้ @DisplayName** – เพิ่มคำอธิบายภาษาไทย/อังกฤษ

### 1.2 โครงสร้าง Test Class (Given-When-Then)
```java
@Test
@DisplayName("ควรโยน Exception เมื่อเปลี่ยนสถานะจาก CLOSED")
void shouldThrowExceptionWhenChangeStatusFromClosed() {
    // GIVEN: สร้าง Job ที่มีสถานะ CLOSED
    TJob job = new TJob();
    job.setStatus(JobStatus.CLOSED);
    
    // WHEN: พยายามเปลี่ยนสถานะ
    // THEN: ควรเกิด IllegalStateException
    assertThatThrownBy(() -> job.changeStatus(JobStatus.IN_PROGRESS))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("Cannot change status of closed");
}
```

### 1.3 การใช้ Mocks (Mockito)
```java
@ExtendWith(MockitoExtension.class)
class JobServiceTest {
    @Mock private JobRepository jobRepository;
    @Mock private JobCacheService cacheService;
    @InjectMocks private JobServiceImpl jobService;
    
    @Test
    void shouldCreateJob() {
        // Mock behavior
        when(jobRepository.create(any(), any())).thenReturn(mockJob);
        
        // Execute
        JobResponseDTO result = jobService.createJob(request);
        
        // Verify
        verify(jobRepository, times(1)).create(any(), any());
        verify(cacheService, times(1)).saveJob(any());
    }
}
```

---

## 2. โครงสร้างโฟลเดอร์ Tests

```
src/test/java/com/template/app/
├── modules/
│   ├── auth/
│   │   ├── domain/
│   │   │   └── MUserTest.java
│   │   ├── application/
│   │   │   ├── AuthServiceImplTest.java
│   │   │   └── UserServiceImplTest.java
│   │   ├── infrastructure/
│   │   │   ├── UserRepositoryImplTest.java
│   │   │   └── JwtTokenProviderTest.java
│   │   └── presentation/
│   │       └── AuthControllerTest.java
│   ├── job/
│   │   ├── domain/
│   │   │   └── TJobTest.java
│   │   ├── application/
│   │   │   └── JobServiceImplTest.java
│   │   ├── infrastructure/
│   │   │   └── JobRepositoryImplTest.java
│   │   └── presentation/
│   │       └── JobControllerTest.java
│   ├── quotation/
│   │   └── ... (similar structure)
│   └── ... (other modules)
├── _shared/
│   ├── GenericBusinessClassTest.java
│   └── GenericServiceImplTest.java
└── configuration/
    └── SecurityConfigTest.java
```

---

## 3. โมดูลที่ 1: Authentication & Permission

### 3.1 Domain Layer: `MUserTest.java`
```java
package com.template.app.modules.auth.domain;

import com.template.app.modules.auth.domain.enums.UserStatus;
import com.template.app.modules.auth.domain.valueobjects.Email;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

@DisplayName("User Domain Entity Tests")
class MUserTest {

    @Test
    @DisplayName("ควรเปิดใช้งานผู้ใช้สำเร็จเมื่อสถานะเป็น INACTIVE")
    void shouldActivateUser() {
        // GIVEN
        MUser user = new MUser();
        user.setStatus(UserStatus.INACTIVE);
        
        // WHEN
        user.activate();
        
        // THEN
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.isActive()).isTrue();
    }

    @Test
    @DisplayName("ควรโยน Exception เมื่อเปิดใช้งานผู้ใช้ที่ถูก SUSPENDED")
    void shouldThrowExceptionWhenActivateSuspendedUser() {
        // GIVEN
        MUser user = new MUser();
        user.setStatus(UserStatus.SUSPENDED);
        
        // WHEN & THEN
        assertThatThrownBy(user::activate)
            .isInstanceOf(DomainException.class)
            .hasMessageContaining("Cannot activate suspended user");
    }

    @Test
    @DisplayName("Email Value Object ควร validate รูปแบบที่ถูกต้อง")
    void shouldValidateEmail() {
        // WHEN
        Email validEmail = new Email("test@example.com");
        
        // THEN
        assertThat(validEmail.getValue()).isEqualTo("test@example.com");
    }

    @Test
    @DisplayName("Email Value Object ควรโยน Exception เมื่อรูปแบบไม่ถูกต้อง")
    void shouldThrowExceptionWhenEmailInvalid() {
        // WHEN & THEN
        assertThatThrownBy(() -> new Email("invalid-email"))
            .isInstanceOf(DomainException.class)
            .hasMessageContaining("Invalid email format");
    }
}
```

### 3.2 Application Layer: `AuthServiceImplTest.java`
```java
package com.template.app.modules.auth.application.impl;

import com.template.app.modules.auth.application.interfaces.AuthService;
import com.template.app.modules.auth.domain.MUser;
import com.template.app.modules.auth.domain.enums.UserStatus;
import com.template.app.modules.auth.infrastructure.repository.UserRepository;
import com.template.app.modules.auth.infrastructure.security.JwtTokenProvider;
import com.template.app.modules.auth.presentation.dto.request.LoginRequestDTO;
import com.template.app.modules.auth.presentation.dto.response.LoginResponseDTO;
import com.template.app.exception.models.FailedRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Auth Service Tests")
class AuthServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private JwtTokenProvider jwtTokenProvider;
    @Mock private PasswordEncoder passwordEncoder;
    @InjectMocks private AuthServiceImpl authService;

    @Test
    @DisplayName("ควร Login สำเร็จเมื่อ Username และ Password ถูกต้อง")
    void shouldLoginSuccessfully() {
        // GIVEN
        LoginRequestDTO request = new LoginRequestDTO();
        request.setUsername("testuser");
        request.setPassword("password123");

        MUser user = new MUser();
        user.setUsername("testuser");
        user.setPasswordHash("encodedPassword");
        user.setStatus(UserStatus.ACTIVE);

        when(userRepository.findByUsername(anyString(), any())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(eq("password123"), anyString())).thenReturn(true);
        when(jwtTokenProvider.generateToken(any())).thenReturn("mock.jwt.token");

        // WHEN
        LoginResponseDTO response = authService.login(request);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.getAccessToken()).isEqualTo("mock.jwt.token");
        verify(userRepository, times(1)).findByUsername(anyString(), any());
    }

    @Test
    @DisplayName("ควรโยน Exception เมื่อผู้ใช้ถูก Suspended")
    void shouldThrowExceptionWhenUserSuspended() {
        // GIVEN
        LoginRequestDTO request = new LoginRequestDTO();
        request.setUsername("testuser");
        request.setPassword("password123");

        MUser user = new MUser();
        user.setStatus(UserStatus.SUSPENDED);

        when(userRepository.findByUsername(anyString(), any())).thenReturn(Optional.of(user));

        // WHEN & THEN
        assertThatThrownBy(() -> authService.login(request))
            .isInstanceOf(FailedRequestException.class)
            .hasMessageContaining("Account is suspended");
    }
}
```

### 3.3 Presentation Layer: `AuthControllerTest.java`
```java
package com.template.app.modules.auth.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.template.app.modules.auth.application.interfaces.AuthService;
import com.template.app.modules.auth.presentation.dto.request.LoginRequestDTO;
import com.template.app.modules.auth.presentation.dto.response.LoginResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@DisplayName("Auth Controller Tests")
class AuthControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private AuthService authService;

    @Test
    @DisplayName("POST /auth/login ควรตอบกลับ 200 OK เมื่อ Login สำเร็จ")
    void shouldLoginSuccessfully() throws Exception {
        // GIVEN
        LoginRequestDTO request = new LoginRequestDTO();
        request.setUsername("admin");
        request.setPassword("admin123");

        LoginResponseDTO response = new LoginResponseDTO();
        response.setAccessToken("mock.jwt.token");
        response.setRefreshToken("mock.refresh.token");

        when(authService.login(any())).thenReturn(response);

        // WHEN & THEN
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("mock.jwt.token"));
    }
}
```

---

## 4. โมดูลที่ 2: Job Card Management

### 4.1 Domain Layer: `TJobTest.java`
```java
package com.template.app.modules.job.domain;

import com.template.app.modules.job.domain.enums.JobStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

@DisplayName("Job Entity Domain Tests")
class TJobTest {

    @Test
    @DisplayName("ควรเปลี่ยนสถานะจาก OPEN เป็น IN_PROGRESS ได้")
    void shouldChangeStatusFromOpenToInProgress() {
        // GIVEN
        TJob job = new TJob();
        job.setStatus(JobStatus.OPEN);
        
        // WHEN
        job.changeStatus(JobStatus.IN_PROGRESS);
        
        // THEN
        assertThat(job.getStatus()).isEqualTo(JobStatus.IN_PROGRESS);
    }

    @Test
    @DisplayName("ควรโยน Exception เมื่อเปลี่ยนสถานะจาก CLOSED")
    void shouldThrowExceptionWhenChangeStatusFromClosed() {
        // GIVEN
        TJob job = new TJob();
        job.setStatus(JobStatus.CLOSED);
        
        // WHEN & THEN
        assertThatThrownBy(() -> job.changeStatus(JobStatus.IN_PROGRESS))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Cannot change status of closed");
    }

    @Test
    @DisplayName("ควร return true เมื่อ Job สามารถสร้าง Quotation ได้")
    void shouldReturnTrueWhenCanCreateQuotation() {
        // GIVEN
        TJob job = new TJob();
        job.setStatus(JobStatus.OPEN);
        
        // WHEN
        boolean result = job.canCreateQuotation();
        
        // THEN
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("ควร return false เมื่อ Job สถานะ CLOSED ไม่สามารถสร้าง Quotation ได้")
    void shouldReturnFalseWhenClosedCannotCreateQuotation() {
        // GIVEN
        TJob job = new TJob();
        job.setStatus(JobStatus.CLOSED);
        
        // WHEN
        boolean result = job.canCreateQuotation();
        
        // THEN
        assertThat(result).isFalse();
    }
}
```

### 4.2 Application Layer: `JobServiceImplTest.java`
```java
package com.template.app.modules.job.application.impl;

import com.template.app.modules.job.application.interfaces.JobService;
import com.template.app.modules.job.domain.TJob;
import com.template.app.modules.job.domain.enums.JobStatus;
import com.template.app.modules.job.infrastructure.cache.JobCacheService;
import com.template.app.modules.job.infrastructure.repository.JobRepository;
import com.template.app.modules.job.presentation.dto.request.JobCreateRequestDTO;
import com.template.app.modules.job.presentation.dto.response.JobResponseDTO;
import com.template.app.exception.SystemGlobalException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Job Service Tests")
class JobServiceImplTest {

    @Mock private JobRepository jobRepository;
    @Mock private JobCacheService cacheService;
    @InjectMocks private JobServiceImpl jobService;

    @Test
    @DisplayName("ควรสร้าง Job ใหม่สำเร็จ")
    void shouldCreateJobSuccessfully() throws SystemGlobalException {
        // GIVEN
        JobCreateRequestDTO request = new JobCreateRequestDTO();
        request.setCustomerId(UUID.randomUUID());
        request.setCarId(UUID.randomUUID());
        request.setMechanicId(UUID.randomUUID());
        request.setSymptom("Engine warning light");

        TJob savedJob = new TJob();
        savedJob.setId(UUID.randomUUID());
        savedJob.setStatus(JobStatus.OPEN);

        when(jobRepository.create(any(), any())).thenReturn(savedJob);

        // WHEN
        JobResponseDTO result = jobService.createJob(request);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(JobStatus.OPEN.name());
        verify(jobRepository, times(1)).create(any(), any());
        verify(cacheService, times(1)).saveJob(any());
    }

    @Test
    @DisplayName("ควรโยน Exception เมื่อ Job ไม่พบ")
    void shouldThrowExceptionWhenJobNotFound() throws SystemGlobalException {
        // GIVEN
        UUID nonExistentId = UUID.randomUUID();
        when(jobRepository.read(eq(nonExistentId), any())).thenThrow(new SystemGlobalException("Job not found", null));

        // WHEN & THEN
        assertThatThrownBy(() -> jobService.getJob(nonExistentId))
            .isInstanceOf(SystemGlobalException.class)
            .hasMessageContaining("Job not found");
    }

    @Test
    @DisplayName("ควรเปลี่ยนสถานะ Job สำเร็จ")
    void shouldChangeStatusSuccessfully() throws SystemGlobalException {
        // GIVEN
        UUID jobId = UUID.randomUUID();
        TJob existingJob = new TJob();
        existingJob.setId(jobId);
        existingJob.setStatus(JobStatus.OPEN);

        when(jobRepository.read(eq(jobId), any())).thenReturn(existingJob);
        when(jobRepository.update(any(), any())).thenReturn(existingJob);

        // WHEN
        JobResponseDTO result = jobService.changeStatus(jobId, JobStatus.IN_PROGRESS);

        // THEN
        assertThat(result.getStatus()).isEqualTo(JobStatus.IN_PROGRESS.name());
        verify(jobRepository, times(1)).update(any(), any());
        verify(cacheService, times(1)).evictJob(jobId);
    }
}
```

---

## 5. โมดูลที่ 3: Customer Management

### 5.1 Domain Layer: `MCustomerTest.java`
```java
package com.template.app.modules.customer.domain;

import com.template.app.modules.customer.domain.enums.CustomerStatus;
import com.template.app.modules.customer.domain.enums.CustomerType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Customer Domain Tests")
class MCustomerTest {

    @Test
    @DisplayName("ควรบันทึกการมาใช้บริการและเพิ่มยอดขาย")
    void shouldRecordVisitAndIncreaseSpent() {
        // GIVEN
        MCustomer customer = new MCustomer();
        customer.setTotalVisitCount(5);
        customer.setTotalSpent(new BigDecimal("5000.00"));

        // WHEN
        customer.recordVisit(new BigDecimal("1500.00"));

        // THEN
        assertThat(customer.getTotalVisitCount()).isEqualTo(6);
        assertThat(customer.getTotalSpent()).isEqualTo(new BigDecimal("6500.00"));
    }

    @Test
    @DisplayName("ลูกค้าสถานะ ACTIVE ควรสามารถใช้บริการได้")
    void shouldCanReceiveServiceWhenActive() {
        // GIVEN
        MCustomer customer = new MCustomer();
        customer.setStatus(CustomerStatus.ACTIVE);

        // WHEN
        boolean result = customer.canReceiveService();

        // THEN
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("ลูกค้าสถานะ BLACKLISTED ควรไม่สามารถใช้บริการได้")
    void shouldCannotReceiveServiceWhenBlacklisted() {
        // GIVEN
        MCustomer customer = new MCustomer();
        customer.setStatus(CustomerStatus.BLACKLISTED);

        // WHEN
        boolean result = customer.canReceiveService();

        // THEN
        assertThat(result).isFalse();
    }
}
```

### 5.2 Application Layer: `CustomerServiceImplTest.java`
```java
package com.template.app.modules.customer.application.impl;

import com.template.app.modules.customer.application.interfaces.CustomerService;
import com.template.app.modules.customer.domain.MCustomer;
import com.template.app.modules.customer.infrastructure.cache.CustomerCacheService;
import com.template.app.modules.customer.infrastructure.repository.CustomerRepository;
import com.template.app.modules.customer.presentation.dto.request.CustomerCreateRequestDTO;
import com.template.app.modules.customer.presentation.dto.response.CustomerResponseDTO;
import com.template.app.exception.SystemGlobalException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Customer Service Tests")
class CustomerServiceImplTest {

    @Mock private CustomerRepository customerRepository;
    @Mock private CustomerCacheService cacheService;
    @InjectMocks private CustomerServiceImpl customerService;

    @Test
    @DisplayName("ควรสร้างลูกค้าใหม่สำเร็จ")
    void shouldCreateCustomerSuccessfully() throws SystemGlobalException {
        // GIVEN
        CustomerCreateRequestDTO request = new CustomerCreateRequestDTO();
        request.setFullName("John Doe");
        request.setPhoneNumber("0812345678");
        request.setEmail("john@example.com");

        MCustomer savedCustomer = new MCustomer();
        savedCustomer.setId(UUID.randomUUID());
        savedCustomer.setFullName("John Doe");
        savedCustomer.setPhoneNumber("0812345678");

        when(customerRepository.existsByPhone(anyString())).thenReturn(false);
        when(customerRepository.create(any(), any())).thenReturn(savedCustomer);

        // WHEN
        CustomerResponseDTO result = customerService.createCustomer(request);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getFullName()).isEqualTo("John Doe");
        verify(customerRepository, times(1)).create(any(), any());
        verify(cacheService, times(1)).saveCustomer(any());
    }

    @Test
    @DisplayName("ควรโยน Exception เมื่อเบอร์โทรซ้ำ")
    void shouldThrowExceptionWhenPhoneDuplicate() throws SystemGlobalException {
        // GIVEN
        CustomerCreateRequestDTO request = new CustomerCreateRequestDTO();
        request.setPhoneNumber("0812345678");

        when(customerRepository.existsByPhone(anyString())).thenReturn(true);

        // WHEN & THEN
        assertThatThrownBy(() -> customerService.createCustomer(request))
            .isInstanceOf(SystemGlobalException.class)
            .hasMessageContaining("Phone number already registered");
    }

    @Test
    @DisplayName("ควรค้นหาลูกค้าด้วยเบอร์โทรจาก Cache ก่อน")
    void shouldGetCustomerFromCacheFirst() throws SystemGlobalException {
        // GIVEN
        String phone = "0812345678";
        MCustomer cachedCustomer = new MCustomer();
        cachedCustomer.setPhoneNumber(phone);
        cachedCustomer.setFullName("Cached Customer");

        when(cacheService.getCustomerByPhone(phone)).thenReturn(cachedCustomer);

        // WHEN
        CustomerResponseDTO result = customerService.getCustomerByPhone(phone);

        // THEN
        assertThat(result.getFullName()).isEqualTo("Cached Customer");
        verify(customerRepository, never()).findByPhone(anyString(), any());
    }
}
```

---

## 6. โมดูลที่ 4: Quotation

### 6.1 Domain Layer: `TQuotationTest.java`
```java
package com.template.app.modules.quotation.domain;

import com.template.app.modules.quotation.domain.enums.QuotationStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Quotation Domain Tests")
class TQuotationTest {

    @Test
    @DisplayName("ควรคำนวณยอดรวมจากรายการอะไหล่และบริการ")
    void shouldCalculateTotalsFromPartsAndServices() {
        // GIVEN
        TQuotation quotation = new TQuotation();
        quotation.setTaxRate(new BigDecimal("7.00"));
        quotation.setDiscountType("PERCENTAGE");
        quotation.setDiscountValue(new BigDecimal("10.00"));

        // สร้างรายการอะไหล่ / Create parts.
        TQuotationPart part1 = new TQuotationPart();
        part1.setNetPrice(new BigDecimal("1000.00"));
        TQuotationPart part2 = new TQuotationPart();
        part2.setNetPrice(new BigDecimal("2000.00"));
        quotation.getParts().add(part1);
        quotation.getParts().add(part2);

        // สร้างรายการบริการ / Create services.
        TQuotationService service1 = new TQuotationService();
        service1.setNetPrice(new BigDecimal("500.00"));
        quotation.getServices().add(service1);

        // WHEN
        quotation.calculateTotals();

        // THEN
        assertThat(quotation.getSubtotal()).isEqualTo(new BigDecimal("3500.00"));
        assertThat(quotation.getTaxAmount()).isEqualTo(new BigDecimal("245.00"));
        assertThat(quotation.getTotal()).isEqualTo(new BigDecimal("3395.00")); // 3500 + 245 - 350
    }

    @Test
    @DisplayName("ควรอนุมัติ Quotation ได้เมื่อสถานะ PENDING")
    void shouldApproveWhenStatusPending() {
        // GIVEN
        TQuotation quotation = new TQuotation();
        quotation.setStatus(QuotationStatus.PENDING);
        quotation.setExpiryDate(LocalDateTime.now().plusDays(7));
        UUID approverId = UUID.randomUUID();

        // WHEN
        quotation.approve(approverId);

        // THEN
        assertThat(quotation.getStatus()).isEqualTo(QuotationStatus.APPROVED);
        assertThat(quotation.getApprovedBy()).isEqualTo(approverId);
        assertThat(quotation.getApprovedAt()).isNotNull();
    }

    @Test
    @DisplayName("ควรโยน Exception เมื่อ Quotation หมดอายุแล้ว")
    void shouldThrowExceptionWhenQuotationExpired() {
        // GIVEN
        TQuotation quotation = new TQuotation();
        quotation.setStatus(QuotationStatus.PENDING);
        quotation.setExpiryDate(LocalDateTime.now().minusDays(1));

        // WHEN & THEN
        assertThatThrownBy(() -> quotation.approve(UUID.randomUUID()))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Quotation has expired");
    }

    @Test
    @DisplayName("ควรตรวจสอบว่า Quotation หมดอายุหรือไม่")
    void shouldCheckIfQuotationIsExpired() {
        // GIVEN
        TQuotation quotation = new TQuotation();
        quotation.setExpiryDate(LocalDateTime.now().minusDays(1));

        // WHEN
        boolean expired = quotation.isExpired();

        // THEN
        assertThat(expired).isTrue();
    }
}
```

---

## 7. โมดูลที่ 5: Purchase Order

### 7.1 Application Layer: `PurchaseOrderServiceImplTest.java`
```java
package com.template.app.modules.purchase.application.impl;

import com.template.app.modules.purchase.application.interfaces.PurchaseOrderService;
import com.template.app.modules.purchase.domain.TPurchaseOrderHeader;
import com.template.app.modules.purchase.domain.enums.PurchaseOrderStatus;
import com.template.app.modules.purchase.infrastructure.cache.PurchaseOrderCacheService;
import com.template.app.modules.purchase.infrastructure.repository.PurchaseOrderRepository;
import com.template.app.modules.purchase.presentation.dto.request.PurchaseOrderCreateRequestDTO;
import com.template.app.modules.purchase.presentation.dto.response.PurchaseOrderResponseDTO;
import com.template.app.exception.SystemGlobalException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Purchase Order Service Tests")
class PurchaseOrderServiceImplTest {

    @Mock private PurchaseOrderRepository poRepository;
    @Mock private PurchaseOrderCacheService cacheService;
    @Mock private InventoryService inventoryService;
    @Mock private PurchaseOrderEmailService emailService;
    @InjectMocks private PurchaseOrderServiceImpl poService;

    @Test
    @DisplayName("ควรสร้าง PO จาก Quotation สำเร็จ")
    void shouldCreatePOFromQuotationSuccessfully() throws SystemGlobalException {
        // GIVEN
        UUID quotationId = UUID.randomUUID();
        Quotation quotation = mock(Quotation.class);
        when(quotation.getStatus()).thenReturn(QuotationStatus.APPROVED);
        when(quotation.getSupplierId()).thenReturn(UUID.randomUUID());

        TPurchaseOrderHeader savedPO = new TPurchaseOrderHeader();
        savedPO.setPoNo("PO-2026-0001");
        savedPO.setStatus(PurchaseOrderStatus.DRAFT);

        when(poRepository.create(any(), any())).thenReturn(savedPO);

        // WHEN
        PurchaseOrderResponseDTO result = poService.createPOFromQuotation(quotationId);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getPoNo()).isEqualTo("PO-2026-0001");
        verify(poRepository, times(1)).create(any(), any());
        verify(cacheService, times(1)).savePurchaseOrder(any());
    }

    @Test
    @DisplayName("ควรส่ง PO ทางอีเมลสำเร็จ")
    void shouldSendPOEmailSuccessfully() throws SystemGlobalException {
        // GIVEN
        UUID poId = UUID.randomUUID();
        TPurchaseOrderHeader po = new TPurchaseOrderHeader();
        po.setStatus(PurchaseOrderStatus.DRAFT);
        po.setPoNo("PO-2026-0001");

        when(poRepository.read(eq(poId), any())).thenReturn(po);
        when(poRepository.update(any(), any())).thenReturn(po);
        when(emailService.sendPurchaseOrderEmail(anyString(), anyString(), any())).thenReturn(true);

        // WHEN
        PurchaseOrderResponseDTO result = poService.sendPurchaseOrder(poId);

        // THEN
        assertThat(result.getStatus()).isEqualTo(PurchaseOrderStatus.SENT.name());
        verify(emailService, times(1)).sendPurchaseOrderEmail(anyString(), anyString(), any());
    }
}
```

---

## 8. โมดูลที่ 6: Inventory Management

### 8.1 Domain Layer: `MPartMasterTest.java`
```java
package com.template.app.modules.inventory.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Part Master Domain Tests")
class MPartMasterTest {

    @Test
    @DisplayName("ควรตรวจสอบว่าสินค้าต่ำกว่า Reorder Level")
    void shouldCheckLowStock() {
        // GIVEN
        MPartMaster part = new MPartMaster();
        part.setStockQuantity(5);
        part.setReorderLevel(10);

        // WHEN
        boolean isLowStock = part.isLowStock();

        // THEN
        assertThat(isLowStock).isTrue();
    }

    @Test
    @DisplayName("ควรเพิ่มจำนวนสต็อกสำเร็จ")
    void shouldIncreaseStockSuccessfully() {
        // GIVEN
        MPartMaster part = new MPartMaster();
        part.setStockQuantity(10);

        // WHEN
        part.increaseStock(5);

        // THEN
        assertThat(part.getStockQuantity()).isEqualTo(15);
        assertThat(part.getLastUpdatedStock()).isNotNull();
    }

    @Test
    @DisplayName("ควรลดจำนวนสต็อกสำเร็จ")
    void shouldDecreaseStockSuccessfully() {
        // GIVEN
        MPartMaster part = new MPartMaster();
        part.setStockQuantity(10);

        // WHEN
        part.decreaseStock(3);

        // THEN
        assertThat(part.getStockQuantity()).isEqualTo(7);
    }

    @Test
    @DisplayName("ควรโยน Exception เมื่อลดสต็อกเกินที่มี")
    void shouldThrowExceptionWhenDecreaseExceedsStock() {
        // GIVEN
        MPartMaster part = new MPartMaster();
        part.setStockQuantity(5);

        // WHEN & THEN
        assertThatThrownBy(() -> part.decreaseStock(10))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Insufficient stock");
    }

    @Test
    @DisplayName("ควรคำนวณมูลค่าสต็อกทั้งหมด")
    void shouldCalculateTotalStockValue() {
        // GIVEN
        MPartMaster part = new MPartMaster();
        part.setStockQuantity(10);
        part.setUnitCost(new BigDecimal("150.50"));

        // WHEN
        BigDecimal totalValue = part.getTotalStockValue();

        // THEN
        assertThat(totalValue).isEqualTo(new BigDecimal("1505.00"));
    }
}
```

### 8.2 Application Layer: `InventoryServiceImplTest.java`
```java
package com.template.app.modules.inventory.application.impl;

import com.template.app.modules.inventory.application.interfaces.InventoryService;
import com.template.app.modules.inventory.domain.MPartMaster;
import com.template.app.modules.inventory.domain.TInventory;
import com.template.app.modules.inventory.infrastructure.cache.InventoryCacheService;
import com.template.app.modules.inventory.infrastructure.cache.PartMasterCacheService;
import com.template.app.modules.inventory.infrastructure.repository.InventoryRepository;
import com.template.app.modules.inventory.infrastructure.repository.PartMasterRepository;
import com.template.app.modules.inventory.presentation.dto.request.InventoryReceiveRequestDTO;
import com.template.app.modules.inventory.presentation.dto.response.InventoryResponseDTO;
import com.template.app.exception.SystemGlobalException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Inventory Service Tests")
class InventoryServiceImplTest {

    @Mock private InventoryRepository inventoryRepository;
    @Mock private PartMasterRepository partMasterRepository;
    @Mock private PartMasterCacheService partCacheService;
    @Mock private InventoryCacheService inventoryCacheService;
    @InjectMocks private InventoryServiceImpl inventoryService;

    @Test
    @DisplayName("ควรรับสินค้าเข้า Inventory สำเร็จ")
    void shouldReceiveGoodsSuccessfully() throws SystemGlobalException {
        // GIVEN
        UUID partId = UUID.randomUUID();
        InventoryReceiveRequestDTO request = new InventoryReceiveRequestDTO();
        request.setPartId(partId);
        request.setQuantity(10);
        request.setUnitCost(new BigDecimal("100.00"));

        MPartMaster part = new MPartMaster();
        part.setId(partId);
        part.setStockQuantity(5);

        when(partCacheService.getPart(partId)).thenReturn(part);
        when(partMasterRepository.update(any(), any())).thenReturn(part);

        TInventory savedTransaction = new TInventory();
        savedTransaction.setId(UUID.randomUUID());

        when(inventoryRepository.create(any(), any())).thenReturn(savedTransaction);

        // WHEN
        InventoryResponseDTO result = inventoryService.receiveGoods(request);

        // THEN
        assertThat(result).isNotNull();
        assertThat(part.getStockQuantity()).isEqualTo(15); // 5 + 10
        verify(inventoryRepository, times(1)).create(any(), any());
        verify(inventoryCacheService, times(1)).evictStockSummary(partId);
    }

    @Test
    @DisplayName("ควรโยน Exception เมื่อเบิกสินค้าเกินสต็อก")
    void shouldThrowExceptionWhenIssueExceedsStock() throws SystemGlobalException {
        // GIVEN
        UUID partId = UUID.randomUUID();
        MPartMaster part = new MPartMaster();
        part.setId(partId);
        part.setStockQuantity(3);

        when(partCacheService.getPart(partId)).thenReturn(part);

        // WHEN & THEN
        assertThatThrownBy(() -> inventoryService.issueGoods(partId, 10, "JOB", UUID.randomUUID()))
            .isInstanceOf(SystemGlobalException.class)
            .hasMessageContaining("Insufficient stock");
    }
}
```

---

## 9. โมดูลที่ 7: Payment Management

### 9.1 Domain Layer: `TPaymentTest.java`
```java
package com.template.app.modules.payment.domain;

import com.template.app.modules.payment.domain.enums.PaymentStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Payment Domain Tests")
class TPaymentTest {

    @Test
    @DisplayName("ควรอนุมัติการชำระเงินสำเร็จ")
    void shouldApprovePaymentSuccessfully() {
        // GIVEN
        TPayment payment = new TPayment();
        payment.setStatus(PaymentStatus.PENDING);
        UUID approverId = UUID.randomUUID();

        // WHEN
        payment.approve(approverId);

        // THEN
        assertThat(payment.getStatus()).isEqualTo(PaymentStatus.COMPLETED);
        assertThat(payment.getApprovedBy()).isEqualTo(approverId);
        assertThat(payment.getApprovedAt()).isNotNull();
    }

    @Test
    @DisplayName("ควรคืนเงินสำเร็จเมื่อสถานะ COMPLETED")
    void shouldRefundSuccessfully() {
        // GIVEN
        TPayment payment = new TPayment();
        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setAmount(new BigDecimal("1000.00"));
        payment.setRefundedAmount(null);

        // WHEN
        payment.processRefund(new BigDecimal("500.00"));

        // THEN
        assertThat(payment.getStatus()).isEqualTo(PaymentStatus.REFUNDED);
        assertThat(payment.getRefundedAmount()).isEqualTo(new BigDecimal("500.00"));
        assertThat(payment.getRefundedAt()).isNotNull();
    }

    @Test
    @DisplayName("ควรคำนวณยอดเงินที่คืนได้คงเหลือ")
    void shouldCalculateRemainingRefundable() {
        // GIVEN
        TPayment payment = new TPayment();
        payment.setAmount(new BigDecimal("1000.00"));
        payment.setRefundedAmount(new BigDecimal("300.00"));

        // WHEN
        BigDecimal remaining = payment.getRemainingRefundable();

        // THEN
        assertThat(remaining).isEqualTo(new BigDecimal("700.00"));
    }

    @Test
    @DisplayName("ควรโยน Exception เมื่อคืนเงินเกินยอดที่จ่าย")
    void shouldThrowExceptionWhenRefundExceedsAmount() {
        // GIVEN
        TPayment payment = new TPayment();
        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setAmount(new BigDecimal("500.00"));

        // WHEN & THEN
        assertThatThrownBy(() -> payment.processRefund(new BigDecimal("1000.00")))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Refund amount exceeds paid amount");
    }
}
```

---

## 10. โมดูลที่ 8: Dashboard & Reports

### 10.1 Service Layer: `DashboardServiceImplTest.java`
```java
package com.template.app.modules.dashboard.application.impl;

import com.template.app.modules.dashboard.application.interfaces.DashboardService;
import com.template.app.modules.dashboard.infrastructure.cache.DashboardCacheService;
import com.template.app.modules.dashboard.infrastructure.repository.DashboardRepository;
import com.template.app.modules.dashboard.presentation.dto.response.DashboardOverviewResponseDTO;
import com.template.app.exception.SystemGlobalException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Dashboard Service Tests")
class DashboardServiceImplTest {

    @Mock private DashboardRepository dashboardRepository;
    @Mock private DashboardCacheService cacheService;
    @InjectMocks private DashboardServiceImpl dashboardService;

    @Test
    @DisplayName("ควรดึงข้อมูล Dashboard จาก Cache ก่อน")
    void shouldGetDashboardFromCacheFirst() throws SystemGlobalException {
        // GIVEN
        UUID whitelabelId = UUID.randomUUID();
        DashboardOverviewResponseDTO cachedData = new DashboardOverviewResponseDTO();
        cachedData.setCacheTimestamp(LocalDateTime.now().minusMinutes(2));

        when(cacheService.getDashboardOverview(whitelabelId)).thenReturn(cachedData);

        // WHEN
        DashboardOverviewResponseDTO result = dashboardService.getDashboardOverview();

        // THEN
        assertThat(result).isEqualTo(cachedData);
        verify(dashboardRepository, never()).getDashboardOverview(any());
    }

    @Test
    @DisplayName("ควรเรียก Repository เมื่อ Cache หมดอายุ")
    void shouldCallRepositoryWhenCacheExpired() throws SystemGlobalException {
        // GIVEN
        UUID whitelabelId = UUID.randomUUID();
        DashboardOverviewResponseDTO expiredCache = new DashboardOverviewResponseDTO();
        expiredCache.setCacheTimestamp(LocalDateTime.now().minusMinutes(10));

        DashboardOverviewResponseDTO freshData = new DashboardOverviewResponseDTO();

        when(cacheService.getDashboardOverview(whitelabelId)).thenReturn(expiredCache);
        when(dashboardRepository.getDashboardOverview(whitelabelId)).thenReturn(freshData);

        // WHEN
        DashboardOverviewResponseDTO result = dashboardService.getDashboardOverview();

        // THEN
        assertThat(result).isEqualTo(freshData);
        verify(dashboardRepository, times(1)).getDashboardOverview(whitelabelId);
    }
}
```

---

## 11. โมดูลที่ 9: Document Management

### 11.1 Service Layer: `DocumentServiceImplTest.java`
```java
package com.template.app.modules.document.application.impl;

import com.template.app.modules.document.application.interfaces.DocumentService;
import com.template.app.modules.document.domain.MDocumentTemplate;
import com.template.app.modules.document.domain.TDocument;
import com.template.app.modules.document.infrastructure.cache.DocumentCacheService;
import com.template.app.modules.document.infrastructure.repository.DocumentRepository;
import com.template.app.modules.document.infrastructure.storage.FileStorageService;
import com.template.app.modules.document.presentation.dto.request.DocumentGenerateRequestDTO;
import com.template.app.modules.document.presentation.dto.response.DocumentResponseDTO;
import com.template.app.exception.SystemGlobalException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Document Service Tests")
class DocumentServiceImplTest {

    @Mock private DocumentRepository documentRepository;
    @Mock private DocumentCacheService cacheService;
    @Mock private FileStorageService fileStorageService;
    @Mock private ReportGenerationService reportGenerationService;
    @Mock private TemplateService templateService;
    @InjectMocks private DocumentServiceImpl documentService;

    @Test
    @DisplayName("ควรสร้างเอกสารจากเทมเพลต Jasper สำเร็จ")
    void shouldGenerateJasperDocumentSuccessfully() throws SystemGlobalException {
        // GIVEN
        DocumentGenerateRequestDTO request = new DocumentGenerateRequestDTO();
        request.setTemplateCode("QUOTATION");
        request.setDocumentSubType("QUOTATION");

        MDocumentTemplate template = new MDocumentTemplate();
        template.setTemplateCode("QUOTATION");
        template.setTemplateType("JASPER");
        template.setIsActive(true);
        template.setFilePath("/templates/quotation.jrxml");

        when(templateService.getTemplate("QUOTATION")).thenReturn(template);
        when(reportGenerationService.generatePDF(anyString(), any())).thenReturn("PDF_CONTENT".getBytes());
        when(fileStorageService.storeFile(anyString(), any(), any())).thenReturn("/storage/doc.pdf");
        when(documentRepository.create(any(), any())).thenReturn(new TDocument());

        // WHEN
        DocumentResponseDTO result = documentService.generateDocument(request);

        // THEN
        assertThat(result).isNotNull();
        verify(reportGenerationService, times(1)).generatePDF(anyString(), any());
        verify(fileStorageService, times(1)).storeFile(anyString(), any(), any());
    }
}
```

---

## 12. โมดูลที่ 10: Email Service

### 12.1 Service Layer: `EmailServiceImplTest.java`
```java
package com.template.app.modules.email.application.impl;

import com.template.app.modules.email.application.interfaces.EmailService;
import com.template.app.modules.email.domain.MEmailTemplate;
import com.template.app.modules.email.domain.TEmailHistory;
import com.template.app.modules.email.domain.enums.EmailStatus;
import com.template.app.modules.email.infrastructure.cache.EmailTemplateCacheService;
import com.template.app.modules.email.infrastructure.provider.EmailProvider;
import com.template.app.modules.email.infrastructure.repository.EmailHistoryRepository;
import com.template.app.modules.email.presentation.dto.request.EmailSendRequestDTO;
import com.template.app.modules.email.presentation.dto.response.EmailSendResponseDTO;
import com.template.app.exception.SystemGlobalException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Email Service Tests")
class EmailServiceImplTest {

    @Mock private EmailHistoryRepository emailRepository;
    @Mock private EmailTemplateCacheService templateCacheService;
    @Mock private EmailProvider emailProvider;
    @InjectMocks private EmailServiceImpl emailService;

    @Test
    @DisplayName("ควรส่งอีเมลจากเทมเพลตสำเร็จ")
    void shouldSendTemplateEmailSuccessfully() throws SystemGlobalException {
        // GIVEN
        EmailSendRequestDTO request = new EmailSendRequestDTO();
        request.setTemplateCode("WELCOME");
        request.setToEmail("test@example.com");
        request.getVariables().put("name", "John");

        MEmailTemplate template = new MEmailTemplate();
        template.setTemplateCode("WELCOME");
        template.setSubject("Welcome {name}");
        template.setBodyText("Hello {name}!");
        template.setFromEmail("noreply@example.com");

        when(templateCacheService.getTemplate("WELCOME", "th")).thenReturn(template);
        when(emailRepository.create(any(), any())).thenReturn(new TEmailHistory());
        when(emailProvider.sendEmail(anyString(), anyString(), anyString(), anyString(), anyString(), any()))
                .thenReturn(true);

        // WHEN
        EmailSendResponseDTO result = emailService.sendTemplateEmail(request);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(EmailStatus.SENT.name());
        verify(emailProvider, times(1)).sendEmail(anyString(), anyString(), anyString(), anyString(), anyString(), any());
    }
}
```

---

## 13. โมดูลที่ 11: Batch Jobs

### 13.1 Scheduler Test: `BatchSchedulerTest.java`
```java
package com.template.app.modules.batch.infrastructure.scheduler;

import com.template.app.modules.batch.infrastructure.cache.BatchJobLockCacheService;
import com.template.app.modules.batch.infrastructure.executor.BatchJobExecutor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Batch Scheduler Tests")
class BatchSchedulerTest {

    @Mock private BatchJobExecutor batchJobExecutor;
    @Mock private BatchJobLockCacheService lockCacheService;
    @InjectMocks private BatchSchedulerConfig scheduler;

    @Test
    @DisplayName("ควรเรียก executeJob สำหรับ batch001 ตาม Cron")
    void shouldExecuteBatch001OnSchedule() {
        // GIVEN
        when(lockCacheService.acquireLock(eq("batch001"), anyString(), any()))
                .thenReturn(true);

        // WHEN
        scheduler.executeBatch001();

        // THEN
        verify(batchJobExecutor, times(1)).executeJob("batch001");
        verify(lockCacheService, times(1)).releaseLock("batch001");
    }

    @Test
    @DisplayName("ควรข้ามการทำงานถ้า Lock ไม่สำเร็จ (อีก Instance กำลังทำงาน)")
    void shouldSkipIfLockFailed() {
        // GIVEN
        when(lockCacheService.acquireLock(eq("batch001"), anyString(), any()))
                .thenReturn(false);

        // WHEN
        scheduler.executeBatch001();

        // THEN
        verify(batchJobExecutor, never()).executeJob(anyString());
        verify(lockCacheService, never()).releaseLock(anyString());
    }
}
```

---

## 14. โมดูลที่ 12: Multi-Language (i18n)

### 14.1 Service Layer: `MessageServiceImplTest.java`
```java
package com.template.app.modules.i18n.application.impl;

import com.template.app.modules.i18n.application.interfaces.MessageService;
import com.template.app.modules.i18n.infrastructure.cache.MessageCacheService;
import com.template.app.modules.i18n.infrastructure.repository.TranslationRepository;
import com.template.app.modules.i18n.presentation.dto.response.MessageResponseDTO;
import com.template.app.exception.SystemGlobalException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Message Service Tests")
class MessageServiceImplTest {

    @Mock private MessageSource messageSource;
    @Mock private TranslationRepository translationRepository;
    @Mock private MessageCacheService cacheService;
    @InjectMocks private MessageServiceImpl messageService;

    @Test
    @DisplayName("ควรดึงข้อความจาก Cache ก่อน")
    void shouldGetMessageFromCacheFirst() throws SystemGlobalException {
        // GIVEN
        String messageKey = "job.status.open";
        String languageCode = "en";
        String cachedMessage = "Open";

        when(cacheService.getMessage(messageKey, languageCode)).thenReturn(cachedMessage);

        // WHEN
        MessageResponseDTO result = messageService.getMessage(messageKey, languageCode);

        // THEN
        assertThat(result.getMessageText()).isEqualTo("Open");
        assertThat(result.getFromCache()).isTrue();
        verify(messageSource, never()).getMessage(anyString(), any(), any(Locale.class));
    }

    @Test
    @DisplayName("ควรดึงข้อความจาก Resource Bundle เมื่อไม่มีใน Cache")
    void shouldGetMessageFromResourceBundleWhenNotCached() throws SystemGlobalException {
        // GIVEN
        String messageKey = "job.status.open";
        String languageCode = "th";

        when(cacheService.getMessage(messageKey, languageCode)).thenReturn(null);
        when(messageSource.getMessage(eq(messageKey), any(), any(Locale.class))).thenReturn("เปิดใบงาน");

        // WHEN
        MessageResponseDTO result = messageService.getMessage(messageKey, languageCode);

        // THEN
        assertThat(result.getMessageText()).isEqualTo("เปิดใบงาน");
        assertThat(result.getFromCache()).isFalse();
    }
}
```

---

## 15. โมดูลที่ 13: IoT & GPS Tracking

### 15.1 Domain Layer: `TGPSDataTest.java`
```java
package com.template.app.modules.iot.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@DisplayName("GPS Data Domain Tests")
class TGPSDataTest {

    @Test
    @DisplayName("ควรคำนวณระยะทางระหว่างสองจุดได้ถูกต้อง")
    void shouldCalculateDistanceCorrectly() {
        // GIVEN
        BigDecimal lat1 = new BigDecimal("13.736717");
        BigDecimal lon1 = new BigDecimal("100.523186");
        BigDecimal lat2 = new BigDecimal("13.756331");
        BigDecimal lon2 = new BigDecimal("100.501765");

        // WHEN
        double distance = TGPSData.calculateDistance(lat1, lon1, lat2, lon2);

        // THEN
        assertThat(distance).isBetween(2.8, 3.0); // ~2.9 km
    }

    @Test
    @DisplayName("ควรตรวจสอบว่าอุปกรณ์กำลังเคลื่อนที่ (speed > 5 km/h)")
    void shouldDetectMoving() {
        // GIVEN
        TGPSData gpsData = new TGPSData();
        gpsData.setSpeed(new BigDecimal("10.5"));

        // WHEN
        boolean isMoving = gpsData.isMoving();

        // THEN
        assertThat(isMoving).isTrue();
    }

    @Test
    @DisplayName("ควรตรวจสอบว่าอุปกรณ์หยุดนิ่ง (speed < 1 km/h)")
    void shouldDetectStopped() {
        // GIVEN
        TGPSData gpsData = new TGPSData();
        gpsData.setSpeed(new BigDecimal("0.5"));

        // WHEN
        boolean isStopped = gpsData.isStopped();

        // THEN
        assertThat(isStopped).isTrue();
    }
}
```

### 15.2 Service Layer: `MqttServiceImplTest.java`
```java
package com.template.app.modules.iot.application.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.template.app.modules.iot.application.interfaces.MqttService;
import com.template.app.modules.iot.domain.MIoTDevice;
import com.template.app.modules.iot.infrastructure.cache.DeviceCacheService;
import com.template.app.modules.iot.infrastructure.cache.DeviceLocationCacheService;
import com.template.app.modules.iot.infrastructure.repository.GpsDataRepository;
import com.template.app.modules.iot.infrastructure.repository.IotDeviceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("MQTT Service Tests")
class MqttServiceImplTest {

    @Mock private IotDeviceRepository deviceRepository;
    @Mock private GpsDataRepository gpsDataRepository;
    @Mock private DeviceCacheService cacheService;
    @Mock private DeviceLocationCacheService locationCacheService;
    @Mock private ObjectMapper objectMapper;
    @InjectMocks private MqttServiceImpl mqttService;

    @Test
    @DisplayName("ควรประมวลผลข้อมูล GPS จาก MQTT สำเร็จ")
    void shouldProcessGpsDataSuccessfully() throws Exception {
        // GIVEN
        String payload = "{\"deviceId\":\"GPS-001\",\"latitude\":\"13.736717\",\"longitude\":\"100.523186\",\"speed\":\"10.5\"}";
        Map<String, Object> headers = new HashMap<>();
        headers.put("mqtt_receivedTopic", "gps/data");
        Message<String> message = new GenericMessage<>(payload, headers);

        MIoTDevice device = new MIoTDevice();
        device.setId(UUID.randomUUID());
        device.setDeviceId("GPS-001");

        when(deviceRepository.findByDeviceId("GPS-001")).thenReturn(Optional.of(device));

        // WHEN
        mqttService.handleMessage(message);

        // THEN
        verify(deviceRepository, times(1)).updateDeviceLocation(any(), any(), any(), any(), any(), any());
        verify(gpsDataRepository, times(1)).save(any());
        verify(locationCacheService, times(1)).updateDeviceLocation(any(), any());
    }
}
```

---

## 16. โมดูลที่ 14: Web Order System (WOS)

### 16.1 Domain Layer: `TWebOrderTest.java`
```java
package com.template.app.modules.weborder.domain;

import com.template.app.modules.weborder.domain.enums.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Web Order Domain Tests")
class TWebOrderTest {

    @Test
    @DisplayName("ควรคำนวณยอดรวมออเดอร์จากรายการสินค้า")
    void shouldCalculateOrderTotalFromItems() {
        // GIVEN
        TWebOrder order = new TWebOrder();
        order.setTax(new BigDecimal("70.00"));
        order.setShippingCost(new BigDecimal("50.00"));
        order.setDiscount(new BigDecimal("100.00"));

        TWebOrderItem item1 = new TWebOrderItem();
        item1.setTotalPrice(new BigDecimal("1000.00"));
        TWebOrderItem item2 = new TWebOrderItem();
        item2.setTotalPrice(new BigDecimal("500.00"));
        order.getItems().add(item1);
        order.getItems().add(item2);

        // WHEN
        order.calculateTotal();

        // THEN
        assertThat(order.getSubtotal()).isEqualTo(new BigDecimal("1500.00"));
        assertThat(order.getTotal()).isEqualTo(new BigDecimal("1520.00")); // 1500 + 70 + 50 - 100
    }

    @Test
    @DisplayName("ควรยกเลิกออเดอร์ได้เมื่อสถานะ PENDING")
    void shouldCancelWhenStatusPending() {
        // GIVEN
        TWebOrder order = new TWebOrder();
        order.setStatus(OrderStatus.PENDING);

        // WHEN
        order.cancel("Customer requested cancellation");

        // THEN
        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCELLED);
        assertThat(order.getCancellationReason()).isEqualTo("Customer requested cancellation");
    }

    @Test
    @DisplayName("ควรเปลี่ยนสถานะออเดอร์ได้")
    void shouldChangeOrderStatus() {
        // GIVEN
        TWebOrder order = new TWebOrder();
        order.setStatus(OrderStatus.PENDING);

        // WHEN
        order.changeStatus(OrderStatus.PROCESSING);

        // THEN
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PROCESSING);
    }

    @Test
    @DisplayName("ควรโยน Exception เมื่อเปลี่ยนสถานะจาก DELIVERED")
    void shouldThrowExceptionWhenChangeFromDelivered() {
        // GIVEN
        TWebOrder order = new TWebOrder();
        order.setStatus(OrderStatus.DELIVERED);

        // WHEN & THEN
        assertThatThrownBy(() -> order.changeStatus(OrderStatus.PROCESSING))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Cannot change status of delivered");
    }
}
```

### 16.2 Service Layer: `OrderServiceImplTest.java`
```java
package com.template.app.modules.weborder.application.impl;

import com.template.app.modules.weborder.application.interfaces.OrderService;
import com.template.app.modules.weborder.domain.TShoppingCart;
import com.template.app.modules.weborder.domain.TShoppingCartItem;
import com.template.app.modules.weborder.domain.TWebOrder;
import com.template.app.modules.weborder.infrastructure.cache.OrderCacheService;
import com.template.app.modules.weborder.infrastructure.repository.CartRepository;
import com.template.app.modules.weborder.infrastructure.repository.OrderRepository;
import com.template.app.modules.weborder.presentation.dto.request.OrderCreateRequestDTO;
import com.template.app.modules.weborder.presentation.dto.response.OrderResponseDTO;
import com.template.app.exception.SystemGlobalException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Order Service Tests")
class OrderServiceImplTest {

    @Mock private OrderRepository orderRepository;
    @Mock private CartRepository cartRepository;
    @Mock private OrderCacheService cacheService;
    @InjectMocks private OrderServiceImpl orderService;

    @Test
    @DisplayName("ควรสร้างออเดอร์จากตะกร้าสำเร็จ")
    void shouldCreateOrderFromCartSuccessfully() throws SystemGlobalException {
        // GIVEN
        String cartId = "cart_123";
        OrderCreateRequestDTO request = new OrderCreateRequestDTO();
        request.setCartId(cartId);
        request.setCustomerId(UUID.randomUUID());
        request.setShippingAddress("123 Main St, Bangkok");

        TShoppingCart cart = new TShoppingCart();
        cart.setId(UUID.randomUUID());
        cart.setCartId(cartId);
        cart.setSubtotal(new BigDecimal("1500.00"));
        cart.setTotal(new BigDecimal("1500.00"));

        TShoppingCartItem cartItem = new TShoppingCartItem();
        cartItem.setItemId(UUID.randomUUID());
        cartItem.setQuantity(2);
        cartItem.setUnitPrice(new BigDecimal("750.00"));
        cartItem.setTotalPrice(new BigDecimal("1500.00"));
        cart.getItems().add(cartItem);

        when(cartRepository.findByCartId(cartId)).thenReturn(Optional.of(cart));
        when(orderRepository.create(any(), any())).thenReturn(new TWebOrder());

        // WHEN
        OrderResponseDTO result = orderService.createOrder(request);

        // THEN
        assertThat(result).isNotNull();
        verify(orderRepository, times(1)).create(any(), any());
        verify(cartRepository, times(1)).clearCart(cart.getId());
        verify(cacheService, times(1)).saveOrder(any());
    }

    @Test
    @DisplayName("ควรโยน Exception เมื่อตะกร้าสินค้าว่าง")
    void shouldThrowExceptionWhenCartEmpty() throws SystemGlobalException {
        // GIVEN
        String cartId = "cart_empty";
        OrderCreateRequestDTO request = new OrderCreateRequestDTO();
        request.setCartId(cartId);

        TShoppingCart cart = new TShoppingCart();
        cart.setCartId(cartId);
        cart.setItems(new ArrayList<>()); // Empty cart

        when(cartRepository.findByCartId(cartId)).thenReturn(Optional.of(cart));

        // WHEN & THEN
        assertThatThrownBy(() -> orderService.createOrder(request))
            .isInstanceOf(SystemGlobalException.class)
            .hasMessageContaining("Cart is empty");
    }
}
```

---

## 17. การรัน Tests และ CI/CD Integration

### 17.1 Maven Commands

```bash
# รัน Tests ทั้งหมด
mvn clean test

# รัน Tests เฉพาะโมดูล Auth
mvn test -Dtest="com.template.app.modules.auth.**"

# รัน Tests พร้อม Coverage Report (Jacoco)
mvn clean test jacoco:report

# รัน Tests เฉพาะ Integration Tests
mvn test -Dtest="**/*IntegrationTest.java"

# รัน Tests ข้าม Unit Tests (เฉพาะ Integration)
mvn test -Dgroups="integration"
```

### 17.2 Jenkins Pipeline (Jenkinsfile)
```groovy
pipeline {
    agent any
    
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage('Unit Tests') {
            steps {
                sh 'mvn test -Dgroups="unit"'
            }
        }
        stage('Integration Tests') {
            steps {
                sh 'mvn test -Dgroups="integration"'
            }
        }
        stage('Coverage Report') {
            steps {
                sh 'mvn jacoco:report'
            }
        }
        stage('Quality Gate') {
            steps {
                // ตรวจสอบ Coverage ต้อง >= 80%
                sh '''
                    COVERAGE=$(cat target/site/jacoco/index.html | grep -oP 'Total[^%]*\\K[0-9]+')
                    if [ "$COVERAGE" -lt 80 ]; then
                        echo "Coverage $COVERAGE% is below 80% threshold"
                        exit 1
                    fi
                '''
            }
        }
    }
}
```

### 17.3 Test Annotations Reference

| Annotation | คำอธิบาย |
|------------|----------|
| `@Test` | ระบุว่าเป็น Method Test |
| `@DisplayName` | ตั้งชื่อแสดงผล (ภาษาไทย/อังกฤษ) |
| `@ExtendWith(MockitoExtension.class)` | เปิดใช้งาน Mockito |
| `@Mock` | สร้าง Mock Object |
| `@InjectMocks` | Inject Mocks เข้าไปใน Class ที่ทดสอบ |
| `@WebMvcTest` | ทดสอบ Controller Layer |
| `@DataJpaTest` | ทดสอบ JPA Repository |
| `@SpringBootTest` | ทดสอบแบบ Integration เต็มรูปแบบ |
| `@TestContainers` | ใช้ Docker Container สำหรับ Integration Test |

---

## 18. สรุป Coverage Targets

| โมดูล | Domain (%) | Application (%) | Infrastructure (%) | Presentation (%) | Overall (%) |
|-------|-----------|-----------------|-------------------|------------------|-------------|
| **Auth** | 95% | 90% | 75% | 85% | **86%** |
| **Job** | 95% | 90% | 75% | 80% | **85%** |
| **Customer** | 95% | 90% | 70% | 80% | **84%** |
| **Quotation** | 95% | 90% | 75% | 80% | **85%** |
| **Purchase** | 90% | 90% | 75% | 80% | **84%** |
| **Inventory** | 95% | 90% | 70% | 80% | **84%** |
| **Payment** | 95% | 90% | 75% | 80% | **85%** |
| **Dashboard** | N/A | 85% | 70% | 75% | **77%** |
| **Document** | 90% | 85% | 70% | 75% | **80%** |
| **Email** | N/A | 85% | 70% | 75% | **77%** |
| **Batch** | N/A | 80% | 70% | 75% | **75%** |
| **i18n** | 90% | 85% | 70% | 75% | **80%** |
| **IoT** | 90% | 80% | 65% | 70% | **76%** |
| **WOS** | 95% | 90% | 70% | 80% | **84%** |
| **Average** | **93%** | **87%** | **72%** | **79%** | **82%** |

---

## ✅ สรุป

เอกสารนี้ครอบคลุมการออกแบบ Unit Test สำหรับทั้ง 14 โมดูล ประกอบด้วย:

- **ตัวอย่างโค้ด Test** ครบทุก Layer (Domain, Application, Infrastructure, Presentation)
- **Best Practices** – Given-When-Then, Mockito, AssertJ
- **Coverage Targets** – ≥ 80% โดยรวม
- **Integration with CI/CD** – Jenkins Pipeline
- **รัน Tests** – Maven Commands

ระบบพร้อมสำหรับการพัฒนาและทดสอบอย่างมืออาชีพ 🚀

---

**ผู้เขียน:** Kongnakorn Jantakun  
**วันที่:** 2026-07-04  
**เวอร์ชัน:** 1.0 (Unit Test Documentation)  
**สถานะ:** ฉบับสมบูรณ์ ✅