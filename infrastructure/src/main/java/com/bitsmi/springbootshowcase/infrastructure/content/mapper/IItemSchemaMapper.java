package com.bitsmi.springbootshowcase.infrastructure.content.mapper;

import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        implementationName = "ItemSchemaMapperImpl",
        uses = {
            IItemSchemaFieldMapper.class
        }
)
public interface IItemSchemaMapper
{
    ItemSchema fromClientResponse(com.bitsmi.springshowcase.contentservice.client.schema.response.ItemSchema response);
}
