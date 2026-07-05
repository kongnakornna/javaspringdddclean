package com.icmon.module.inventory.application.usecase;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.InventoryService;
import com.icmon.module.inventory.presentation.dto.response.InventoryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SearchTransactionsUseCase {
    private final InventoryService inventoryService;
    public Page<InventoryResponseDTO> execute(UUID partId, String transactionType,
                                               LocalDateTime startDate, LocalDateTime endDate,
                                               Pageable pageable) throws SystemGlobalException {
        return inventoryService.searchTransactions(partId, transactionType, startDate, endDate, pageable);
    }
}
