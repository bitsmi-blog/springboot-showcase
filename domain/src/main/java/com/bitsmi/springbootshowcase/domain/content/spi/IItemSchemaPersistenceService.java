package com.bitsmi.springbootshowcase.domain.content.spi;

import com.bitsmi.springbootshowcase.domain.common.dto.Page;
import com.bitsmi.springbootshowcase.domain.common.dto.PagedData;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface IItemSchemaPersistenceService
{
    List<ItemSchema> findAllItemSchemas();
    PagedData<ItemSchema> findAllItemSchemas(@NotNull Page page);
}
