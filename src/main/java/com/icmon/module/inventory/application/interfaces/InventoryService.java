package com.icmon.module.inventory.application.interfaces;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.presentation.dto.request.InventoryTransactionRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.InventoryResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface InventoryService {
    InventoryResponseDTO recordTransaction(InventoryTransactionRequestDTO request) throws SystemGlobalException;
    InventoryResponseDTO getTransaction(UUID id) throws SystemGlobalException;
    Page<InventoryResponseDTO> searchTransactions(UUID partId, String transactionType,
                                                  LocalDateTime startDate, LocalDateTime endDate,
                                                  Pageable pageable) throws SystemGlobalException;
}
