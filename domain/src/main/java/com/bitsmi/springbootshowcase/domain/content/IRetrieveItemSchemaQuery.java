package com.bitsmi.springbootshowcase.domain.content;

import com.bitsmi.springbootshowcase.domain.common.dto.Page;
import com.bitsmi.springbootshowcase.domain.common.dto.PagedData;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;

import java.util.List;

public interface IRetrieveItemSchemaQuery
{
    List<ItemSchema> retrieveAllItemSchemas();
    PagedData<ItemSchema> retrieveAllItemSchemas(Page page);
}
