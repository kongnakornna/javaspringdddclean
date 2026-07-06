package com.icmon.module.inventory.application.impl;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.inventory.application.interfaces.StockTakeService;
import com.icmon.module.inventory.infrastructure.entity.StockTakeHeaderEntity;
import com.icmon.module.inventory.infrastructure.repository.StockTakeRepository;
import com.icmon.module.inventory.presentation.dto.request.StockTakeRequestDTO;
import com.icmon.module.inventory.presentation.dto.response.StockTakeResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockTakeServiceImpl implements StockTakeService {
    private final StockTakeRepository stockTakeRepository;

    @Override
    @Transactional
    public StockTakeResponseDTO createStockTake(StockTakeRequestDTO request) throws SystemGlobalException {
        StockTakeHeaderEntity entity = new StockTakeHeaderEntity();
        entity.setStocktakeDate(LocalDateTime.now());
        entity.setStatus("DRAFT");
        entity.setStartedBy(getCurrentUserId());
        entity.setStartedAt(LocalDateTime.now());
        entity.setNotes(request.getNotes());
        entity.setWhitelabelId(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        StockTakeHeaderEntity saved = stockTakeRepository.save(entity);
        log.info("Created stock take: {}", saved.getStocktakeNo());
        return StockTakeResponseDTO.fromEntity(saved);
    }

    @Override
    @Transactional
    public StockTakeResponseDTO completeStockTake(UUID id) throws SystemGlobalException {
        StockTakeHeaderEntity entity = stockTakeRepository.findById(id)
                .orElseThrow(() -> new SystemGlobalException("Stock take not found: " + id, null));
        entity.setStatus("COMPLETED");
        entity.setCompletedBy(getCurrentUserId());
        entity.setCompletedAt(LocalDateTime.now());
        StockTakeHeaderEntity saved = stockTakeRepository.save(entity);
        log.info("Completed stock take: {}", saved.getStocktakeNo());
        return StockTakeResponseDTO.fromEntity(saved);
    }

    private UUID getCurrentUserId() {
        return UUID.fromString("00000000-0000-0000-0000-000000000001");
    }
}
