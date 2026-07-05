package com.icmon.module.email.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.email.application.interfaces.EmailHistoryService;
import com.icmon.module.email.presentation.dto.response.EmailHistoryResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/email/history")
@Tag(name = "Email History", description = "ประวัติการส่งอีเมล // Email History APIs")
@RequiredArgsConstructor
public class EmailHistoryController {

    private final EmailHistoryService emailHistoryService;

    @GetMapping("/{id}")
    @RateLimit(limit = 60, duration = 60, keyType = "USER_ID")
    @Operation(summary = "ค้นหาประวัติตาม ID // Get history by ID")
    public ResponseEntity<EmailHistoryResponseDTO> getHistoryById(@PathVariable UUID id) throws SystemGlobalException {
        return ResponseEntity.ok(emailHistoryService.getHistoryById(id));
    }

    @GetMapping("/email/{emailId}")
    @RateLimit(limit = 60, duration = 60, keyType = "USER_ID")
    @Operation(summary = "ค้นหาประวัติตาม Email ID // Get history by email ID")
    public ResponseEntity<EmailHistoryResponseDTO> getHistoryByEmailId(@PathVariable String emailId) throws SystemGlobalException {
        return ResponseEntity.ok(emailHistoryService.getHistoryByEmailId(emailId));
    }

    @GetMapping
    @RateLimit(limit = 60, duration = 60, keyType = "USER_ID")
    @Operation(summary = "ค้นหาประวัติ // Search email histories")
    public ResponseEntity<Page<EmailHistoryResponseDTO>> searchHistories(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String templateCode,
            @RequestParam(required = false) String toEmail,
            Pageable pageable) throws SystemGlobalException {
        return ResponseEntity.ok(emailHistoryService.searchHistories(status, templateCode, toEmail, pageable));
    }
}
