package com.bitsmi.springbootshowcase.core.content;

import com.bitsmi.springbootshowcase.core.content.model.Item;
import com.bitsmi.springbootshowcase.core.content.model.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Validated
public interface IItemManagementService
{
    public Optional<Item> findItemById(@NotNull Long id);

    public Item addItemTag(@NotNull Item item, @NotNull Tag tag);
}
