package com.icmon.module.inventory.infrastructure.batch;

import com.icmon.module.inventory.infrastructure.entity.InventoryAlertEntity;
import com.icmon.module.inventory.infrastructure.entity.PartMasterEntity;
import com.icmon.module.inventory.infrastructure.repository.InventoryAlertRepository;
import com.icmon.module.inventory.infrastructure.repository.PartMasterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatchLowStockAlertJob {
    private final PartMasterRepository partMasterRepository;
    private final InventoryAlertRepository alertRepository;

    @Scheduled(cron = "0 30 6 * * *")
    @Transactional
    public void checkLowStockAndAlert() {
        log.info("[BATCH] Starting Low Stock Alert check at: {}", LocalDateTime.now());
        LocalDate today = LocalDate.now();
        List<PartMasterEntity> lowStockParts = partMasterRepository.findLowStockParts();
        if (lowStockParts.isEmpty()) { log.info("[BATCH] No low stock items found."); return; }
        int alertCount = 0;
        for (PartMasterEntity part : lowStockParts) {
            boolean alreadyAlerted = alertRepository.existsByAlertDateAndPartId(today, part.getId());
            if (!alreadyAlerted) {
                InventoryAlertEntity alert = new InventoryAlertEntity();
                alert.setAlertDate(today); alert.setPartId(part.getId());
                alert.setPartCode(part.getPartCode()); alert.setPartName(part.getPartName());
                alert.setCurrentStock(part.getStockQuantity()); alert.setReorderLevel(part.getReorderLevel());
                alert.setReorderQuantity(part.getReorderQuantity()); alert.setAlertSent(false);
                alert.setResolved(false); alert.setWhitelabelId(UUID.fromString("00000000-0000-0000-0000-000000000001"));
                alertRepository.save(alert); alertCount++;
            }
        }
        log.info("[BATCH] Found {} low stock parts, created {} new alerts.", lowStockParts.size(), alertCount);
    }
}
