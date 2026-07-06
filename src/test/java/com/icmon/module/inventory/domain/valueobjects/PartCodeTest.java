package com.icmon.module.inventory.domain.valueobjects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

@DisplayName("PartCode Value Object Tests")
class PartCodeTest {

    @Test
    @DisplayName("should create valid part code")
    void shouldCreateValidPartCode() {
        PartCode code = new PartCode("OIL-001");
        assertThat(code.value()).isEqualTo("OIL-001");
    }

    @Test
    @DisplayName("should throw exception for blank part code")
    void shouldThrowExceptionForBlankCode() {
        assertThatThrownBy(() -> new PartCode(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("should throw exception for null part code")
    void shouldThrowExceptionForNullCode() {
        assertThatThrownBy(() -> new PartCode(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
