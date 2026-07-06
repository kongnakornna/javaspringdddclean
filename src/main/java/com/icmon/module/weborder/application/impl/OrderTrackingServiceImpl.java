package com.icmon.module.weborder.application.impl;

import com.icmon._shared.application.GenericAuthDomainServiceImpl;
import com.icmon.module.weborder.application.interfaces.OrderTrackingService;
import com.icmon.module.weborder.infrastructure.entity.WebOrderEntity;
import com.icmon.module.weborder.infrastructure.repository.OrderRepository;
import com.icmon.module.weborder.presentation.dto.response.OrderTrackingResponseDTO;
import com.icmon.exception.SystemGlobalException;
import com.icmon.exception.models.FailedRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderTrackingServiceImpl extends GenericAuthDomainServiceImpl implements OrderTrackingService {

    private final OrderRepository orderRepository;

    @Override
    @Transactional(readOnly = true)
    public OrderTrackingResponseDTO getTracking(UUID orderId) throws SystemGlobalException {
        WebOrderEntity entity = orderRepository.findById(orderId)
                .orElseThrow(() -> new FailedRequestException("Order not found with id: " + orderId, null));

        return OrderTrackingResponseDTO.builder()
                .id(entity.getId())
                .orderNo(entity.getOrderNo())
                .status(entity.getStatus())
                .trackingNumber(entity.getTrackingNumber())
                .courier(entity.getCourier())
                .paymentStatus(entity.getPaymentStatus())
                .build();
    }

    @Override
    @Transactional
    public void updateTrackingNumber(UUID orderId, String trackingNumber, String courier) throws SystemGlobalException {
        WebOrderEntity entity = orderRepository.findById(orderId)
                .orElseThrow(() -> new FailedRequestException("Order not found with id: " + orderId, null));
        entity.setTrackingNumber(trackingNumber);
        entity.setCourier(courier);
        orderRepository.save(entity);
    }
}
