package com.icmon.module.weborder.presentation.controller;

import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.weborder.application.interfaces.OrderService;
import com.icmon.module.weborder.presentation.dto.request.OrderCreateRequestDTO;
import com.icmon.module.weborder.presentation.dto.request.OrderStatusUpdateRequestDTO;
import com.icmon.module.weborder.presentation.dto.response.OrderDetailResponseDTO;
import com.icmon.module.weborder.presentation.dto.response.OrderResponseDTO;
import com.icmon.module.weborder.presentation.dto.response.OrderStatusHistoryDTO;
import com.icmon.module.weborder.presentation.dto.response.OrderTrackingResponseDTO;
import com.icmon.exception.SystemGlobalException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wos/orders")
@Tag(name = "Web Order - Orders", description = "Order Management APIs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Create order from cart")
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderCreateRequestDTO request)
            throws SystemGlobalException {
        OrderResponseDTO response = orderService.createOrder(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @RateLimit(limit = 100, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Get order by ID")
    public ResponseEntity<OrderDetailResponseDTO> getOrder(@PathVariable UUID id)
            throws SystemGlobalException {
        OrderDetailResponseDTO response = orderService.getOrder(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/number/{orderNo}")
    @RateLimit(limit = 80, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Get order by order number")
    public ResponseEntity<OrderDetailResponseDTO> getOrderByNumber(@PathVariable String orderNo)
            throws SystemGlobalException {
        OrderDetailResponseDTO response = orderService.getOrderByNumber(orderNo);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    @Operation(summary = "List customer orders with pagination")
    public ResponseEntity<Page<OrderResponseDTO>> listOrders(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            Pageable pageable) throws SystemGlobalException {
        Page<OrderResponseDTO> page = orderService.listOrders(status, startDate, endDate, pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}/status")
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Update order status (Admin)")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(
            @PathVariable UUID id,
            @Valid @RequestBody OrderStatusUpdateRequestDTO request) throws SystemGlobalException {
        OrderResponseDTO response = orderService.updateOrderStatus(id, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/cancel")
    @RateLimit(limit = 10, duration = 3600, keyType = "USER_ID")
    @Operation(summary = "Cancel order")
    public ResponseEntity<OrderResponseDTO> cancelOrder(
            @PathVariable UUID id,
            @RequestParam String reason) throws SystemGlobalException {
        OrderResponseDTO response = orderService.cancelOrder(id, reason);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/history")
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Get order status history")
    public ResponseEntity<List<OrderStatusHistoryDTO>> getOrderHistory(@PathVariable UUID id)
            throws SystemGlobalException {
        List<OrderStatusHistoryDTO> history = orderService.getOrderHistory(id);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/{id}/tracking")
    @RateLimit(limit = 60, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Get order tracking info")
    public ResponseEntity<OrderTrackingResponseDTO> getOrderTracking(@PathVariable UUID id)
            throws SystemGlobalException {
        OrderTrackingResponseDTO response = orderService.getOrderTracking(id);
        return ResponseEntity.ok(response);
    }
}
