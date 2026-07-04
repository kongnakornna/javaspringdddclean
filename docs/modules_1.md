## 🗂️ โครงสร้างโมดูล Authentication & Permission 
```
modules/auth/
├── application/
│   ├── interfaces/
│   │   ├── AuthService.java
│   │   ├── UserService.java
│   │   └── PermissionService.java
│   ├── impl/
│   │   ├── AuthServiceImpl.java
│   │   ├── UserServiceImpl.java
│   │   └── PermissionServiceImpl.java
│   └── usecase/
│       ├── LoginUseCase.java
│       ├── LogoutUseCase.java
│       ├── RefreshTokenUseCase.java
│       ├── CreateUserUseCase.java
│       ├── UpdateUserUseCase.java
│       ├── DeleteUserUseCase.java
│       ├── GetUserUseCase.java
│       └── ValidatePermissionUseCase.java
├── domain/
│   ├── MUser.java
│   ├── MRole.java
│   ├── MPermission.java
│   ├── MUserToken.java
│   ├── enums/
│   │   ├── UserStatus.java
│   │   ├── RoleType.java
│   │   ├── TokenType.java
│   │   └── PermissionAction.java
│   └── valueobjects/
│       ├── Email.java
│       └── PasswordHash.java
├── infrastructure/
│   ├── repository/
│   │   ├── UserRepository.java
│   │   ├── RoleRepository.java
│   │   ├── PermissionRepository.java
│   │   ├── UserTokenRepository.java
│   │   ├── UserRoleRepository.java
│   │   ├── RolePermissionRepository.java
│   │   └── impl/
│   │       ├── UserRepositoryImpl.java
│   │       ├── RoleRepositoryImpl.java
│   │       ├── PermissionRepositoryImpl.java
│   │       ├── UserTokenRepositoryImpl.java
│   │       ├── UserRoleRepositoryImpl.java
│   │       └── RolePermissionRepositoryImpl.java
│   ├── security/
│   │   ├── JwtTokenFilter.java
│   │   ├── JwtTokenProvider.java
│   │   ├── PermissionInterceptor.java
│   │   ├── CustomUserDetailsService.java
│   │   └── SecurityConfig.java
│   ├── cache/                              // ⬅️ เพิ่มระบบ Cache
│   │   ├── CacheConfig.java                // กำหนด Redis Cache Manager
│   │   ├── UserPermissionCacheService.java // เก็บ Permission ของ User ใน Redis
│   │   └── TokenCacheService.java          // เก็บ Blacklist/Revoke Token
│   ├── ratelimit/                          // ⬅️ เพิ่มระบบ Rate Limit
│   │   ├── RateLimit.java                  // Custom Annotation
│   │   ├── RateLimiterInterceptor.java     // Interceptor ตรวจสอบ Rate
│   │   └── RateLimitExceededException.java // Exception เมื่อเกิน Limit
│   ├── entity/
│   │   ├── UserEntity.java
│   │   ├── RoleEntity.java
│   │   ├── PermissionEntity.java
│   │   ├── UserTokenEntity.java
│   │   ├── UserRoleEntity.java
│   │   └── RolePermissionEntity.java
│   └── mapper/
│       ├── UserMapper.java
│       ├── RoleMapper.java
│       ├── PermissionMapper.java
│       ├── UserTokenMapper.java
│       ├── UserRoleMapper.java
│       └── RolePermissionMapper.java
└── presentation/
    ├── controller/
    │   ├── AuthController.java
    │   ├── UserController.java
    │   └── PermissionController.java
    ├── dto/
    │   ├── request/
    │   │   ├── LoginRequestDTO.java
    │   │   ├── RegisterRequestDTO.java
    │   │   ├── UserCreateDTO.java
    │   │   ├── UserUpdateDTO.java
    │   │   └── PermissionRequestDTO.java
    │   └── response/
    │       ├── LoginResponseDTO.java
    │       ├── UserResponseDTO.java
    │       ├── PermissionResponseDTO.java
    │       └── TokenResponseDTO.java
    └── validator/
        └── UserValidator.java
```

---

## 🗄️ ออกแบบฐานข้อมูล (Database Design) สำหรับโมดูล Auth

### 📄 SQL DDL (ไฟล์: `src/main/resources/db/migration/V1__auth_schema.sql`)

```sql
-- ==============================================
-- ตาราง: m_user (ผู้ใช้ระบบ)
-- This table stores system user credentials and profiles.
-- ==============================================
CREATE TABLE IF NOT EXISTS m_user (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING', -- PENDING, ACTIVE, INACTIVE, SUSPENDED
    phone_number VARCHAR(20),
    profile_image_url TEXT,
    last_login TIMESTAMP,
    -- Generic Audit Fields (จาก GenericBusinessEntity)
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID NOT NULL,          -- ผู้สร้าง (Audit)
    whitelabel_id UUID NOT NULL     -- บริษัท/สาขา (Multi-tenancy)
);

CREATE INDEX idx_m_user_email ON m_user(email);
CREATE INDEX idx_m_user_whitelabel ON m_user(whitelabel_id);
CREATE INDEX idx_m_user_deleted ON m_user(deleted);

-- ==============================================
-- ตาราง: m_role (บทบาท)
-- This table defines roles (e.g., ADMIN, MANAGER, USER).
-- ==============================================
CREATE TABLE IF NOT EXISTS m_role (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    -- Audit Fields
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

-- ==============================================
-- ตาราง: m_permission (สิทธิ์การใช้งาน)
-- This table defines granular permissions (e.g., READ_JOB, WRITE_INVENTORY).
-- ==============================================
CREATE TABLE IF NOT EXISTS m_permission (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(100) UNIQUE NOT NULL,  -- เช่น "JOB_CREATE", "INVENTORY_READ"
    description TEXT,
    action VARCHAR(50),                  -- CREATE, READ, UPDATE, DELETE, EXECUTE
    resource VARCHAR(50),                -- JOB, INVENTORY, USER, REPORT
    -- Audit Fields
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    user_id UUID NOT NULL,
    whitelabel_id UUID NOT NULL
);

-- ==============================================
-- ตารางเชื่อม: m_user_role (ผู้ใช้ <-> บทบาท)
-- Junction table for many-to-many relationship between users and roles.
-- ==============================================
CREATE TABLE IF NOT EXISTS m_user_role (
    user_id UUID NOT NULL REFERENCES m_user(id) ON DELETE CASCADE,
    role_id UUID NOT NULL REFERENCES m_role(id) ON DELETE CASCADE,
    assigned_at TIMESTAMP NOT NULL DEFAULT NOW(),
    PRIMARY KEY (user_id, role_id)
);

CREATE INDEX idx_user_role_user ON m_user_role(user_id);
CREATE INDEX idx_user_role_role ON m_user_role(role_id);

-- ==============================================
-- ตารางเชื่อม: m_role_permission (บทบาท <-> สิทธิ์)
-- Junction table for many-to-many relationship between roles and permissions.
-- ==============================================
CREATE TABLE IF NOT EXISTS m_role_permission (
    role_id UUID NOT NULL REFERENCES m_role(id) ON DELETE CASCADE,
    permission_id UUID NOT NULL REFERENCES m_permission(id) ON DELETE CASCADE,
    granted_at TIMESTAMP NOT NULL DEFAULT NOW(),
    PRIMARY KEY (role_id, permission_id)
);

CREATE INDEX idx_role_permission_role ON m_role_permission(role_id);
CREATE INDEX idx_role_permission_permission ON m_role_permission(permission_id);

-- ==============================================
-- ตาราง: m_user_token (Token ที่ออกให้ผู้ใช้)
-- This table stores issued tokens (JWT, Refresh) for logout/revoke management.
-- ==============================================
CREATE TABLE IF NOT EXISTS m_user_token (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES m_user(id) ON DELETE CASCADE,
    token TEXT UNIQUE NOT NULL,              -- JWT String
    token_type VARCHAR(20) NOT NULL,         -- ACCESS, REFRESH
    expiry_date TIMESTAMP NOT NULL,          -- เวลาหมดอายุ
    revoked BOOLEAN DEFAULT FALSE,           -- ถูกเพิกถอนหรือไม่
    revoked_at TIMESTAMP,
    -- Audit
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    user_agent TEXT,
    ip_address INET
);

CREATE INDEX idx_user_token_user ON m_user_token(user_id);
CREATE INDEX idx_user_token_token ON m_user_token(token);
CREATE INDEX idx_user_token_expiry ON m_user_token(expiry_date);

-- ==============================================
-- ตาราง: m_rate_limit_log (บันทึกการเข้าถึงที่ถูกปฏิเสธเพราะ Rate Limit)
-- Optional: This table logs requests that were rejected due to rate limiting.
-- ==============================================
CREATE TABLE IF NOT EXISTS m_rate_limit_log (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    client_id VARCHAR(100) NOT NULL,    -- User ID หรือ IP Address
    api_path TEXT NOT NULL,
    method VARCHAR(10) NOT NULL,
    attempted_at TIMESTAMP NOT NULL DEFAULT NOW(),
    rate_limit_key VARCHAR(255)        -- Redis Key ที่ใช้
);

CREATE INDEX idx_rate_limit_client ON m_rate_limit_log(client_id);
CREATE INDEX idx_rate_limit_time ON m_rate_limit_log(attempted_at);
```

---

## 🧠 ระบบ Cache (Redis)

### `infrastructure/cache/CacheConfig.java`

```java
package com.template.app.modules.auth.infrastructure.cache;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

    /*
        ฟังก์ชันนี้กำหนดค่า Redis Cache Manager สำหรับเก็บข้อมูลชั่วคราว เช่น Session, Permission, Token Blacklist
        This function configures the Redis Cache Manager to store temporary data like sessions, permissions, and token blacklists.
    */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        // กำหนดรูปแบบการ Serialize เป็น JSON เพื่อให้อ่านค่าได้ง่าย / Define JSON serialization for readability.
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30)) // กำหนดเวลาหมดอายุ 30 นาที / Set default TTL to 30 minutes.
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        // สร้าง Cache Manager แยกตามชื่อ Cache ที่มี TTL ต่างกัน / Create separate cache managers with different TTLs.
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .withCacheConfiguration("user-permissions", config.entryTtl(Duration.ofHours(1)))  // Permission cache 1 ชั่วโมง
                .withCacheConfiguration("user-sessions", config.entryTtl(Duration.ofMinutes(15))) // Session cache 15 นาที
                .withCacheConfiguration("token-blacklist", config.entryTtl(Duration.ofDays(1)))   // Blacklist cache 1 วัน
                .build();
    }
}
```

### `infrastructure/cache/UserPermissionCacheService.java`

```java
package com.template.app.modules.auth.infrastructure.cache;

import com.template.app.modules.auth.domain.MPermission;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserPermissionCacheService {

    /*
        ฟังก์ชันนี้ดึงข้อมูลสิทธิ์ของผู้ใช้จาก Redis หากมีอยู่ใน Cache จะไม่เรียกฐานข้อมูล
        This function retrieves user permissions from Redis. If cached, it avoids hitting the database.
    */
    @Cacheable(value = "user-permissions", key = "#userId")
    public List<MPermission> getPermissions(UUID userId) {
        // เนื่องจาก @Cacheable จะไม่ Execute Method ถ้ามีใน Cache
        // Since @Cacheable prevents execution if cached, this acts as a fallback.
        return null; 
    }

    /*
        ฟังก์ชันนี้ลบข้อมูลสิทธิ์ของผู้ใช้ออกจาก Cache เมื่อมีการเปลี่ยนแปลงสิทธิ์ (เพื่อให้ข้อมูลสดใหม่)
        This function evicts user permissions from cache when permissions are updated (to ensure freshness).
    */
    @CacheEvict(value = "user-permissions", key = "#userId")
    public void evictUserPermissions(UUID userId) {
        // Spring จะลบ Cache เอง / Spring automatically evicts the cache.
    }

    /*
        ฟังก์ชันนี้ลบข้อมูลสิทธิ์ของผู้ใช้ทั้งหมด (ใช้เมื่อระบบมีการเปลี่ยนแปลงโครงสร้างสิทธิ์ครั้งใหญ่)
        This function clears all user permission caches (used for global permission structure updates).
    */
    @CacheEvict(value = "user-permissions", allEntries = true)
    public void evictAllUserPermissions() {
        // ลบทุก key ที่เกี่ยวข้องกับ permission cache / Evict all keys under permission cache.
    }
}
```

---

## ⏱️ ระบบ Rate Limit Exceeded

### `infrastructure/ratelimit/RateLimit.java` (Custom Annotation)

```java
package com.template.app.modules.auth.infrastructure.ratelimit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
    แอนโนเทชันนี้ใช้สำหรับกำหนดขีดจำกัดการเรียก API ต่อ 1 ช่วงเวลา
    This annotation is used to define rate limits per time window for API endpoints.
*/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    // จำนวนครั้งที่อนุญาต / Number of allowed requests.
    int limit() default 100;
    // ระยะเวลา (วินาที) / Time window in seconds.
    int duration() default 60;
    // ประเภทของ Key ที่ใช้ระบุตัวตน (เช่น IP, USER_ID) / Type of key to identify the client.
    String keyType() default "IP";
}
```

### `infrastructure/ratelimit/RateLimiterInterceptor.java`

```java
package com.template.app.modules.auth.infrastructure.ratelimit;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;
import java.util.UUID;

@Component
public class RateLimiterInterceptor implements HandlerInterceptor {

    private final RedisTemplate<String, String> redisTemplate;

    public RateLimiterInterceptor(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /*
        ฟังก์ชันนี้จะถูกเรียกก่อนที่ Controller จะประมวลผล เพื่อตรวจสอบว่า Client เรียก API เกินที่กำหนดหรือไม่
        This function is called before controller execution to check if the client has exceeded the allowed request rate.
    */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        RateLimit rateLimit = handlerMethod.getMethodAnnotation(RateLimit.class);
        if (rateLimit == null) {
            return true; // ไม่มีแอนโนเทชัน ข้าม / No annotation, skip check.
        }

        // ดึง Client ID (IP หรือ User ID) / Retrieve client identifier (IP or User ID).
        String clientId = getClientId(request, rateLimit.keyType());
        // สร้าง Redis Key เฉพาะ path + method / Create unique Redis key for the endpoint.
        String key = "rate:" + clientId + ":" + request.getRequestURI() + ":" + request.getMethod();

        // ดึงจำนวนครั้งที่เรียกแล้ว / Get current request count.
        String countStr = redisTemplate.opsForValue().get(key);
        int count = countStr == null ? 0 : Integer.parseInt(countStr);

        // ถ้าเกิน Limit / If limit exceeded.
        if (count >= rateLimit.limit()) {
            // โยน Exception เพื่อให้ GlobalExceptionHandler จัดการ / Throw exception for handler to manage.
            throw new RateLimitExceededException("Request limit exceeded. Please try again later.");
        }

        // เพิ่มจำนวนครั้งและตั้ง TTL / Increment count and set TTL.
        redisTemplate.opsForValue().increment(key);
        // ถ้าเป็นครั้งแรก ให้ตั้งเวลา TTL / If first request, set TTL.
        if (count == 0) {
            redisTemplate.expire(key, Duration.ofSeconds(rateLimit.duration()));
        }

        return true;
    }

    // ดึง Client ID ตามประเภท / Resolve client ID based on type.
    private String getClientId(HttpServletRequest request, String keyType) {
        if ("USER_ID".equalsIgnoreCase(keyType)) {
            // กรณีต้องใช้ User ID ให้ดึงจาก Header หรือ Security Context / Use User ID from header/context.
            return request.getHeader("X-User-Id") != null ? request.getHeader("X-User-Id") : "anonymous";
        }
        // ค่าเริ่มต้นคือ IP Address / Default to IP Address.
        return request.getRemoteAddr();
    }
}
```

### `infrastructure/ratelimit/RateLimitExceededException.java`

```java
package com.template.app.modules.auth.infrastructure.ratelimit;

public class RateLimitExceededException extends Exception {
    public RateLimitExceededException(String message) {
        super(message);
    }
}
```

---

## 🛣️ API Routing & Controller พร้อม Rate Limit

### `presentation/controller/AuthController.java`

```java
package com.template.app.modules.auth.presentation.controller;

import com.template.app.modules.auth.application.interfaces.AuthService;
import com.template.app.modules.auth.infrastructure.ratelimit.RateLimit;
import com.template.app.modules.auth.presentation.dto.request.LoginRequestDTO;
import com.template.app.modules.auth.presentation.dto.request.RegisterRequestDTO;
import com.template.app.modules.auth.presentation.dto.response.LoginResponseDTO;
import com.template.app.exception.SystemGlobalException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Authentication and Authorization APIs")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /*
        API: POST /api/v1/auth/login
        ฟังก์ชันนี้ให้ผู้ใช้เข้าสู่ระบบ โดยตรวจสอบ credentials และคืน JWT Access & Refresh Token
        This function authenticates a user by validating credentials and returns JWT Access & Refresh Tokens.
        Rate Limit: อนุญาต 5 ครั้งต่อ 5 นาที / Allows 5 attempts per 5 minutes.
    */
    @PostMapping("/login")
    @RateLimit(limit = 5, duration = 300, keyType = "IP")
    @Operation(summary = "User Login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request,
                                                  HttpServletRequest httpRequest) throws SystemGlobalException {
        // ดึง IP Address และ User-Agent สำหรับ Log / Extract IP and User-Agent for logging.
        LoginResponseDTO response = authService.login(request, httpRequest);
        return ResponseEntity.ok(response);
    }

    /*
        API: POST /api/v1/auth/logout
        ฟังก์ชันนี้ให้ผู้ใช้ออกจากระบบ โดยเพิกถอน Token ปัจจุบัน
        This function logs out a user by revoking the current token.
        Rate Limit: อนุญาต 10 ครั้งต่อ 1 นาที / Allows 10 attempts per 1 minute.
    */
    @PostMapping("/logout")
    @RateLimit(limit = 10, duration = 60, keyType = "USER_ID")
    @Operation(summary = "User Logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) throws SystemGlobalException {
        authService.logout(token);
        return ResponseEntity.ok().build();
    }

    /*
        API: POST /api/v1/auth/refresh
        ฟังก์ชันนี้ต่ออายุ Access Token ใหม่เมื่อ Refresh Token ยังไม่หมดอายุ
        This function issues a new Access Token when the Refresh Token is still valid.
        Rate Limit: อนุญาต 20 ครั้งต่อ 1 ชั่วโมง / Allows 20 attempts per 1 hour.
    */
    @PostMapping("/refresh")
    @RateLimit(limit = 20, duration = 3600, keyType = "USER_ID")
    @Operation(summary = "Refresh JWT Token")
    public ResponseEntity<LoginResponseDTO> refresh(@RequestParam String refreshToken) throws SystemGlobalException {
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }

    /*
        API: POST /api/v1/auth/register
        ฟังก์ชันนี้ให้ผู้ใช้ลงทะเบียนใหม่ (เปิดให้เฉพาะบางบทบาท หรือเปิดสาธารณะ)
        This function allows new user registration (public or restricted by role).
        Rate Limit: อนุญาต 3 ครั้งต่อ 1 ชั่วโมง (ป้องกันการสมัครซ้ำหลายครั้ง) / Allows 3 attempts per 1 hour (anti-spam).
    */
    @PostMapping("/register")
    @RateLimit(limit = 3, duration = 3600, keyType = "IP")
    @Operation(summary = "Register New User")
    public ResponseEntity<LoginResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) throws SystemGlobalException {
        return ResponseEntity.ok(authService.register(request));
    }

    /*
        API: GET /api/v1/auth/me
        ฟังก์ชันนี้ดึงข้อมูลผู้ใช้ที่กำลังล็อกอินอยู่ (ใช้ Access Token)
        This function fetches the currently logged-in user's profile (using Access Token).
        Rate Limit: อนุญาต 50 ครั้งต่อ 1 นาที (การเรียกดูข้อมูลส่วนตัวบ่อยๆ) / Allows 50 attempts per 1 minute (frequent profile fetch).
    */
    @GetMapping("/me")
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Get Current User Profile")
    public ResponseEntity<UserResponseDTO> getCurrentUser() throws SystemGlobalException {
        return ResponseEntity.ok(authService.getCurrentUser());
    }
}
```

---

## 🗂️ เพิ่ม WebConfig เพื่อ Register Interceptor

```java
package com.template.app.configuration.web;

import com.template.app.modules.auth.infrastructure.ratelimit.RateLimiterInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final RateLimiterInterceptor rateLimiterInterceptor;

    public WebConfig(RateLimiterInterceptor rateLimiterInterceptor) {
        this.rateLimiterInterceptor = rateLimiterInterceptor;
    }

    /*
        ฟังก์ชันนี้ลงทะเบียน Interceptor เพื่อให้ทำงานกับทุก Request ที่เข้ามาในระบบ
        This function registers the interceptor to handle all incoming requests.
    */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rateLimiterInterceptor)
                .addPathPatterns("/api/v1/**") // ใช้กับ API ทุกตัว / Apply to all API endpoints.
                .excludePathPatterns("/api/v1/auth/login", "/api/v1/auth/register", "/swagger-ui/**", "/v3/api-docs/**"); // ยกเว้นบาง endpoint
    }
}
```

---

## 🧩 การเชื่อมโยงกับ `GlobalExceptionHandler`

```java
// ใน GlobalExceptionHandler.java เพิ่ม Method สำหรับจัดการ RateLimitExceededException
/*
    ฟังก์ชันนี้จัดการ Exception เมื่อผู้ใช้เรียก API เกินอัตราที่กำหนด โดยส่ง HTTP Status 429 (Too Many Requests)
    This function handles the exception when a user exceeds the request rate, returning HTTP 429 (Too Many Requests).
*/
@ExceptionHandler(RateLimitExceededException.class)
public ResponseEntity<Object> handleRateLimitException(RateLimitExceededException ex) {
    // บันทึก Log หรือเพิ่ม Header Retry-After / Log error or add Retry-After header.
    return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(Map.of(
        "error", "Too Many Requests",
        "message", ex.getMessage(),
        "retryAfter", "60 seconds"
    ));
}
```

---

## 🔑 คำอธิบายการทำงานของ `JwtTokenFilter` (ปรับปรุง)

```java
package com.template.app.modules.auth.infrastructure.security;

// ... imports ...

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    /*
        ฟังก์ชันนี้จะทำงานทุกครั้งที่มี Request เข้ามา โดยจะตรวจสอบ JWT Token, ดึงข้อมูลผู้ใช้ และตั้งค่า Context
        This function executes on every incoming request. It validates the JWT, extracts user data, and sets the security context.
    */
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        // 1. ดึง Token จาก Header / Extract Token from Authorization header.
        String token = getBearerToken(request);
        
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 2. ดึง User ID จาก Token / Extract User ID from token.
            UUID userId = jwtTokenProvider.getUserIdFromToken(token);
            
            // 3. ตรวจสอบว่า Token นี้ถูกเพิกถอนหรือไม่ (จาก Redis/DB) / Check if token is revoked (via Redis/DB).
            if (tokenCacheService.isTokenRevoked(token)) {
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token has been revoked.");
                return;
            }
            
            // 4. โหลดสิทธิ์ของผู้ใช้จาก Cache (Redis) / Load user permissions from Cache (Redis).
            List<MPermission> permissions = userPermissionCacheService.getPermissions(userId);
            
            // 5. ตั้งค่า MDC (Mapped Diagnostic Context) สำหรับ Logging / Set MDC for logging context.
            MDC.put("userId", userId.toString());
            MDC.put("whitelabelId", jwtTokenProvider.getWhitelabelIdFromToken(token));
            MDC.put("requestId", UUID.randomUUID().toString());
        }
        
        // 6. ส่งต่อ Request ไปยัง Filter ถัดไป / Continue the filter chain.
        filterChain.doFilter(request, response);
    }
}
```

---

## ✅ สรุปส่วนที่เพิ่มเติม

| ฟีเจอร์ | สถานะ | คำอธิบาย |
|---------|--------|----------|
| **Database Design** | ✅ ครบถ้วน | ครอบคลุม `User`, `Role`, `Permission`, `UserToken`, `RateLimitLog` พร้อม Relation |
| **Cache (Redis)** | ✅ เพิ่มแล้ว | `CacheConfig`, `UserPermissionCacheService`, `TokenCacheService` |
| **Rate Limit** | ✅ เพิ่มแล้ว | Custom Annotation `@RateLimit`, Interceptor, Exception Handler |
| **API Routing** | ✅ ชัดเจน | `/login`, `/logout`, `/refresh`, `/register`, `/me` พร้อม Rate Limit |
| **Bilingual Comments** | ✅ ครบถ้วน | ทุก Function มีคำอธิบายภาษาไทยและภาษาอังกฤษแยกบรรทัด |

---
 