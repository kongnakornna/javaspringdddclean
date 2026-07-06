package com.icmon.module.weborder.domain;

import com.icmon.module.weborder.domain.enums.PromotionType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MPromotionTest {

    @Test
    void testIsValid() {
        MPromotion promotion = new MPromotion();
        promotion.setIsActive(true);
        promotion.setStartDate(LocalDateTime.now().minusDays(1));
        promotion.setEndDate(LocalDateTime.now().plusDays(1));
        promotion.setUsageLimit(100);
        promotion.setUsedCount(0);
        assertTrue(promotion.isValid());
    }

    @Test
    void testIsNotValidWhenExpired() {
        MPromotion promotion = new MPromotion();
        promotion.setIsActive(true);
        promotion.setStartDate(LocalDateTime.now().minusDays(10));
        promotion.setEndDate(LocalDateTime.now().minusDays(1));
        promotion.setUsageLimit(100);
        promotion.setUsedCount(0);
        assertFalse(promotion.isValid());
    }

    @Test
    void testIsNotValidWhenUsageExceeded() {
        MPromotion promotion = new MPromotion();
        promotion.setIsActive(true);
        promotion.setStartDate(LocalDateTime.now().minusDays(1));
        promotion.setEndDate(LocalDateTime.now().plusDays(1));
        promotion.setUsageLimit(100);
        promotion.setUsedCount(100);
        assertFalse(promotion.isValid());
    }

    @Test
    void testIsNotValidWhenInactive() {
        MPromotion promotion = new MPromotion();
        promotion.setIsActive(false);
        promotion.setStartDate(LocalDateTime.now().minusDays(1));
        promotion.setEndDate(LocalDateTime.now().plusDays(1));
        assertFalse(promotion.isValid());
    }
}
