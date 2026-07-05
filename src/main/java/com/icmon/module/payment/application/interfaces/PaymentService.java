package com.icmon.module.payment.application.interfaces;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.payment.presentation.dto.request.PaymentRecordRequestDTO;
import com.icmon.module.payment.presentation.dto.request.PaymentSearchRequestDTO;
import com.icmon.module.payment.presentation.dto.request.RefundRequestDTO;
import com.icmon.module.payment.presentation.dto.response.OutstandingBalanceResponseDTO;
import com.icmon.module.payment.presentation.dto.response.PaymentHistoryResponseDTO;
import com.icmon.module.payment.presentation.dto.response.PaymentResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PaymentService {
    PaymentResponseDTO recordPayment(PaymentRecordRequestDTO request) throws SystemGlobalException;
    PaymentResponseDTO getPayment(UUID id) throws SystemGlobalException;
    PaymentResponseDTO getPaymentByInvoiceId(UUID invoiceId) throws SystemGlobalException;
    Page<PaymentResponseDTO> searchPayments(PaymentSearchRequestDTO request, Pageable pageable) throws SystemGlobalException;
    OutstandingBalanceResponseDTO getOutstandingBalance(UUID customerId) throws SystemGlobalException;
    List<PaymentHistoryResponseDTO> getPaymentHistory(UUID customerId) throws SystemGlobalException;
    PaymentResponseDTO processRefund(UUID id, RefundRequestDTO request) throws SystemGlobalException;
    PaymentResponseDTO cancelPayment(UUID id, String reason) throws SystemGlobalException;
}
