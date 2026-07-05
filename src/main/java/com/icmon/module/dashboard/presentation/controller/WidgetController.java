package com.icmon.module.dashboard.presentation.controller;

import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.dashboard.application.interfaces.WidgetService;
import com.icmon.module.dashboard.presentation.dto.request.WidgetConfigRequestDTO;
import com.icmon.module.dashboard.presentation.dto.response.WidgetConfigResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/widgets")
@Tag(name = "Dashboard Widgets", description = "Dashboard Widget Management APIs")
@RequiredArgsConstructor
public class WidgetController {

    private final WidgetService widgetService;

    @GetMapping("/user/{userId}")
    @RateLimit(limit = 30, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Get user widgets")
    public ResponseEntity<List<WidgetConfigResponseDTO>> getUserWidgets(@PathVariable UUID userId) throws FailedRequestException {
        List<WidgetConfigResponseDTO> response = widgetService.getUserWidgets(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Save widget configuration")
    public ResponseEntity<WidgetConfigResponseDTO> saveWidget(@Valid @RequestBody WidgetConfigRequestDTO request) throws FailedRequestException {
        WidgetConfigResponseDTO response = widgetService.saveWidget(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/user/{userId}/widget/{widgetId}")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Delete widget configuration")
    public ResponseEntity<Void> deleteWidget(@PathVariable UUID userId, @PathVariable String widgetId) throws FailedRequestException {
        widgetService.deleteWidget(userId, widgetId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/user/{userId}/reorder")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Reorder user widgets")
    public ResponseEntity<List<WidgetConfigResponseDTO>> reorderWidgets(
            @PathVariable UUID userId,
            @RequestBody List<String> widgetIds) throws FailedRequestException {
        List<WidgetConfigResponseDTO> response = widgetService.reorderWidgets(userId, widgetIds);
        return ResponseEntity.ok(response);
    }
}
