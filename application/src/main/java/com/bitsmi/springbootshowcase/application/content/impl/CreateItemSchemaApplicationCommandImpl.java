package com.bitsmi.springbootshowcase.application.content.impl;

import com.bitsmi.springbootshowcase.application.content.ICreateItemSchemaApplicationCommand;
import com.bitsmi.springbootshowcase.domain.content.IItemSchemaDomainCommandService;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class CreateItemSchemaApplicationCommandImpl implements ICreateItemSchemaApplicationCommand
{
    private final IItemSchemaDomainCommandService itemSchemaDomainCommandService;

    public CreateItemSchemaApplicationCommandImpl(IItemSchemaDomainCommandService itemSchemaDomainCommandService)
    {
        this.itemSchemaDomainCommandService = itemSchemaDomainCommandService;
    }

    @Override
    public ItemSchema createItemSchema(@Valid ItemSchema itemSchema)
    {
        return itemSchemaDomainCommandService.createItemSchema(itemSchema);
    }
}
