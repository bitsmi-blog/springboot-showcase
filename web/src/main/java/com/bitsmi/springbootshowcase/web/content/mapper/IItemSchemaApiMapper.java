package com.bitsmi.springbootshowcase.web.content.mapper;

import com.bitsmi.springbootshowcase.api.content.request.CreateItemSchemaRequest;
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
    ItemSchema mapResponseFromModel(com.bitsmi.springbootshowcase.domain.content.model.ItemSchema model);
    com.bitsmi.springbootshowcase.domain.content.model.ItemSchema mapModelFromRequest(CreateItemSchemaRequest request);
}
