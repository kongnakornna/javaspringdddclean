package com.icmon.module.email.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.email.application.interfaces.EmailService;
import com.icmon.module.email.presentation.dto.request.BulkEmailRequestDTO;
import com.icmon.module.email.presentation.dto.request.EmailSendRequestDTO;
import com.icmon.module.email.presentation.dto.response.EmailSendResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email")
@Tag(name = "Email Service", description = "ส่งอีเมล // Email Management APIs")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    @Operation(summary = "ส่งอีเมล // Send an email")
    public ResponseEntity<EmailSendResponseDTO> sendEmail(@Valid @RequestBody EmailSendRequestDTO request)
            throws SystemGlobalException {
        return ResponseEntity.ok(emailService.sendEmail(request));
    }

    @PostMapping("/send-template")
    @RateLimit(limit = 25, duration = 60, keyType = "USER_ID")
    @Operation(summary = "ส่งอีเมลจากเทมเพลต // Send email from template")
    public ResponseEntity<EmailSendResponseDTO> sendTemplateEmail(@Valid @RequestBody EmailSendRequestDTO request)
            throws SystemGlobalException {
        return ResponseEntity.ok(emailService.sendTemplateEmail(request));
    }

    @PostMapping("/bulk")
    @RateLimit(limit = 5, duration = 300, keyType = "USER_ID")
    @Operation(summary = "ส่งอีเมลจำนวนมาก // Send bulk emails")
    public ResponseEntity<EmailSendResponseDTO> sendBulkEmail(@Valid @RequestBody BulkEmailRequestDTO request)
            throws SystemGlobalException {
        return ResponseEntity.ok(emailService.sendBulkEmail(request));
    }

    @GetMapping("/status/{emailId}")
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    @Operation(summary = "ตรวจสอบสถานะ // Get email send status")
    public ResponseEntity<EmailSendResponseDTO> getEmailStatus(@PathVariable String emailId)
            throws SystemGlobalException {
        return ResponseEntity.ok(emailService.getEmailStatus(emailId));
    }

    @PostMapping("/resend/{emailId}")
    @RateLimit(limit = 10, duration = 3600, keyType = "USER_ID")
    @Operation(summary = "ส่งอีเมลซ้ำ // Resend an email")
    public ResponseEntity<EmailSendResponseDTO> resendEmail(@PathVariable String emailId)
            throws SystemGlobalException {
        return ResponseEntity.ok(emailService.resendEmail(emailId));
    }
}
