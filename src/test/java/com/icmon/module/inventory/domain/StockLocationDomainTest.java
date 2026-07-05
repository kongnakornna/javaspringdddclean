package com.icmon.module.inventory.domain;

import com.icmon.module.inventory.domain.enums.LocationType;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class StockLocationDomainTest {

    @Test
    void shouldCreateStockLocation() {
        StockLocation loc = new StockLocation(UUID.randomUUID(), "A-01", "Shelf A-01");
        assertEquals("A-01", loc.getLocationCode());
        assertEquals("Shelf A-01", loc.getLocationName());
        assertEquals(LocationType.SHELF, loc.getLocationType());
        assertTrue(loc.isActive());
        assertEquals(0, loc.getCurrentUsage().intValue());
    }

    @Test
    void shouldSetCapacityAndUsage() {
        StockLocation loc = new StockLocation(UUID.randomUUID(), "B-02", "Bin B-02");
        loc.setCapacity(100);
        loc.setCurrentUsage(30);
        assertEquals(100, loc.getCapacity().intValue());
        assertEquals(30, loc.getCurrentUsage().intValue());
    }
}
