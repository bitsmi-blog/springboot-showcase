package com.bitsmi.springbootshowcase.application.content;

import com.bitsmi.springbootshowcase.domain.common.dto.Pagination;
import com.bitsmi.springbootshowcase.domain.common.dto.PagedData;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;

import java.util.List;

public interface IRetrieveItemSchemaApplicationQuery
{
    List<ItemSchema> retrieveAllItemSchemas();
    PagedData<ItemSchema> retrieveAllItemSchemas(Pagination page);
}
