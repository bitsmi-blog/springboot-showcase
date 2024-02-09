package com.bitsmi.springbootshowcase.core.content.mapper;

import com.bitsmi.springbootshowcase.core.content.entity.ItemSchemaEntity;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        implementationName = "ItemSchemaMapperImpl",
        uses = {
            IItemSchemaFieldMapper.class
        }
)
public interface IItemSchemaMapper
{
    ItemSchema fromEntity(ItemSchemaEntity entity);
}
