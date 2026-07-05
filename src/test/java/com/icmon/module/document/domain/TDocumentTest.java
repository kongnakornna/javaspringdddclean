package com.icmon.module.document.domain;

import com.icmon.module.document.domain.enums.DocumentStatus;
import com.icmon.module.document.domain.enums.DocumentType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TDocumentTest {

    @Test
    void shouldMarkAsSent() {
        TDocument doc = new TDocument();
        doc.setStatus(DocumentStatus.GENERATED);
        doc.markAsSent("test@example.com");
        assertEquals(DocumentStatus.SENT, doc.getStatus());
        assertNotNull(doc.getSentAt());
        assertEquals("test@example.com", doc.getSentToEmail());
    }

    @Test
    void shouldNotSendDeletedDocument() {
        TDocument doc = new TDocument();
        doc.setStatus(DocumentStatus.DELETED);
        assertThrows(IllegalStateException.class, () -> doc.markAsSent("test@example.com"));
    }

    @Test
    void shouldArchiveDocument() {
        TDocument doc = new TDocument();
        doc.setStatus(DocumentStatus.GENERATED);
        doc.archive();
        assertEquals(DocumentStatus.ARCHIVED, doc.getStatus());
    }

    @Test
    void shouldNotArchiveDeletedDocument() {
        TDocument doc = new TDocument();
        doc.setStatus(DocumentStatus.DELETED);
        assertThrows(IllegalStateException.class, doc::archive);
    }

    @Test
    void shouldAllowDownloadWhenNotDeletedAndHasPath() {
        TDocument doc = new TDocument();
        doc.setStatus(DocumentStatus.GENERATED);
        doc.setFilePath("/path/to/file.pdf");
        assertTrue(doc.canDownload());
    }

    @Test
    void shouldNotAllowDownloadWhenDeleted() {
        TDocument doc = new TDocument();
        doc.setStatus(DocumentStatus.DELETED);
        doc.setFilePath("/path/to/file.pdf");
        assertFalse(doc.canDownload());
    }

    @Test
    void shouldNotAllowDownloadWhenNoFilePath() {
        TDocument doc = new TDocument();
        doc.setStatus(DocumentStatus.GENERATED);
        assertFalse(doc.canDownload());
    }
}
