package com.bitsmi.springbootshowcase.application.content;

import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;
import jakarta.validation.Valid;

public interface ICreateItemSchemaApplicationCommand
{
    ItemSchema createItemSchema(@Valid ItemSchema itemSchema);
}
