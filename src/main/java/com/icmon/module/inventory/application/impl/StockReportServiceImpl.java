package com.icmon.module.inventory.application.impl;

import com.icmon._shared.application.GenericAuthDomainServiceImpl;
import com.icmon.exception.SystemGlobalException;
import com.icmon.exception.models.FailedRequestException;
import com.icmon.module.inventory.application.interfaces.StockReportService;
import com.icmon.module.inventory.infrastructure.entity.InventoryEntity;
import com.icmon.module.inventory.infrastructure.entity.PartMasterEntity;
import com.icmon.module.inventory.infrastructure.report.StockReportGenerator;
import com.icmon.module.inventory.infrastructure.repository.InventoryRepository;
import com.icmon.module.inventory.infrastructure.repository.PartMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockReportServiceImpl extends GenericAuthDomainServiceImpl implements StockReportService {

    private final PartMasterRepository partRepository;
    private final InventoryRepository inventoryRepository;
    private final StockReportGenerator reportGenerator;

    @Override
    public byte[] generateStockReport() throws SystemGlobalException {
        List<PartMasterEntity> parts = partRepository.findAll();
        return reportGenerator.generateStockReportPdf(parts);
    }

    @Override
    public byte[] generatePartStockReport(UUID partId) throws SystemGlobalException {
        PartMasterEntity part = partRepository.findById(partId)
                .orElseThrow(() -> new FailedRequestException("Part not found with id: " + partId, null));
        return reportGenerator.generateStockReportPdf(List.of(part));
    }

    @Override
    public byte[] generateInventoryTransactionReport(UUID partId) throws SystemGlobalException {
        List<InventoryEntity> transactions = inventoryRepository.findByPartId(partId);
        return reportGenerator.generateInventoryTransactionReport(transactions);
    }

    @Override
    public byte[] generateLowStockReport() throws SystemGlobalException {
        List<PartMasterEntity> lowStockParts = partRepository.findAll().stream()
                .filter(p -> p.getStockQuantity() <= p.getReorderLevel())
                .toList();
        return reportGenerator.generateStockReportPdf(lowStockParts);
    }
}
