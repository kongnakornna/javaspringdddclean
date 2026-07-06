package com.icmon.module.weborder.presentation.controller;

import com.icmon.module.auth.infrastructure.ratelimit.RateLimit;
import com.icmon.module.weborder.application.interfaces.CatalogueService;
import com.icmon.module.weborder.presentation.dto.response.CatalogueItemResponseDTO;
import com.icmon.module.weborder.presentation.dto.response.CatalogueCategoryResponseDTO;
import com.icmon.exception.SystemGlobalException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wos/catalogue")
@Tag(name = "Web Order - Catalogue", description = "Product Catalogue APIs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class CatalogueController {

    private final CatalogueService catalogueService;

    @GetMapping
    @RateLimit(limit = 100, duration = 60, keyType = "USER_ID")
    @Operation(summary = "List catalogue items with pagination")
    public ResponseEntity<Page<CatalogueItemResponseDTO>> listCatalogueItems(Pageable pageable)
            throws SystemGlobalException {
        Page<CatalogueItemResponseDTO> page = catalogueService.listItems(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @RateLimit(limit = 200, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Get catalogue item by ID")
    public ResponseEntity<CatalogueItemResponseDTO> getItem(@PathVariable UUID id)
            throws SystemGlobalException {
        CatalogueItemResponseDTO response = catalogueService.getItem(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{categoryId}")
    @RateLimit(limit = 100, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Get items by category")
    public ResponseEntity<Page<CatalogueItemResponseDTO>> getItemsByCategory(
            @PathVariable UUID categoryId,
            Pageable pageable) throws SystemGlobalException {
        Page<CatalogueItemResponseDTO> page = catalogueService.getItemsByCategory(categoryId, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/search")
    @RateLimit(limit = 80, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Search catalogue items by keyword")
    public ResponseEntity<Page<CatalogueItemResponseDTO>> searchItems(
            @RequestParam String keyword,
            Pageable pageable) throws SystemGlobalException {
        Page<CatalogueItemResponseDTO> page = catalogueService.searchItems(keyword, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/featured")
    @RateLimit(limit = 100, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Get featured items for homepage")
    public ResponseEntity<List<CatalogueItemResponseDTO>> getFeaturedItems()
            throws SystemGlobalException {
        List<CatalogueItemResponseDTO> items = catalogueService.getFeaturedItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/categories")
    @RateLimit(limit = 50, duration = 60, keyType = "USER_ID")
    @Operation(summary = "Get all categories")
    public ResponseEntity<List<CatalogueCategoryResponseDTO>> getCategories()
            throws SystemGlobalException {
        List<CatalogueCategoryResponseDTO> categories = catalogueService.getCategories();
        return ResponseEntity.ok(categories);
    }
}
