package com.icmon.module.email.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.email.application.interfaces.EmailTemplateService;
import com.icmon.module.email.presentation.dto.request.EmailTemplateRequestDTO;
import com.icmon.module.email.presentation.dto.response.EmailTemplateResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email/templates")
@Tag(name = "Email Templates", description = "จัดการเทมเพลตอีเมล // Email Template Management APIs")
@RequiredArgsConstructor
public class EmailTemplateController {

    private final EmailTemplateService emailTemplateService;

    @PostMapping
    @RateLimit(limit = 10, duration = 300, keyType = "USER_ID")
    @Operation(summary = "สร้างเทมเพลต // Create email template")
    public ResponseEntity<EmailTemplateResponseDTO> createTemplate(@Valid @RequestBody EmailTemplateRequestDTO request)
            throws SystemGlobalException {
        return ResponseEntity.ok(emailTemplateService.createTemplate(request));
    }

    @GetMapping("/{templateCode}")
    @RateLimit(limit = 100, duration = 60, keyType = "USER_ID")
    @Operation(summary = "ค้นหาเทมเพลต // Get template by code")
    public ResponseEntity<EmailTemplateResponseDTO> getTemplate(
            @PathVariable String templateCode,
            @RequestParam(defaultValue = "th") String language) throws SystemGlobalException {
        return ResponseEntity.ok(emailTemplateService.getTemplate(templateCode, language));
    }

    @GetMapping
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    @Operation(summary = "รายการเทมเพลต // List email templates")
    public ResponseEntity<Page<EmailTemplateResponseDTO>> listTemplates(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String language,
            Pageable pageable) throws SystemGlobalException {
        return ResponseEntity.ok(emailTemplateService.listTemplates(category, language, pageable));
    }

    @PutMapping("/{templateCode}")
    @RateLimit(limit = 10, duration = 300, keyType = "USER_ID")
    @Operation(summary = "แก้ไขเทมเพลต // Update email template")
    public ResponseEntity<EmailTemplateResponseDTO> updateTemplate(
            @PathVariable String templateCode,
            @RequestParam(defaultValue = "th") String language,
            @Valid @RequestBody EmailTemplateRequestDTO request) throws SystemGlobalException {
        return ResponseEntity.ok(emailTemplateService.updateTemplate(templateCode, language, request));
    }

    @DeleteMapping("/{templateCode}")
    @RateLimit(limit = 5, duration = 3600, keyType = "USER_ID")
    @Operation(summary = "ลบเทมเพลต // Delete email template")
    public ResponseEntity<Void> deleteTemplate(
            @PathVariable String templateCode,
            @RequestParam(defaultValue = "th") String language) throws SystemGlobalException {
        emailTemplateService.deleteTemplate(templateCode, language);
        return ResponseEntity.noContent().build();
    }
}
