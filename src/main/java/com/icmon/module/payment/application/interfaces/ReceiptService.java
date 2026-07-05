package com.icmon.module.payment.application.interfaces;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.payment.presentation.dto.response.ReceiptResponseDTO;

import java.util.UUID;

public interface ReceiptService {
    ReceiptResponseDTO getReceipt(UUID id) throws SystemGlobalException;
    ReceiptResponseDTO getReceiptByPaymentId(UUID paymentId) throws SystemGlobalException;
    byte[] generateReceiptPDF(UUID id) throws SystemGlobalException;
    void cancelReceipt(UUID id, String reason) throws SystemGlobalException;
}
