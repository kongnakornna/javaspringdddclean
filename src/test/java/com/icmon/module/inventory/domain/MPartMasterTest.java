package com.icmon.module.inventory.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

@DisplayName("Part Master Domain Tests")
class MPartMasterTest {

    @Test
    @DisplayName("should detect low stock when quantity is below reorder level")
    void shouldDetectLowStock() {
        MPartMaster part = new MPartMaster();
        part.setStockQuantity(3);
        part.setReorderLevel(5);
        assertThat(part.isLowStock()).isTrue();
    }

    @Test
    @DisplayName("should not detect low stock when quantity is above reorder level")
    void shouldNotDetectLowStock() {
        MPartMaster part = new MPartMaster();
        part.setStockQuantity(10);
        part.setReorderLevel(5);
        assertThat(part.isLowStock()).isFalse();
    }

    @Test
    @DisplayName("should increase stock quantity")
    void shouldIncreaseStock() {
        MPartMaster part = new MPartMaster();
        part.setStockQuantity(10);
        part.increaseStock(5);
        assertThat(part.getStockQuantity()).isEqualTo(15);
    }

    @Test
    @DisplayName("should decrease stock quantity")
    void shouldDecreaseStock() {
        MPartMaster part = new MPartMaster();
        part.setStockQuantity(10);
        part.decreaseStock(3);
        assertThat(part.getStockQuantity()).isEqualTo(7);
    }

    @Test
    @DisplayName("should throw exception when decreasing with insufficient stock")
    void shouldThrowExceptionWhenInsufficientStock() {
        MPartMaster part = new MPartMaster();
        part.setStockQuantity(5);
        assertThatThrownBy(() -> part.decreaseStock(10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Insufficient stock");
    }

    @Test
    @DisplayName("should throw exception when increasing with negative quantity")
    void shouldThrowExceptionWhenNegativeIncrease() {
        MPartMaster part = new MPartMaster();
        assertThatThrownBy(() -> part.increaseStock(-5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Quantity must be positive");
    }
}
