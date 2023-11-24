package com.bitsmi.springbootshowcase.core.content.mapper;

import com.bitsmi.springbootshowcase.core.content.entity.TagEntity;
import com.bitsmi.springbootshowcase.core.content.model.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        implementationName = "TagMapperImpl",
        uses = {
                IItemSchemaMapper.class
        }
)
public interface ITagMapper
{
    Tag fromEntity(TagEntity entity);
}
