package com.bitsmi.springbootshowcase.clients.fluentclient.inventory.request;

import com.bitsmi.springbootshowcase.clients.fluentclient.common.request.RelationalOperator;
import com.bitsmi.springbootshowcase.clients.fluentclient.common.request.Selector;
import com.bitsmi.springbootshowcase.clients.fluentclient.common.request.SimpleSelector;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;
import java.util.Optional;

public class CategorySetSelector
{
    @NotNull
    @Valid
    private final Selector selector;

    private CategorySetSelector(Selector selector)
    {
        this.selector = selector;
    }

    public Selector selector()
    {
        return selector;
    }

    public static CategorySetSelector id(Long id)
    {
        return new CategorySetSelector(new SimpleSelector("id",
                RelationalOperator.EQUALS,
                Optional.ofNullable(id).map(v -> Long.toString(v)).orElse(null)
        ));
    }

    public static CategorySetSelector externalId(String externalId)
    {
        return new CategorySetSelector(new SimpleSelector("externalId",
                RelationalOperator.EQUALS,
                externalId
        ));
    }

    public CategorySetSelector and(CategorySetSelector other)
    {
        return new CategorySetSelector(this.selector().and(other.selector()));
    }

    public CategorySetSelector or(CategorySetSelector other)
    {
        return new CategorySetSelector(this.selector().and(other.selector()));
    }

    @Override
    public String toString()
    {
        return selector.toString();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(selector);
    }

    @Override
    public boolean equals(Object o)
    {
        return this == o
                || o instanceof CategorySetSelector other
                && Objects.equals(selector, other.selector);
    }
}
