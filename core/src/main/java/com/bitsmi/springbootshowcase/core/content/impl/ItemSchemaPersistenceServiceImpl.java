package com.bitsmi.springbootshowcase.core.content.impl;

import com.bitsmi.springbootshowcase.core.content.entity.ItemSchemaEntity;
import com.bitsmi.springbootshowcase.core.content.mapper.IItemSchemaMapper;
import com.bitsmi.springbootshowcase.core.content.repository.IItemSchemaRepository;
import com.bitsmi.springbootshowcase.domain.common.dto.Page;
import com.bitsmi.springbootshowcase.domain.common.dto.PagedData;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;
import com.bitsmi.springbootshowcase.domain.content.spi.IItemSchemaPersistenceService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class ItemSchemaPersistenceServiceImpl implements IItemSchemaPersistenceService
{
    @Autowired
    private IItemSchemaRepository itemSchemaRepository;
    @Autowired
    private IItemSchemaMapper itemSchemaMapper;

    @Override
    public List<ItemSchema> findAllItemSchemas()
    {
        return itemSchemaRepository.findAll()
                .stream()
                .map(itemSchemaMapper::fromEntity)
                .toList();
    }

    @Override
    public PagedData<ItemSchema> findAllItemSchemas(@NotNull Page page)
    {
        final Pageable pageable = PageRequest.of(page.number(), page.size());

        final org.springframework.data.domain.Page<ItemSchemaEntity> results = itemSchemaRepository.findAll(pageable);

        return PagedData.<ItemSchema>builder()
                .content(results.getContent()
                        .stream()
                        .map(itemSchemaMapper::fromEntity)
                        .toList())
                .pageSize(results.getSize())
                .totalElements(results.getTotalElements())
                .totalPages(results.getTotalPages())
                .build();
    }
}
