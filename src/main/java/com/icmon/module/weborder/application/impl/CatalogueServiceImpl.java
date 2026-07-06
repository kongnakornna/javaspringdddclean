package com.icmon.module.weborder.application.impl;

import com.icmon._shared.application.GenericAuthDomainServiceImpl;
import com.icmon.module.weborder.application.interfaces.CatalogueService;
import com.icmon.module.weborder.infrastructure.cache.CatalogueCacheService;
import com.icmon.module.weborder.infrastructure.entity.CatalogueCategoryEntity;
import com.icmon.module.weborder.infrastructure.entity.CatalogueItemEntity;
import com.icmon.module.weborder.infrastructure.mapper.CatalogueItemMapper;
import com.icmon.module.weborder.infrastructure.repository.CatalogueCategoryRepository;
import com.icmon.module.weborder.infrastructure.repository.CatalogueItemRepository;
import com.icmon.module.weborder.presentation.dto.response.CatalogueItemResponseDTO;
import com.icmon.module.weborder.presentation.dto.response.CatalogueCategoryResponseDTO;
import com.icmon.exception.SystemGlobalException;
import com.icmon.exception.models.FailedRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatalogueServiceImpl extends GenericAuthDomainServiceImpl implements CatalogueService {

    private final CatalogueItemRepository itemRepository;
    private final CatalogueCategoryRepository categoryRepository;
    private final CatalogueItemMapper itemMapper;
    private final CatalogueCacheService cacheService;

    @Override
    @Transactional(readOnly = true)
    public Page<CatalogueItemResponseDTO> listItems(Pageable pageable) throws SystemGlobalException {
        Page<CatalogueItemEntity> page = itemRepository.findByDeletedFalse(pageable);
        return page.map(this::toItemResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public CatalogueItemResponseDTO getItem(UUID id) throws SystemGlobalException {
        CatalogueItemEntity entity = itemRepository.findById(id)
                .orElseThrow(() -> new FailedRequestException("Catalogue item not found with id: " + id, null));
        return toItemResponseDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CatalogueItemResponseDTO> getItemsByCategory(UUID categoryId, Pageable pageable) throws SystemGlobalException {
        Page<CatalogueItemEntity> page = itemRepository.findByCategoryIdAndDeletedFalse(categoryId, pageable);
        return page.map(this::toItemResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CatalogueItemResponseDTO> searchItems(String keyword, Pageable pageable) throws SystemGlobalException {
        Page<CatalogueItemEntity> page = itemRepository.searchItems(keyword, pageable);
        return page.map(this::toItemResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CatalogueItemResponseDTO> getFeaturedItems() throws SystemGlobalException {
        List<CatalogueItemEntity> items = itemRepository.findByIsFeaturedTrueAndIsActiveTrueAndDeletedFalse();
        return items.stream().map(this::toItemResponseDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CatalogueCategoryResponseDTO> getCategories() throws SystemGlobalException {
        List<CatalogueCategoryEntity> categories = categoryRepository.findByIsActiveTrueAndDeletedFalseOrderBySortOrder();
        return categories.stream().map(this::toCategoryResponseDTO).collect(Collectors.toList());
    }

    private CatalogueItemResponseDTO toItemResponseDTO(CatalogueItemEntity entity) {
        return CatalogueItemResponseDTO.builder()
                .id(entity.getId())
                .itemCode(entity.getItemCode())
                .itemName(entity.getItemName())
                .itemNameEn(entity.getItemNameEn())
                .categoryId(entity.getCategoryId())
                .shortDescription(entity.getShortDescription())
                .brand(entity.getBrand())
                .imageUrl(entity.getImageUrl())
                .isActive(entity.getIsActive())
                .isFeatured(entity.getIsFeatured())
                .isNew(entity.getIsNew())
                .build();
    }

    private CatalogueCategoryResponseDTO toCategoryResponseDTO(CatalogueCategoryEntity entity) {
        return CatalogueCategoryResponseDTO.builder()
                .id(entity.getId())
                .categoryCode(entity.getCategoryCode())
                .categoryName(entity.getCategoryName())
                .categoryNameEn(entity.getCategoryNameEn())
                .parentId(entity.getParentId())
                .level(entity.getLevel())
                .sortOrder(entity.getSortOrder())
                .iconUrl(entity.getIconUrl())
                .isActive(entity.getIsActive())
                .build();
    }
}
