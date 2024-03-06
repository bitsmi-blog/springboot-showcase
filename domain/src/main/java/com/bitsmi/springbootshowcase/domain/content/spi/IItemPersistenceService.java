package com.bitsmi.springbootshowcase.domain.content.spi;

import com.bitsmi.springbootshowcase.domain.content.model.Item;
import com.bitsmi.springbootshowcase.domain.content.model.Tag;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface IItemPersistenceService
{
    Optional<Item> findItemById(@NotNull Long id);

    Item addItemTag(@NotNull Item item, @NotNull Tag tag);
}