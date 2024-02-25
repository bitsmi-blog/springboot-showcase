package com.bitsmi.springbootshowcase.core.content.impl;

import com.bitsmi.springbootshowcase.core.content.entity.ItemEntity;
import com.bitsmi.springbootshowcase.core.content.entity.TagEntity;
import com.bitsmi.springbootshowcase.core.content.mapper.IItemMapper;
import com.bitsmi.springbootshowcase.core.content.repository.IItemRepository;
import com.bitsmi.springbootshowcase.core.content.repository.ITagRepository;
import com.bitsmi.springbootshowcase.domain.common.exception.ElementNotFoundException;
import com.bitsmi.springbootshowcase.domain.content.model.Item;
import com.bitsmi.springbootshowcase.domain.content.model.Tag;
import com.bitsmi.springbootshowcase.domain.content.spi.IItemPersistenceService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Validated
public class ItemPersistenceServiceImpl implements IItemPersistenceService
{
    @Autowired
    private IItemRepository itemRepository;
    @Autowired
    private ITagRepository tagRepository;
    @Autowired
    private IItemMapper itemMapper;

    @Override
    public Optional<Item> findItemById(@NotNull Long id)
    {
        return itemRepository.findById(id)
                .map(itemMapper::fromEntity);
    }

    /**
     * TODO Replace by saveItemTags. AddItemTag should be a command in Domain module
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Item addItemTag(@NotNull Item item, @NotNull Tag tag)
    {
        ItemEntity itemEntity = itemRepository.findById(item.id())
                .orElseThrow(() -> new ElementNotFoundException(Item.class.getSimpleName(), item.id()));
        TagEntity tagEntity = tagRepository.findById(tag.id())
                .orElseThrow(() -> new ElementNotFoundException(Tag.class.getSimpleName(), tag.id()));

        itemEntity.getTags().add(tagEntity);
        itemEntity = itemRepository.save(itemEntity);

        return itemMapper.fromEntity(itemEntity);
    }
}
