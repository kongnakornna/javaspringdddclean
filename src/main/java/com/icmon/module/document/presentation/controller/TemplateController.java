package com.icmon.module.document.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.document.application.usecase.TemplateUseCase;
import com.icmon.module.document.presentation.dto.request.TemplateUploadRequestDTO;
import com.icmon.module.document.presentation.dto.response.TemplateResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/templates")
@RequiredArgsConstructor
@Tag(name = "Document Template Management", description = "จัดการเทมเพลตเอกสาร // Document template management")
public class TemplateController {

    private final TemplateUseCase templateUseCase;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @RateLimit(limit = 10, duration = 60, keyType = "USER_ID")
    @Operation(summary = "อัปโหลดเทมเพลต // Upload a document template")
    public ResponseEntity<TemplateResponseDTO> uploadTemplate(
            @RequestParam("file") MultipartFile file,
            @Valid @ModelAttribute TemplateUploadRequestDTO request) throws SystemGlobalException {
        return ResponseEntity.ok(templateUseCase.uploadTemplate(file, request));
    }

    @GetMapping("/{templateCode}")
    @RateLimit(limit = 60, duration = 60, keyType = "USER_ID")
    @Operation(summary = "ค้นหาเทมเพลตตามรหัส // Get template by code")
    public ResponseEntity<TemplateResponseDTO> getTemplate(@PathVariable String templateCode) throws SystemGlobalException {
        return ResponseEntity.ok(templateUseCase.getTemplate(templateCode));
    }

    @GetMapping
    @RateLimit(limit = 60, duration = 60, keyType = "USER_ID")
    @Operation(summary = "รายการเทมเพลต // List templates by type")
    public ResponseEntity<Page<TemplateResponseDTO>> listTemplates(
            @RequestParam(required = false) String templateType,
            @PageableDefault(size = 20) Pageable pageable) throws SystemGlobalException {
        return ResponseEntity.ok(templateUseCase.listTemplates(templateType, pageable));
    }

    @DeleteMapping("/{templateCode}")
    @RateLimit(limit = 10, duration = 60, keyType = "USER_ID")
    @Operation(summary = "ลบเทมเพลต // Delete template")
    public ResponseEntity<Void> deleteTemplate(@PathVariable String templateCode) throws SystemGlobalException {
        templateUseCase.deleteTemplate(templateCode);
        return ResponseEntity.noContent().build();
    }
}
