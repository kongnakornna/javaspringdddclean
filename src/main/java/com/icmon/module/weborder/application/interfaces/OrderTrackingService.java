package com.icmon.module.weborder.application.interfaces;

import com.icmon.module.weborder.presentation.dto.response.OrderTrackingResponseDTO;
import com.icmon.exception.SystemGlobalException;

import java.util.UUID;

public interface OrderTrackingService {
    OrderTrackingResponseDTO getTracking(UUID orderId) throws SystemGlobalException;

    void updateTrackingNumber(UUID orderId, String trackingNumber, String courier) throws SystemGlobalException;
}
