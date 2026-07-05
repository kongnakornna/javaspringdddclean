package com.icmon.module.payment.application.interfaces;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.payment.presentation.dto.response.PaymentMethodResponseDTO;

import java.util.List;
import java.util.UUID;

public interface PaymentMethodService {
    List<PaymentMethodResponseDTO> getAllActiveMethods() throws SystemGlobalException;
    PaymentMethodResponseDTO getMethodById(UUID id) throws SystemGlobalException;
    PaymentMethodResponseDTO getMethodByCode(String code) throws SystemGlobalException;
}
