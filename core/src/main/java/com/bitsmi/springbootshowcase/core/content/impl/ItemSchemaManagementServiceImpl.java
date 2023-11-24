package com.bitsmi.springbootshowcase.core.content.impl;

import com.bitsmi.springbootshowcase.core.common.exception.ElementAlreadyExistsException;
import com.bitsmi.springbootshowcase.core.common.exception.ElementNotFoundException;
import com.bitsmi.springbootshowcase.core.content.IItemSchemaManagementService;
import com.bitsmi.springbootshowcase.core.content.dto.ItemSchemaDto;
import com.bitsmi.springbootshowcase.core.content.entity.DataType;
import com.bitsmi.springbootshowcase.core.content.entity.ItemSchemaEntity;
import com.bitsmi.springbootshowcase.core.content.entity.ItemSchemaFieldEntity;
import com.bitsmi.springbootshowcase.core.content.mapper.IItemSchemaMapper;
import com.bitsmi.springbootshowcase.core.content.model.ItemSchema;
import com.bitsmi.springbootshowcase.core.content.repository.IItemSchemaRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public List<ItemSchema> findAllSchemas()
    {
        return itemSchemaRepository.findAll()
                .stream()
                .map(itemSchemaMapper::fromEntity)
                .toList();
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ItemSchema createSchema(@Valid ItemSchemaDto itemSchemaDto)
    {
        if(itemSchemaRepository.existsByExternalId(itemSchemaDto.externalId())) {
            throw new ElementAlreadyExistsException(ItemSchemaDto.class.getSimpleName(), "externalId:" + itemSchemaDto.externalId());
        }

        final ItemSchemaEntity schemaEntity = ItemSchemaEntity.builder()
                .externalId(itemSchemaDto.externalId())
                .name(itemSchemaDto.name())
                .build();
        schemaEntity.setFields(itemSchemaDto.fields().stream()
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
    public ItemSchema updateSchema(@NotNull Long id, @Valid ItemSchemaDto itemSchemaDto)
    {
        ItemSchemaEntity schemaEntity = itemSchemaRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException(ItemSchemaDto.class.getSimpleName(), "externalId:" + itemSchemaDto.externalId()));

        schemaEntity.setExternalId(itemSchemaDto.externalId());
        schemaEntity.setName(itemSchemaDto.name());

        Map<String, ItemSchemaFieldEntity> idxCurrentEntityFields = schemaEntity.getFields().stream()
                .collect(Collectors.toMap(ItemSchemaFieldEntity::getName, Function.identity()));

        itemSchemaDto.fields()
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
