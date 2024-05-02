package com.bitsmi.springbootshowcase.infrastructure.content.repository;

import com.bitsmi.springbootshowcase.infrastructure.content.projection.IItemSummaryProjection;

import java.util.Optional;

public interface ItemExtRepository
{
    Optional<IItemSummaryProjection> findSummaryProjectionUsingQueryByName(final String name);
}
