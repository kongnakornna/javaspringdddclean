package com.icmon.module.i18n.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.i18n.application.interfaces.LanguageService;
import com.icmon.module.i18n.application.interfaces.MessageService;
import com.icmon.module.i18n.presentation.dto.request.LanguageSwitchRequestDTO;
import com.icmon.module.i18n.presentation.dto.response.LanguageResponseDTO;
import com.icmon.module.i18n.presentation.dto.response.MessageResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/languages")
@RequiredArgsConstructor
@Tag(name = "Multi-Language (i18n)", description = "Internationalization and Language Management APIs")
public class LanguageController {

    private final LanguageService languageService;
    private final MessageService messageService;

    @GetMapping("/")
    @RateLimit(limit = 50, duration = 60)
    @Operation(summary = "ดึงรายการภาษาที่รองรับ", description = "Get list of supported languages")
    public ResponseEntity<List<LanguageResponseDTO>> getLanguages() throws SystemGlobalException {
        return ResponseEntity.ok(languageService.getAllLanguages());
    }

    @GetMapping("/current")
    @RateLimit(limit = 100, duration = 60)
    @Operation(summary = "ดึงภาษาปัจจุบัน", description = "Get current language")
    public ResponseEntity<LanguageResponseDTO> getCurrentLanguage() throws SystemGlobalException {
        return ResponseEntity.ok(languageService.getCurrentLanguage());
    }

    @PostMapping("/switch")
    @RateLimit(limit = 20, duration = 60)
    @Operation(summary = "สลับภาษา", description = "Switch current language")
    public ResponseEntity<LanguageResponseDTO> switchLanguage(
            @Valid @RequestBody LanguageSwitchRequestDTO request,
            Locale locale) throws SystemGlobalException {
        return ResponseEntity.ok(languageService.switchLanguage(request.getLanguageCode(), locale));
    }

    @GetMapping("/messages/{languageCode}")
    @RateLimit(limit = 30, duration = 60)
    @Operation(summary = "ดึงข้อความทั้งหมดตามภาษา", description = "Get all messages by language code")
    public ResponseEntity<Map<String, String>> getMessages(@PathVariable String languageCode) throws SystemGlobalException {
        return ResponseEntity.ok(messageService.getAllMessages(languageCode));
    }

    @GetMapping("/message")
    @RateLimit(limit = 100, duration = 60)
    @Operation(summary = "ดึงข้อความเดี่ยว", description = "Get single message by key and language")
    public ResponseEntity<MessageResponseDTO> getMessage(
            @RequestParam String messageKey,
            @RequestParam(required = false) String languageCode) throws SystemGlobalException {
        return ResponseEntity.ok(messageService.getMessage(messageKey, languageCode));
    }

    @GetMapping("/default")
    @RateLimit(limit = 50, duration = 60)
    @Operation(summary = "ดึงภาษาเริ่มต้น", description = "Get default language")
    public ResponseEntity<LanguageResponseDTO> getDefaultLanguage() throws SystemGlobalException {
        return ResponseEntity.ok(languageService.getDefaultLanguage());
    }
}