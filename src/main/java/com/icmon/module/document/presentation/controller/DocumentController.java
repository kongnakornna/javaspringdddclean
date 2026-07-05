package com.icmon.module.document.presentation.controller;

import com.icmon.exception.SystemGlobalException;
import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.document.application.interfaces.DocumentService;
import com.icmon.module.document.application.usecase.*;
import com.icmon.module.document.presentation.dto.request.DocumentGenerateRequestDTO;
import com.icmon.module.document.presentation.dto.request.DocumentSearchRequestDTO;
import com.icmon.module.document.presentation.dto.response.DocumentHistoryDTO;
import com.icmon.module.document.presentation.dto.response.DocumentResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
@Tag(name = "Document Management", description = "จัดการเอกสาร // Document management (upload, generate, download)")
public class DocumentController {

    private final GenerateDocumentUseCase generateDocumentUseCase;
    private final UploadDocumentUseCase uploadDocumentUseCase;
    private final GetDocumentUseCase getDocumentUseCase;
    private final SearchDocumentUseCase searchDocumentUseCase;
    private final GetDocumentHistoryUseCase getDocumentHistoryUseCase;
    private final DeleteDocumentUseCase deleteDocumentUseCase;
    private final DocumentService documentService;

    @PostMapping("/generate")
    @RateLimit(limit = 10, duration = 60, keyType = "USER_ID")
    @Operation(summary = "สร้างเอกสารอัตโนมัติจากเทมเพลต // Auto-generate document from template")
    public ResponseEntity<DocumentResponseDTO> generateDocument(@Valid @RequestBody DocumentGenerateRequestDTO request) throws SystemGlobalException {
        return ResponseEntity.ok(generateDocumentUseCase.execute(request));
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @RateLimit(limit = 20, duration = 60, keyType = "USER_ID")
    @Operation(summary = "อัปโหลดเอกสาร // Upload a document file")
    public ResponseEntity<DocumentResponseDTO> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("documentType") String documentType,
            @RequestParam(value = "referenceType", required = false) String referenceType,
            @RequestParam(value = "referenceId", required = false) UUID referenceId,
            @RequestParam(value = "description", required = false) String description) throws SystemGlobalException {
        return ResponseEntity.ok(uploadDocumentUseCase.execute(file, documentType, referenceType, referenceId, description));
    }

    @GetMapping("/{id}")
    @RateLimit(limit = 60, duration = 60, keyType = "USER_ID")
    @Operation(summary = "ค้นหาเอกสารตาม ID // Get document by ID")
    public ResponseEntity<DocumentResponseDTO> getDocument(@PathVariable UUID id) throws SystemGlobalException {
        return ResponseEntity.ok(getDocumentUseCase.byId(id));
    }

    @GetMapping("/by-reference")
    @RateLimit(limit = 60, duration = 60, keyType = "USER_ID")
    @Operation(summary = "ค้นหาเอกสารตาม reference // Get document by reference type and ID")
    public ResponseEntity<DocumentResponseDTO> getDocumentByReference(
            @RequestParam String referenceType,
            @RequestParam UUID referenceId) throws SystemGlobalException {
        return ResponseEntity.ok(getDocumentUseCase.byReference(referenceType, referenceId));
    }

    @GetMapping
    @RateLimit(limit = 60, duration = 60, keyType = "USER_ID")
    @Operation(summary = "ค้นหาเอกสารตามเงื่อนไข // Search documents with filters")
    public ResponseEntity<Page<DocumentResponseDTO>> searchDocuments(
            @Valid DocumentSearchRequestDTO request,
            @PageableDefault(size = 20) Pageable pageable) throws SystemGlobalException {
        return ResponseEntity.ok(searchDocumentUseCase.execute(request, pageable));
    }

    @GetMapping("/{id}/download")
    @RateLimit(limit = 30, duration = 60, keyType = "USER_ID")
    @Operation(summary = "ดาวน์โหลดไฟล์เอกสาร // Download document file")
    public ResponseEntity<Resource> downloadDocument(@PathVariable UUID id) throws SystemGlobalException {
        Resource resource = documentService.downloadDocument(id);
        String fileName = documentService.getDocumentFileName(id);
        String mimeType = documentService.getDocumentMimeType(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename(fileName).build());
        headers.setContentType(MediaType.parseMediaType(mimeType != null ? mimeType : "application/octet-stream"));
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @GetMapping("/{id}/history")
    @RateLimit(limit = 60, duration = 60, keyType = "USER_ID")
    @Operation(summary = "ดูประวัติเอกสาร // Get document history")
    public ResponseEntity<List<DocumentHistoryDTO>> getDocumentHistory(@PathVariable UUID id) throws SystemGlobalException {
        return ResponseEntity.ok(getDocumentHistoryUseCase.execute(id));
    }

    @DeleteMapping("/{id}")
    @RateLimit(limit = 10, duration = 60, keyType = "USER_ID")
    @Operation(summary = "ลบเอกสาร // Delete document")
    public ResponseEntity<Void> deleteDocument(@PathVariable UUID id) throws SystemGlobalException {
        deleteDocumentUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
