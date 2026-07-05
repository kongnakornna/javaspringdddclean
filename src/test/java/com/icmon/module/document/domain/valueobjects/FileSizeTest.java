package com.icmon.module.document.domain.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileSizeTest {

    @Test
    void shouldCreateValidFileSize() {
        FileSize fs = new FileSize(1024L);
        assertEquals(1024L, fs.bytes());
    }

    @Test
    void shouldRejectNullBytes() {
        assertThrows(IllegalArgumentException.class, () -> new FileSize(null));
    }

    @Test
    void shouldRejectNegativeBytes() {
        assertThrows(IllegalArgumentException.class, () -> new FileSize(-1L));
    }

    @Test
    void shouldFormatBytes() {
        assertEquals("500 B", new FileSize(500L).toHumanReadable());
    }

    @Test
    void shouldFormatKB() {
        assertEquals("1.5 KB", new FileSize(1536L).toHumanReadable());
    }

    @Test
    void shouldFormatMB() {
        assertEquals("1.0 MB", new FileSize(1048576L).toHumanReadable());
    }

    @Test
    void shouldFormatGB() {
        assertEquals("1.0 GB", new FileSize(1073741824L).toHumanReadable());
    }
}
