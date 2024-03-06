package com.bitsmi.springbootshowcase.infrastructure.content.repository;

import com.bitsmi.springbootshowcase.infrastructure.content.projection.IItemSchemaSummaryProjection;

import java.util.Optional;

public interface ItemSchemaExtRepository
{
    Optional<IItemSchemaSummaryProjection> findSummaryProjectionUsingQueryByExternalId(final String externalId);
}
