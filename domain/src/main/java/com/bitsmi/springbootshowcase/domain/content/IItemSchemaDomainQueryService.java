package com.bitsmi.springbootshowcase.domain.content;

import com.bitsmi.springbootshowcase.domain.common.dto.PaginatedData;
import com.bitsmi.springbootshowcase.domain.common.dto.Pagination;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;

import java.util.List;

public interface IItemSchemaDomainQueryService
{
    List<ItemSchema> findAllItemSchemas();
    PaginatedData<ItemSchema> findAllItemSchemas(Pagination page);
}
