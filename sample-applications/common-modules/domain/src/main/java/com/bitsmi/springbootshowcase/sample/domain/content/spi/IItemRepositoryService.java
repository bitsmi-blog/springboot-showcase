package com.bitsmi.springbootshowcase.sample.domain.content.spi;

import com.bitsmi.springbootshowcase.sample.domain.content.model.Item;
import com.bitsmi.springbootshowcase.sample.domain.content.model.Tag;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public interface IItemRepositoryService
{
    Optional<Item> findItemById(@NotNull Long id);

    Item addItemTag(@NotNull Item item, @NotNull Tag tag);
}
