package com.bitsmi.springbootshowcase.sample.domain.content;

import com.bitsmi.springbootshowcase.sample.domain.common.dto.PaginatedData;
import com.bitsmi.springbootshowcase.sample.domain.common.dto.Pagination;
import com.bitsmi.springbootshowcase.sample.domain.content.model.ItemSchema;

import java.util.List;

public interface IItemSchemaDomainQueryService
{
    List<ItemSchema> findAllItemSchemas();
    PaginatedData<ItemSchema> findAllItemSchemas(Pagination page);
}
