package com.icmon.module.auth.presentation.controller;

import com.icmon.module.auth.application.interfaces.AuthService;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.auth.presentation.dto.request.LoginRequestDTO;
import com.icmon.module.auth.presentation.dto.request.RegisterRequestDTO;
import com.icmon.module.auth.presentation.dto.response.LoginResponseDTO;
import com.icmon.module.auth.presentation.dto.response.UserResponseDTO;
import com.icmon.exception.SystemGlobalException;
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
        ฟังก์ชันนี้ให้ผู้ใช้ลงทะเบียนใหม่
        This function allows new user registration.
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
        Rate Limit: อนุญาต 50 ครั้งต่อ 1 นาที / Allows 50 attempts per 1 minute.
    */
    @GetMapping("/me")
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Get Current User Profile")
    public ResponseEntity<UserResponseDTO> getCurrentUser() throws SystemGlobalException {
        return ResponseEntity.ok(authService.getCurrentUser());
    }
}
