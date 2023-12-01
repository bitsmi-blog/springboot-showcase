package com.bitsmi.springbootshowcase.web.content.mapper;

import com.bitsmi.springbootshowcase.api.content.response.ItemSchemaField;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        implementationName = "ItemSchemaFieldApiMapperImpl"
)
public interface IItemSchemaFieldApiMapper
{
    ItemSchemaField fromModel(com.bitsmi.springbootshowcase.core.content.model.ItemSchemaField model);
}
