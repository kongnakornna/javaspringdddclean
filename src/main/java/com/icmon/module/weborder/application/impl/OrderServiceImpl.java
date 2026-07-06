package com.icmon.module.weborder.application.impl;

import com.icmon._shared.application.GenericAuthDomainServiceImpl;
import com.icmon.module.weborder.application.interfaces.OrderService;
import com.icmon.module.weborder.domain.TWebOrder;
import com.icmon.module.weborder.domain.enums.OrderStatus;
import com.icmon.module.weborder.infrastructure.cache.OrderCacheService;
import com.icmon.module.weborder.infrastructure.entity.*;
import com.icmon.module.weborder.infrastructure.mapper.OrderMapper;
import com.icmon.module.weborder.infrastructure.repository.*;
import com.icmon.module.weborder.presentation.dto.request.OrderCreateRequestDTO;
import com.icmon.module.weborder.presentation.dto.request.OrderStatusUpdateRequestDTO;
import com.icmon.module.weborder.presentation.dto.response.OrderResponseDTO;
import com.icmon.module.weborder.presentation.dto.response.OrderDetailResponseDTO;
import com.icmon.module.weborder.presentation.dto.response.OrderStatusHistoryDTO;
import com.icmon.module.weborder.presentation.dto.response.OrderTrackingResponseDTO;
import com.icmon.exception.SystemGlobalException;
import com.icmon.exception.models.FailedRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends GenericAuthDomainServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderStatusHistoryRepository statusHistoryRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CatalogueItemRepository catalogueItemRepository;
    private final OrderMapper orderMapper;
    private final OrderCacheService cacheService;

    @Override
    @Transactional
    public OrderResponseDTO createOrder(OrderCreateRequestDTO request) throws SystemGlobalException {
        ShoppingCartEntity cart = cartRepository.findByCartId(request.getCartId())
                .orElseThrow(() -> new FailedRequestException("Cart not found: " + request.getCartId(), null));

        List<ShoppingCartItemEntity> cartItems = cartItemRepository.findByCartId(cart.getId());
        if (cartItems.isEmpty()) {
            throw new FailedRequestException("Cart is empty.", null);
        }

        for (ShoppingCartItemEntity cartItem : cartItems) {
            catalogueItemRepository.findById(cartItem.getItemId())
                    .orElseThrow(() -> new FailedRequestException("Item not found: " + cartItem.getItemId(), null));
        }

        WebOrderEntity order = new WebOrderEntity();
        order.setCartId(cart.getId());
        order.setCustomerId(request.getCustomerId());
        order.setOrderDate(LocalDateTime.now());
        order.setOrderSource(request.getOrderSource() != null ? request.getOrderSource() : null);
        order.setStatus(OrderStatus.PENDING);
        order.setPaymentStatus("PENDING");
        order.setSubtotal(cart.getSubtotal() != null ? cart.getSubtotal() : BigDecimal.ZERO);
        order.setDiscount(cart.getDiscount() != null ? cart.getDiscount() : BigDecimal.ZERO);
        order.setTax(cart.getTax() != null ? cart.getTax() : BigDecimal.ZERO);
        order.setShippingCost(cart.getShipping() != null ? cart.getShipping() : BigDecimal.ZERO);
        order.setTotal(cart.getTotal() != null ? cart.getTotal() : BigDecimal.ZERO);
        order.setPromotionCode(cart.getPromotionCode());
        order.setShippingAddress(request.getShippingAddress());
        order.setShippingPhone(request.getShippingPhone());
        order.setShippingEmail(request.getShippingEmail());
        order.setNotes(request.getNotes());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setUserId(getUserId());
        order.setWhitelabelId(getWhitelabelId());

        WebOrderEntity savedOrder = orderRepository.save(order);

        for (ShoppingCartItemEntity cartItem : cartItems) {
            WebOrderItemEntity orderItem = new WebOrderItemEntity();
            orderItem.setOrderId(savedOrder.getId());
            orderItem.setItemId(cartItem.getItemId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getUnitPrice());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            orderItem.setDiscount(BigDecimal.ZERO);
            orderItem.setNetPrice(cartItem.getTotalPrice());
            orderItemRepository.save(orderItem);
        }

        cartItemRepository.deleteByCartId(cart.getId());
        cart.setSubtotal(BigDecimal.ZERO);
        cart.setDiscount(BigDecimal.ZERO);
        cart.setTax(BigDecimal.ZERO);
        cart.setTotal(BigDecimal.ZERO);
        cartRepository.save(cart);

        recordStatusHistory(savedOrder.getId(), null, OrderStatus.PENDING, "Order created");

        TWebOrder domain = orderMapper.toDomain(savedOrder);
        cacheService.saveOrder(domain);

        return toOrderResponseDTO(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDetailResponseDTO getOrder(UUID id) throws SystemGlobalException {
        WebOrderEntity entity = orderRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Order not found with id: " + id, null));
        return toDetailResponseDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDetailResponseDTO getOrderByNumber(String orderNo) throws SystemGlobalException {
        WebOrderEntity entity = orderRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new FailedRequestException("Order not found with number: " + orderNo, null));
        return toDetailResponseDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponseDTO> listOrders(String status, String startDate, String endDate, Pageable pageable) throws SystemGlobalException {
        Page<WebOrderEntity> page = orderRepository.searchOrders(status, startDate, endDate, pageable);
        return page.map(this::toOrderResponseDTO);
    }

    @Override
    @Transactional
    public OrderResponseDTO updateOrderStatus(UUID id, OrderStatusUpdateRequestDTO request) throws SystemGlobalException {
        WebOrderEntity entity = orderRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Order not found with id: " + id, null));

        if (entity.getStatus() == OrderStatus.DELIVERED || entity.getStatus() == OrderStatus.CANCELLED) {
            throw new FailedRequestException("Cannot change status of delivered or cancelled order.", null);
        }

        OrderStatus previousStatus = entity.getStatus();
        entity.setStatus(request.getStatus());

        if (request.getTrackingNumber() != null) entity.setTrackingNumber(request.getTrackingNumber());
        if (request.getCourier() != null) entity.setCourier(request.getCourier());

        if (request.getStatus() == OrderStatus.DELIVERED) {
            entity.setDeliveredAt(LocalDateTime.now());
        }

        WebOrderEntity saved = orderRepository.save(entity);
        recordStatusHistory(saved.getId(), previousStatus, request.getStatus(), request.getReason());

        TWebOrder domain = orderMapper.toDomain(saved);
        cacheService.evictOrder(id);
        cacheService.saveOrder(domain);

        return toOrderResponseDTO(saved);
    }

    @Override
    @Transactional
    public OrderResponseDTO cancelOrder(UUID id, String reason) throws SystemGlobalException {
        WebOrderEntity entity = orderRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Order not found with id: " + id, null));

        if (entity.getStatus() != OrderStatus.PENDING && entity.getStatus() != OrderStatus.CONFIRMED) {
            throw new FailedRequestException("Cannot cancel order with status: " + entity.getStatus(), null);
        }

        OrderStatus previousStatus = entity.getStatus();
        entity.setStatus(OrderStatus.CANCELLED);
        entity.setCancelledAt(LocalDateTime.now());
        entity.setCancellationReason(reason);

        WebOrderEntity saved = orderRepository.save(entity);
        recordStatusHistory(saved.getId(), previousStatus, OrderStatus.CANCELLED, reason);

        cacheService.evictOrder(id);
        return toOrderResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderStatusHistoryDTO> getOrderHistory(UUID id) throws SystemGlobalException {
        orderRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Order not found with id: " + id, null));

        List<WebOrderStatusHistoryEntity> history = statusHistoryRepository.findByOrderIdOrderByChangedAtAsc(id);
        return history.stream()
                .map(h -> OrderStatusHistoryDTO.builder()
                        .id(h.getId())
                        .orderId(h.getOrderId())
                        .fromStatus(h.getFromStatus())
                        .toStatus(h.getToStatus())
                        .changedBy(h.getChangedBy())
                        .changedAt(h.getChangedAt())
                        .reason(h.getReason())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public OrderTrackingResponseDTO getOrderTracking(UUID id) throws SystemGlobalException {
        WebOrderEntity entity = orderRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Order not found with id: " + id, null));

        return OrderTrackingResponseDTO.builder()
                .id(entity.getId())
                .orderNo(entity.getOrderNo())
                .status(entity.getStatus())
                .trackingNumber(entity.getTrackingNumber())
                .courier(entity.getCourier())
                .paymentStatus(entity.getPaymentStatus())
                .build();
    }

    private void recordStatusHistory(UUID orderId, OrderStatus fromStatus, OrderStatus toStatus, String reason) throws SystemGlobalException {
        WebOrderStatusHistoryEntity history = new WebOrderStatusHistoryEntity();
        history.setOrderId(orderId);
        history.setFromStatus(fromStatus);
        history.setToStatus(toStatus);
        history.setChangedBy(getUserId());
        history.setChangedAt(LocalDateTime.now());
        history.setReason(reason);
        history.setWhitelabelId(getWhitelabelId());
        statusHistoryRepository.save(history);
    }

    private OrderResponseDTO toOrderResponseDTO(WebOrderEntity entity) {
        return OrderResponseDTO.builder()
                .id(entity.getId())
                .orderNo(entity.getOrderNo())
                .customerId(entity.getCustomerId())
                .orderDate(entity.getOrderDate())
                .status(entity.getStatus())
                .paymentStatus(entity.getPaymentStatus())
                .total(entity.getTotal())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    private OrderDetailResponseDTO toDetailResponseDTO(WebOrderEntity entity) {
        List<WebOrderItemEntity> orderItems = orderItemRepository.findByOrderId(entity.getId());
        List<OrderDetailResponseDTO.OrderItemDTO> itemDTOs = orderItems.stream().map(item -> {
            CatalogueItemEntity catItem = catalogueItemRepository.findById(item.getItemId()).orElse(null);
            return OrderDetailResponseDTO.OrderItemDTO.builder()
                    .id(item.getId())
                    .itemId(item.getItemId())
                    .itemName(catItem != null ? catItem.getItemName() : null)
                    .quantity(item.getQuantity())
                    .unitPrice(item.getUnitPrice())
                    .totalPrice(item.getTotalPrice())
                    .discount(item.getDiscount())
                    .netPrice(item.getNetPrice())
                    .build();
        }).collect(Collectors.toList());

        return OrderDetailResponseDTO.builder()
                .id(entity.getId())
                .orderNo(entity.getOrderNo())
                .cartId(entity.getCartId())
                .customerId(entity.getCustomerId())
                .orderDate(entity.getOrderDate())
                .orderSource(entity.getOrderSource())
                .status(entity.getStatus())
                .paymentStatus(entity.getPaymentStatus())
                .subtotal(entity.getSubtotal())
                .discount(entity.getDiscount())
                .tax(entity.getTax())
                .shippingCost(entity.getShippingCost())
                .total(entity.getTotal())
                .promotionCode(entity.getPromotionCode())
                .shippingAddress(entity.getShippingAddress())
                .shippingPhone(entity.getShippingPhone())
                .shippingEmail(entity.getShippingEmail())
                .trackingNumber(entity.getTrackingNumber())
                .courier(entity.getCourier())
                .notes(entity.getNotes())
                .paymentMethod(entity.getPaymentMethod())
                .paidAt(entity.getPaidAt())
                .deliveredAt(entity.getDeliveredAt())
                .cancellationReason(entity.getCancellationReason())
                .items(itemDTOs)
                .build();
    }
}
