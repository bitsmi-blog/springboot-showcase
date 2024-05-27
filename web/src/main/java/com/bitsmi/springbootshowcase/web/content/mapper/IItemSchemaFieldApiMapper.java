package com.bitsmi.springbootshowcase.web.content.mapper;

import com.bitsmi.springbootshowcase.api.content.response.ItemSchemaField;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        implementationName = "ItemSchemaFieldApiMapperImpl"
)
public interface IItemSchemaFieldApiMapper
{
    ItemSchemaField mapResponseFromModel(com.bitsmi.springbootshowcase.domain.content.model.ItemSchemaField model);
}
