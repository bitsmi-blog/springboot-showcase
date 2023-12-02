package com.bitsmi.springbootshowcase.core.content;

import com.bitsmi.springbootshowcase.core.common.util.ValidToUpdate;
import com.bitsmi.springbootshowcase.core.content.model.ItemSchema;
import com.bitsmi.springbootshowcase.core.content.model.ItemSchemaSummary;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IItemSchemaManagementService
{
    Page<ItemSchema> findAllSchemas(@NotNull Pageable pageable);
    Page<ItemSchema> findSchemasByNameStartWith(@NotNull String namePrefix, @NotNull Pageable pageable);

    Optional<ItemSchema> findSchemaById(@NotNull Long id);
    Optional<ItemSchema> findSchemaByExternalId(@NotNull String externalId);
    Optional<ItemSchema> findSchemaByName(@NotNull String name);

    Optional<ItemSchemaSummary> findSchemaSummaryByExternalId(@NotNull String externalId);
    Optional<ItemSchemaSummary> findSchemaSummaryUsingQueryByExternalId(@NotNull String externalId);

    ItemSchema createSchema(@Valid ItemSchema itemSchema);
    ItemSchema updateSchema(@Valid @ValidToUpdate ItemSchema itemSchema);
}
