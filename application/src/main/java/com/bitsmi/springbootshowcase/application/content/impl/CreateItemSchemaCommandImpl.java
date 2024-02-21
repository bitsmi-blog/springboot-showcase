package com.bitsmi.springbootshowcase.application.content.impl;

import com.bitsmi.springbootshowcase.application.content.ICreateItemSchemaCommand;
import com.bitsmi.springbootshowcase.domain.content.IItemSchemaCommandDomainService;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class CreateItemSchemaCommandImpl implements ICreateItemSchemaCommand
{
    @Autowired
    private IItemSchemaCommandDomainService itemSchemaCommandDomainService;

    @Override
    public ItemSchema createItemSchema(@Valid ItemSchema itemSchema)
    {
        return itemSchemaCommandDomainService.createItemSchema(itemSchema);
    }
}
