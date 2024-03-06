package com.bitsmi.springbootshowcase.domain.content.spi;

import com.bitsmi.springbootshowcase.domain.common.dto.Pagination;
import com.bitsmi.springbootshowcase.domain.common.dto.PagedData;
import com.bitsmi.springbootshowcase.domain.common.util.ValidToUpdate;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchema;
import com.bitsmi.springbootshowcase.domain.content.model.ItemSchemaSummary;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

public interface IItemSchemaPersistenceService
{
    List<ItemSchema> findAllItemSchemas();
    PagedData<ItemSchema> findAllItemSchemas(@NotNull Pagination pagination);

    PagedData<ItemSchema> findSchemasByNameStartWith(@NotNull String namePrefix, @NotNull Pagination pagination);

    Optional<ItemSchema> findItemSchemaById(@NotNull Long id);

    Optional<ItemSchema> findItemSchemaByExternalId(@NotNull String externalId);

    Optional<ItemSchemaSummary> findItemSchemaSummaryByExternalId(@NotNull String externalId);
    Optional<ItemSchemaSummary> findItemSchemaSummaryUsingQueryByExternalId(@NotNull String externalId);

    ItemSchema createItemSchema(@Valid ItemSchema itemSchema);
    ItemSchema updateItemSchema(@Valid @ValidToUpdate ItemSchema itemSchema);
}