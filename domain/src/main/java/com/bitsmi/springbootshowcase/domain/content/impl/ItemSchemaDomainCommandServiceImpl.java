package com.bitsmi.springbootshowcase.domain.content.impl;

import com.bitsmi.springbootshowcase.domain.content.IItemSchemaDomainCommandService;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;
import com.bitsmi.springbootshowcase.domain.content.spi.IItemSchemaRepositoryService;

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
