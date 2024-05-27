package com.bitsmi.springbootshowcase.sample.domain.content.impl;

import com.bitsmi.springbootshowcase.sample.domain.common.dto.PaginatedData;
import com.bitsmi.springbootshowcase.sample.domain.common.dto.Pagination;
import com.bitsmi.springbootshowcase.sample.domain.content.IItemSchemaDomainQueryService;
import com.bitsmi.springbootshowcase.sample.domain.content.model.ItemSchema;
import com.bitsmi.springbootshowcase.sample.domain.content.spi.IItemSchemaRepositoryService;
import java.util.List;

public class ItemSchemaDomainQueryServiceImpl implements IItemSchemaDomainQueryService
{
    private final IItemSchemaRepositoryService itemSchemaRepositoryService;

    public ItemSchemaDomainQueryServiceImpl(
        IItemSchemaRepositoryService itemSchemaRepositoryService)
    {
        this.itemSchemaRepositoryService = itemSchemaRepositoryService;
    }

    @Override
    public List<ItemSchema> findAllItemSchemas()
    {
        return itemSchemaRepositoryService.findAllItemSchemas();
    }

    @Override
    public PaginatedData<ItemSchema> findAllItemSchemas(Pagination page)
    {
        return itemSchemaRepositoryService.findAllItemSchemas(page);
    }
}
