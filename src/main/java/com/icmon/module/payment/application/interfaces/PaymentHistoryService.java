package com.icmon.module.payment.application.interfaces;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.payment.presentation.dto.response.PaymentHistoryResponseDTO;

import java.util.List;
import java.util.UUID;

public interface PaymentHistoryService {
    List<PaymentHistoryResponseDTO> getPaymentHistoryByPaymentId(UUID paymentId) throws SystemGlobalException;
    PaymentHistoryResponseDTO recordStatusChange(UUID paymentId, String fromStatus, String toStatus, UUID changedBy, String reason) throws SystemGlobalException;
}
