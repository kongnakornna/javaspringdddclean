package com.icmon.module.inventory.presentation.controller;

import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/part-picking")
@RequiredArgsConstructor
@Tag(name = "Inventory - Part Picking", description = "การเบิกอะไหล่ // Part Picking APIs")
public class PartPickingPdfController {

    @GetMapping("/{id}/pdf")
    @RateLimit(limit = 15, duration = 300)
    @Operation(summary = "สร้าง PDF เอกสารเบิกอะไหล่", description = "Generate picking document as PDF")
    public ResponseEntity<byte[]> generatePickingPdf(@PathVariable UUID id) {
        try {
            byte[] pdfBytes = new byte[0];
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=picking_" + id + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (Exception e) {
            log.error("Failed to generate PDF: {}", e.getMessage());
            throw new RuntimeException("Failed to generate picking PDF", e);
        }
    }
}
