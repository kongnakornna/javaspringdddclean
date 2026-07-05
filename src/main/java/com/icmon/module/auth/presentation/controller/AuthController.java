package com.icmon.module.auth.presentation.controller;

import com.icmon.module.auth.application.interfaces.AuthService;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.auth.presentation.dto.request.LoginRequestDTO;
import com.icmon.module.auth.presentation.dto.request.RegisterRequestDTO;
import com.icmon.module.auth.presentation.dto.response.LoginResponseDTO;
import com.icmon.module.auth.presentation.dto.response.UserResponseDTO;
import com.icmon.exception.SystemGlobalException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;  
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j 
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
    @SecurityRequirements({})
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request,
                                                  HttpServletRequest httpRequest) throws SystemGlobalException {
        // ✅ Log: เริ่มต้นการ Login
        String clientIp = getClientIp(httpRequest);
        String userAgent = httpRequest.getHeader("User-Agent");
        log.info("🔐 [LOGIN] Attempt - username: {}, IP: {}, User-Agent: {}", 
                 request.getUsername(), clientIp, userAgent);

        long startTime = System.currentTimeMillis();
        LoginResponseDTO response = authService.login(request, httpRequest);
        long elapsedTime = System.currentTimeMillis() - startTime;

        // ✅ Log: Login สำเร็จ
        log.info("✅ [LOGIN] Success - username: {}, IP: {}, elapsed: {}ms", 
                 request.getUsername(), clientIp, elapsedTime);

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
        // ✅ Log: เริ่มต้น Logout (ไม่บันทึก Token เต็ม)
        String tokenPreview = token != null && token.length() > 20 
                ? token.substring(0, 20) + "..." 
                : "null";
        log.info("🚪 [LOGOUT] Attempt - token: {}", tokenPreview);

        long startTime = System.currentTimeMillis();
        authService.logout(token);
        long elapsedTime = System.currentTimeMillis() - startTime;

        // ✅ Log: Logout สำเร็จ
        log.info("✅ [LOGOUT] Success - elapsed: {}ms", elapsedTime);

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
        // ✅ Log: เริ่มต้น Refresh Token
        String tokenPreview = refreshToken != null && refreshToken.length() > 20 
                ? refreshToken.substring(0, 20) + "..." 
                : "null";
        log.info("🔄 [REFRESH] Attempt - token: {}", tokenPreview);

        long startTime = System.currentTimeMillis();
        LoginResponseDTO response = authService.refreshToken(refreshToken);
        long elapsedTime = System.currentTimeMillis() - startTime;

        // ✅ Log: Refresh สำเร็จ
        log.info("✅ [REFRESH] Success - elapsed: {}ms", elapsedTime);

        return ResponseEntity.ok(response);
    }

    /*
        API: POST /api/v1/auth/register
        ฟังก์ชันนี้ให้ผู้ใช้ลงทะเบียนใหม่
        This function allows new user registration.
        Rate Limit: อนุญาต 3 ครั้งต่อ 1 ชั่วโมง (ป้องกันการสมัครซ้ำหลายครั้ง) / Allows 3 attempts per 1 hour (anti-spam).
    */
    @PostMapping("/register")
    @RateLimit(limit = 30, duration = 3600, keyType = "IP")
    @Operation(summary = "Register New User")
    @SecurityRequirements({})
    public ResponseEntity<LoginResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request,
                                                     HttpServletRequest httpRequest) throws SystemGlobalException {
        // ✅ Log: เริ่มต้น Register
        String clientIp = getClientIp(httpRequest);
        log.info("📝 [REGISTER] Attempt - username: {}, email: {}, IP: {}", 
                 request.getUsername(), request.getEmail(), clientIp);

        long startTime = System.currentTimeMillis();
        LoginResponseDTO response = authService.register(request);
        long elapsedTime = System.currentTimeMillis() - startTime;

        // ✅ Log: Register สำเร็จ
        log.info("✅ [REGISTER] Success - username: {}, elapsed: {}ms", 
                 request.getUsername(), elapsedTime);

        return ResponseEntity.ok(response);
    }

    /*
        API: GET /api/v1/auth/me
        ฟังก์ชันนี้ดึงข้อมูลผู้ใช้ที่กำลังล็อกอินอยู่ (ใช้ Access Token)
        This function fetches the currently logged-in user's profile (using Access Token).
        Rate Limit: อนุญาต 50 ครั้งต่อ 1 นาที / Allows 50 attempts per 1 minute.
    */
    @GetMapping("/me")
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Get Current User Profile")
    public ResponseEntity<UserResponseDTO> getCurrentUser() throws SystemGlobalException {
        // ✅ Log: เรียกดู Profile
        log.info("👤 [ME] Fetching current user profile");

        long startTime = System.currentTimeMillis();
        UserResponseDTO response = authService.getCurrentUser();
        long elapsedTime = System.currentTimeMillis() - startTime;

        // ✅ Log: ดึง Profile สำเร็จ
        log.info("✅ [ME] Success - userId: {}, username: {}, elapsed: {}ms", 
                 response.getId(), response.getUsername(), elapsedTime);

        return ResponseEntity.ok(response);
    }

    /*
        API: GET /api/v1/auth/verify
        ฟังก์ชันนี้ตรวจสอบว่า Access Token (Bearer) ที่ส่งมายังใช้งานได้หรือไม่
        This function verifies whether the provided Bearer Access Token is still valid.
    */
    @GetMapping("/verify")
    @Operation(summary = "Verify Bearer Token")
    public ResponseEntity<Map<String, Object>> verifyToken(@RequestHeader("Authorization") String authHeader) {
        log.info("🔍 [VERIFY] Token verification requested");

        long startTime = System.currentTimeMillis();
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("valid", false, "message", "Missing or invalid Authorization header"));
        }

        String token = authHeader.substring(7);
        boolean isValid = authService.validateToken(token);
        long elapsedTime = System.currentTimeMillis() - startTime;

        if (isValid) {
            log.info("✅ [VERIFY] Token is valid - elapsed: {}ms", elapsedTime);
            return ResponseEntity.ok(Map.of("valid", true, "message", "Token is valid"));
        } else {
            log.warn("⚠️ [VERIFY] Token is invalid or expired - elapsed: {}ms", elapsedTime);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("valid", false, "message", "Token is invalid or expired"));
        }
    }

    @ExceptionHandler(SystemGlobalException.class)
    public ResponseEntity<Map<String, String>> handleSystemGlobalException(SystemGlobalException ex) {
        log.warn("⚠️ [SystemGlobalException] {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Unauthorized", "message", ex.getMessage()));
    }

    /*
        เมธอด Helper สำหรับดึง IP จริงจาก Request (รองรับ Proxy, Load Balancer)
        Helper method to get real client IP from request (supports Proxy, Load Balancer).
    */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // ถ้ามีหลาย IP (X-Forwarded-For: client, proxy1, proxy2) ใช้ตัวแรก
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}