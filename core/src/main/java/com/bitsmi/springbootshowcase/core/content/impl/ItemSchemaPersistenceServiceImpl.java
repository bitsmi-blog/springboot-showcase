package com.bitsmi.springbootshowcase.core.content.impl;

import com.bitsmi.springbootshowcase.core.CoreConstants;
import com.bitsmi.springbootshowcase.core.content.entity.DataType;
import com.bitsmi.springbootshowcase.core.content.entity.ItemSchemaEntity;
import com.bitsmi.springbootshowcase.core.content.entity.ItemSchemaFieldEntity;
import com.bitsmi.springbootshowcase.core.content.mapper.IItemSchemaMapper;
import com.bitsmi.springbootshowcase.core.content.mapper.IItemSchemaSummaryMapper;
import com.bitsmi.springbootshowcase.core.content.repository.IItemSchemaRepository;
import com.bitsmi.springbootshowcase.domain.common.dto.Page;
import com.bitsmi.springbootshowcase.domain.common.dto.PagedData;
import com.bitsmi.springbootshowcase.domain.common.exception.ElementAlreadyExistsException;
import com.bitsmi.springbootshowcase.domain.common.exception.ElementNotFoundException;
import com.bitsmi.springbootshowcase.domain.common.util.ValidToUpdate;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchemaSummary;
import com.bitsmi.springbootshowcase.domain.content.spi.IItemSchemaPersistenceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Validated
public class ItemSchemaPersistenceServiceImpl implements IItemSchemaPersistenceService
{
    @Autowired
    private IItemSchemaRepository itemSchemaRepository;
    @Autowired
    private IItemSchemaMapper itemSchemaMapper;
    @Autowired
    private IItemSchemaSummaryMapper itemSchemaSummaryMapper;

    @Override
    public List<ItemSchema> findAllItemSchemas()
    {
        return itemSchemaRepository.findAll()
                .stream()
                .map(itemSchemaMapper::fromEntity)
                .toList();
    }

    @Cacheable(cacheNames = CoreConstants.CACHE_ALL_SCHEMAS, key = "#page.number")
    @Override
    public PagedData<ItemSchema> findAllItemSchemas(@NotNull Page page)
    {
        final Pageable pageable = PageRequest.of(page.number(), page.size());

        final org.springframework.data.domain.Page<ItemSchemaEntity> entityPage = itemSchemaRepository.findAll(pageable);

        return PagedData.<ItemSchema>builder()
                .content(entityPage.getContent()
                        .stream()
                        .map(itemSchemaMapper::fromEntity)
                        .toList())
                .pageNumber(entityPage.getNumber())
                .pageSize(entityPage.getSize())
                .totalElements(entityPage.getTotalElements())
                .totalPages(entityPage.getTotalPages())
                .build();
    }

    @Override
    public PagedData<ItemSchema> findSchemasByNameStartWith(@NotNull String namePrefix, @NotNull Page page)
    {
        final Pageable pageable = PageRequest.of(page.number(), page.size());

        final org.springframework.data.domain.Page<ItemSchemaEntity> entityPage = itemSchemaRepository.findByNameStartsWithIgnoreCase(namePrefix, pageable);
        return PagedData.<ItemSchema>builder()
                .content(entityPage
                        .stream()
                        .map(itemSchemaMapper::fromEntity)
                        .toList())
                .pageNumber(entityPage.getNumber())
                .pageSize(entityPage.getSize())
                .totalElements(entityPage.getTotalElements())
                .totalPages(entityPage.getTotalPages())
                .build();
    }

    @Cacheable(cacheNames = CoreConstants.CACHE_SCHEMA_BY_ID)
    @Override
    public Optional<ItemSchema> findItemSchemaById(@NotNull Long id)
    {
        return itemSchemaRepository.findById(id)
                .map(itemSchemaMapper::fromEntity);
    }

    @Override
    public Optional<ItemSchema> findItemSchemaByExternalId(@NotNull String externalId)
    {
        return itemSchemaRepository.findThroughExternalId(externalId)
                .map(itemSchemaMapper::fromEntity);
    }

    @Override
    public Optional<ItemSchemaSummary> findItemSchemaSummaryByExternalId(@NotNull String externalId)
    {
        return itemSchemaRepository.findSummaryProjectionByExternalId(externalId)
                .map(itemSchemaSummaryMapper::fromProjection);
    }

    @Override
    public Optional<ItemSchemaSummary> findItemSchemaSummaryUsingQueryByExternalId(@NotNull String externalId)
    {
        return itemSchemaRepository.findSummaryProjectionUsingQueryByExternalId(externalId)
                .map(itemSchemaSummaryMapper::fromProjection);
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = CoreConstants.CACHE_ALL_SCHEMAS, allEntries = true)
    @Override
    public ItemSchema createItemSchema(@Valid ItemSchema itemSchema)
    {
        if(itemSchemaRepository.existsByExternalId(itemSchema.externalId())) {
            throw new ElementAlreadyExistsException(ItemSchema.class.getSimpleName(), "externalId:" + itemSchema.externalId());
        }

        final ItemSchemaEntity schemaEntity = ItemSchemaEntity.builder()
                .externalId(itemSchema.externalId())
                .name(itemSchema.name())
                .build();
        schemaEntity.setFields(itemSchema.fields().stream()
                .map(fieldDto -> ItemSchemaFieldEntity.builder()
                        .schema(schemaEntity)
                        .name(fieldDto.name())
                        .dataType(DataType.valueOf(fieldDto.dataType().name()))
                        .comments(fieldDto.comments())
                        .build()
                )
                .collect(Collectors.toSet())
        );

        return itemSchemaMapper.fromEntity(itemSchemaRepository.save(schemaEntity));
    }

    @Transactional(rollbackFor = Exception.class)
    @Caching(evict = {
            @CacheEvict(cacheNames = CoreConstants.CACHE_ALL_SCHEMAS, allEntries = true),
            @CacheEvict(cacheNames = CoreConstants.CACHE_SCHEMA_BY_ID, key = "#itemSchema.id")
    })
    @Override
    public ItemSchema updateItemSchema(@Valid @ValidToUpdate ItemSchema itemSchema)
    {
        ItemSchemaEntity schemaEntity = itemSchemaRepository.findById(itemSchema.id())
                .orElseThrow(() -> new ElementNotFoundException(ItemSchema.class.getSimpleName(), "externalId:" + itemSchema.externalId()));

        schemaEntity.setExternalId(itemSchema.externalId());
        schemaEntity.setName(itemSchema.name());

        Map<String, ItemSchemaFieldEntity> idxCurrentEntityFields = schemaEntity.getFields().stream()
                .collect(Collectors.toMap(ItemSchemaFieldEntity::getName, Function.identity()));

        itemSchema.fields()
                .forEach(fieldDto -> {
                    ItemSchemaFieldEntity fieldEntity = idxCurrentEntityFields.get(fieldDto.name());
                    if(fieldEntity==null) {
                        fieldEntity = new ItemSchemaFieldEntity();
                        fieldEntity.setSchema(schemaEntity);
                        schemaEntity.getFields().add(fieldEntity);
                    }
                    fieldEntity.setName(fieldDto.name());
                    fieldEntity.setDataType(DataType.valueOf(fieldDto.dataType().name()));
                    fieldEntity.setComments(fieldDto.comments());

                    idxCurrentEntityFields.remove(fieldDto.name());
                });

        // Remove fields not present in the new schema definition
        idxCurrentEntityFields.values().forEach(field -> schemaEntity.getFields().remove(field));

        return itemSchemaMapper.fromEntity(itemSchemaRepository.save(schemaEntity));
    }
}
