package com.bitsmi.springbootshowcase.web.content.mapper;

import com.bitsmi.springbootshowcase.api.content.response.ItemSchema;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        implementationName = "ItemSchemaApiMapperImpl",
        uses = {
                IItemSchemaFieldApiMapper.class
        }
)
public interface IItemSchemaApiMapper
{
    ItemSchema fromModel(com.bitsmi.springbootshowcase.core.content.model.ItemSchema model);
}
