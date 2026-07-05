package com.icmon.module.inventory.domain;

import com.icmon.module.inventory.domain.enums.PartStatus;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class PartMasterDomainTest {

    @Test
    void shouldCreatePartMaster() {
        PartMaster part = new PartMaster(UUID.randomUUID(), "BRK-001", "Brake Pad");
        assertEquals("BRK-001", part.getPartCode());
        assertEquals("Brake Pad", part.getPartName());
        assertEquals(PartStatus.ACTIVE, part.getStatus());
        assertEquals(0, part.getStockQuantity());
        assertEquals("PIECE", part.getUnit());
    }

    @Test
    void shouldUpdateStockQuantity() {
        PartMaster part = new PartMaster(UUID.randomUUID(), "OIL-001", "Engine Oil");
        part.setStockQuantity(50);
        assertEquals(50, part.getStockQuantity());
    }

    @Test
    void shouldSetUnitCost() {
        PartMaster part = new PartMaster(UUID.randomUUID(), "TIR-001", "Tire");
        part.setUnitCost(new BigDecimal("2500.00"));
        assertEquals(0, new BigDecimal("2500.00").compareTo(part.getUnitCost()));
    }
}
