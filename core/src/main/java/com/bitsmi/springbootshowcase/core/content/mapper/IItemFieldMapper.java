package com.bitsmi.springbootshowcase.core.content.mapper;

import com.bitsmi.springbootshowcase.core.content.entity.ItemFieldEntity;
import com.bitsmi.springbootshowcase.core.content.model.ItemField;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        implementationName = "ItemFieldMapperImpl",
        uses = {
            IItemSchemaFieldMapper.class
        }
)
public interface IItemFieldMapper
{
    ItemField fromEntity(ItemFieldEntity entity);
}
