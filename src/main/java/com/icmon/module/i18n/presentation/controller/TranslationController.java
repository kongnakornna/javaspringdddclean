package com.icmon.module.i18n.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.i18n.application.interfaces.TranslationService;
import com.icmon.module.i18n.presentation.dto.request.BulkImportRequestDTO;
import com.icmon.module.i18n.presentation.dto.request.TranslationUpdateRequestDTO;
import com.icmon.module.i18n.presentation.dto.response.BulkImportResponseDTO;
import com.icmon.module.i18n.presentation.dto.response.TranslationResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/translations")
@RequiredArgsConstructor
@Tag(name = "Translations", description = "Translation Management APIs")
public class TranslationController {

    private final TranslationService translationService;

    @GetMapping("/")
    @RateLimit(limit = 30, duration = 60)
    @Operation(summary = "ดึงรายการข้อความที่แปลแล้ว", description = "List translations with optional filters")
    public ResponseEntity<Page<TranslationResponseDTO>> listTranslations(
            @RequestParam(required = false) String languageCode,
            @RequestParam(required = false) String messageKey,
            Pageable pageable) throws SystemGlobalException {
        return ResponseEntity.ok(translationService.listTranslations(languageCode, messageKey, pageable));
    }

    @PutMapping("/{id}")
    @RateLimit(limit = 10, duration = 300)
    @Operation(summary = "อัปเดตข้อความที่แปลแล้ว", description = "Update a translation by ID")
    public ResponseEntity<TranslationResponseDTO> updateTranslation(
            @PathVariable UUID id,
            @Valid @RequestBody TranslationUpdateRequestDTO request) throws SystemGlobalException {
        return ResponseEntity.ok(translationService.updateTranslation(id, request));
    }

    @PostMapping("/")
    @RateLimit(limit = 10, duration = 300)
    @Operation(summary = "สร้างข้อความที่แปลแล้ว", description = "Create a new translation")
    public ResponseEntity<TranslationResponseDTO> createTranslation(
            @Valid @RequestBody TranslationUpdateRequestDTO request) throws SystemGlobalException {
        return ResponseEntity.ok(translationService.createTranslation(request));
    }

    @DeleteMapping("/{id}")
    @RateLimit(limit = 5, duration = 3600)
    @Operation(summary = "ลบข้อความที่แปลแล้ว", description = "Delete a translation by ID")
    public ResponseEntity<Void> deleteTranslation(@PathVariable UUID id) throws SystemGlobalException {
        translationService.deleteTranslation(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/bulk-import")
    @RateLimit(limit = 3, duration = 3600)
    @Operation(summary = "นำเข้าข้อความจำนวนมาก", description = "Bulk import translations")
    public ResponseEntity<BulkImportResponseDTO> bulkImportTranslations(
            @RequestBody BulkImportRequestDTO request) throws SystemGlobalException {
        return ResponseEntity.ok(translationService.bulkImportTranslations(request));
    }
}
