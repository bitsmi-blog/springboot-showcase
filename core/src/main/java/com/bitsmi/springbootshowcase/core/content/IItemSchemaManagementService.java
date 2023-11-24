package com.bitsmi.springbootshowcase.core.content;

import com.bitsmi.springbootshowcase.core.content.dto.ItemSchemaDto;
import com.bitsmi.springbootshowcase.core.content.model.ItemSchema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

public interface IItemSchemaManagementService
{
    List<ItemSchema> findAllSchemas();

    Optional<ItemSchema> findSchemaById(@NotNull Long id);
    Optional<ItemSchema> findSchemaByExternalId(@NotNull String externalId);

    ItemSchema createSchema(@Valid ItemSchemaDto itemSchemaDto);
    ItemSchema updateSchema(@NotNull Long id, @Valid ItemSchemaDto itemSchemaDto);
}
