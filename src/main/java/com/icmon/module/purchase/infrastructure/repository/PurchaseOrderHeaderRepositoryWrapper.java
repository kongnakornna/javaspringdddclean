package com.icmon.module.purchase.infrastructure.repository;

import com.icmon.module.purchase.infrastructure.entity.PurchaseOrderHeaderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PurchaseOrderHeaderRepositoryWrapper {

    private final PurchaseOrderHeaderRepository repository;

    public PurchaseOrderHeaderEntity save(PurchaseOrderHeaderEntity entity) {
        return repository.save(entity);
    }

    public Optional<PurchaseOrderHeaderEntity> findById(UUID id) {
        return repository.findById(id);
    }

    public Optional<PurchaseOrderHeaderEntity> findByPoNo(String poNo) {
        return repository.findByPoNo(poNo);
    }

    public List<PurchaseOrderHeaderEntity> findBySupplierId(UUID supplierId) {
        return repository.findBySupplierId(supplierId);
    }

    public List<PurchaseOrderHeaderEntity> findByStatus(String status) {
        return repository.findByStatus(status);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
