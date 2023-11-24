package com.bitsmi.springbootshowcase.core.content.mapper;

import com.bitsmi.springbootshowcase.core.content.entity.ItemSchemaFieldEntity;
import com.bitsmi.springbootshowcase.core.content.model.ItemSchemaField;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        implementationName = "ItemSchemaFieldMapperImpl"
)
public interface IItemSchemaFieldMapper
{
    ItemSchemaField fromEntity(ItemSchemaFieldEntity entity);
}
