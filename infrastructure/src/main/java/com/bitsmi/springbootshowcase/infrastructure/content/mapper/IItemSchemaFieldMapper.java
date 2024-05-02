package com.bitsmi.springbootshowcase.infrastructure.content.mapper;

import com.bitsmi.springbootshowcase.domain.content.model.ItemSchemaField;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        implementationName = "ItemSchemaFieldMapperImpl"
)
public interface IItemSchemaFieldMapper
{
    ItemSchemaField fromClientResponse(com.bitsmi.springshowcase.contentservice.client.schema.response.ItemSchemaField response);
}
