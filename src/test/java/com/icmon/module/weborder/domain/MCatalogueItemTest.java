package com.icmon.module.weborder.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MCatalogueItemTest {

    @Test
    void testIsAvailable() {
        MCatalogueItem item = new MCatalogueItem();
        item.setIsActive(true);
        assertTrue(item.isAvailable());

        item.setIsActive(false);
        assertFalse(item.isAvailable());

        item.setIsActive(null);
        assertFalse(item.isAvailable());
    }

    @Test
    void testShouldFeature() {
        MCatalogueItem item = new MCatalogueItem();
        item.setIsActive(true);
        item.setIsFeatured(true);
        assertTrue(item.shouldFeature());

        item.setIsFeatured(false);
        assertFalse(item.shouldFeature());

        item.setIsActive(false);
        item.setIsFeatured(true);
        assertFalse(item.shouldFeature());
    }
}
