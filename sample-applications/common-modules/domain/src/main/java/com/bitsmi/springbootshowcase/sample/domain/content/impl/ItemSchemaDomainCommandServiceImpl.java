package com.bitsmi.springbootshowcase.sample.domain.content.impl;

import com.bitsmi.springbootshowcase.sample.domain.content.IItemSchemaDomainCommandService;
import com.bitsmi.springbootshowcase.sample.domain.content.model.ItemSchema;
import com.bitsmi.springbootshowcase.sample.domain.content.spi.IItemSchemaRepositoryService;

public class ItemSchemaDomainCommandServiceImpl implements IItemSchemaDomainCommandService
{
    private final IItemSchemaRepositoryService itemSchemaRepositoryService;

    public ItemSchemaDomainCommandServiceImpl(
        IItemSchemaRepositoryService itemSchemaRepositoryService)
    {
        this.itemSchemaRepositoryService = itemSchemaRepositoryService;
    }

    @Override
    public ItemSchema createItemSchema(ItemSchema itemSchema)
    {
        return itemSchemaRepositoryService.createItemSchema(itemSchema);
    }
}
