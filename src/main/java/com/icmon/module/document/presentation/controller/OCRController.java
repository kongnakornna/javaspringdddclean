package com.icmon.module.document.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.document.application.usecase.ProcessOCRUseCase;
import com.icmon.module.document.presentation.dto.request.OCRRequestDTO;
import com.icmon.module.document.presentation.dto.response.OCRResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ocr")
@RequiredArgsConstructor
@Tag(name = "OCR Processing", description = "ประมวลผล OCR // Optical Character Recognition processing")
public class OCRController {

    private final ProcessOCRUseCase processOCRUseCase;

    @PostMapping("/process")
    @RateLimit(limit = 5, duration = 60, keyType = "USER_ID")
    @Operation(summary = "ประมวลผล OCR จากรูปภาพ // Process OCR from image")
    public ResponseEntity<OCRResponseDTO> processOCR(@Valid @RequestBody OCRRequestDTO request) throws SystemGlobalException {
        return ResponseEntity.ok(processOCRUseCase.execute(request));
    }
}
