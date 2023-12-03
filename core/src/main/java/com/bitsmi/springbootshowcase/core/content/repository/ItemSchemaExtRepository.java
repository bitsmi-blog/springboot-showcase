package com.bitsmi.springbootshowcase.core.content.repository;

import com.bitsmi.springbootshowcase.core.content.projection.IItemSchemaSummaryProjection;

import java.util.Optional;

public interface ItemSchemaExtRepository
{
    Optional<IItemSchemaSummaryProjection> findSummaryProjectionUsingQueryByExternalId(final String externalId);
}
