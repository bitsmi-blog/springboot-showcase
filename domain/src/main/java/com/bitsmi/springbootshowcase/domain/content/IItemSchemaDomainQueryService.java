package com.bitsmi.springbootshowcase.domain.content;

import com.bitsmi.springbootshowcase.domain.common.dto.Pagination;
import com.bitsmi.springbootshowcase.domain.common.dto.PagedData;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;

import java.util.List;

public interface IItemSchemaDomainQueryService
{
    List<ItemSchema> findAllItemSchemas();
    PagedData<ItemSchema> findAllItemSchemas(Pagination page);
}
