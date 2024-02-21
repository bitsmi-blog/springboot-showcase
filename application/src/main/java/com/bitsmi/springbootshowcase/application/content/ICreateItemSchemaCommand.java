package com.bitsmi.springbootshowcase.application.content;

import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;

public interface ICreateItemSchemaCommand
{
    ItemSchema createItemSchema(ItemSchema itemSchema);
}
