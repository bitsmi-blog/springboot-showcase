package com.bitsmi.springbootshowcase.core.content.impl;

import com.bitsmi.springbootshowcase.core.common.exception.ElementAlreadyExistsException;
import com.bitsmi.springbootshowcase.core.common.exception.ElementNotFoundException;
import com.bitsmi.springbootshowcase.core.common.util.ValidToUpdate;
import com.bitsmi.springbootshowcase.core.content.IItemSchemaManagementService;
import com.bitsmi.springbootshowcase.core.content.entity.DataType;
import com.bitsmi.springbootshowcase.core.content.entity.ItemSchemaEntity;
import com.bitsmi.springbootshowcase.core.content.entity.ItemSchemaFieldEntity;
import com.bitsmi.springbootshowcase.core.content.mapper.IItemSchemaMapper;
import com.bitsmi.springbootshowcase.core.content.mapper.IItemSchemaSummaryModelMapper;
import com.bitsmi.springbootshowcase.core.content.model.ItemSchema;
import com.bitsmi.springbootshowcase.core.content.model.ItemSchemaSummary;
import com.bitsmi.springbootshowcase.core.content.repository.IItemSchemaRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
public class ItemSchemaManagementServiceImpl implements IItemSchemaManagementService
{
    @Autowired
    private IItemSchemaRepository itemSchemaRepository;
    @Autowired
    private IItemSchemaMapper itemSchemaMapper;
    @Autowired
    private IItemSchemaSummaryModelMapper itemSchemaSummaryMapper;

    @Override
    public Page<ItemSchema> findAllSchemas(@NotNull Pageable pageable)
    {
        Page<ItemSchemaEntity> entityPage = itemSchemaRepository.findAll(pageable);
        return mapPage(entityPage);
    }

    @Override
    public Page<ItemSchema> findSchemasByNameStartWith(@NotNull String namePrefix, @NotNull Pageable pageable)
    {
        Page<ItemSchemaEntity> entityPage = itemSchemaRepository.findByNameStartsWithIgnoreCase(namePrefix, pageable);
        return mapPage(entityPage);
    }

    private Page<ItemSchema> mapPage(Page<ItemSchemaEntity> entityPage)
    {
        List<ItemSchema> results = entityPage.stream()
                .map(itemSchemaMapper::fromEntity)
                .toList();

        return new PageImpl<>(results, entityPage.getPageable(), entityPage.getTotalElements());
    }

    @Override
    public Optional<ItemSchema> findSchemaById(@NotNull Long id)
    {
        return itemSchemaRepository.findById(id)
                .map(itemSchemaMapper::fromEntity);
    }

    @Override
    public Optional<ItemSchema> findSchemaByExternalId(@NotNull String externalId)
    {
        return itemSchemaRepository.findByExternalId(externalId)
                .map(itemSchemaMapper::fromEntity);
    }

    @Override
    public Optional<ItemSchema> findSchemaByName(@NotNull String name)
    {
        return itemSchemaRepository.findByName(name)
                .map(itemSchemaMapper::fromEntity);
    }

    @Override
    public Optional<ItemSchemaSummary> findSchemaSummaryByExternalId(@NotNull String externalId)
    {
        return itemSchemaRepository.findSummaryProjectionByExternalId(externalId)
                .map(itemSchemaSummaryMapper::fromProjection);
    }

    @Override
    public Optional<ItemSchemaSummary> findSchemaSummaryUsingQueryByExternalId(@NotNull String externalId)
    {
        return itemSchemaRepository.findSummaryProjectionUsingQueryByExternalId(externalId)
                .map(itemSchemaSummaryMapper::fromProjection);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ItemSchema createSchema(@Valid ItemSchema itemSchema)
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
    @Override
    public ItemSchema updateSchema(@Valid @ValidToUpdate ItemSchema itemSchema)
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
