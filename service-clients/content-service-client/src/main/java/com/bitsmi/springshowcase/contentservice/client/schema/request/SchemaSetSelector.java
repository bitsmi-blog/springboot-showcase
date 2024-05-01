package com.bitsmi.springshowcase.contentservice.client.schema.request;

import com.bitsmi.springshowcase.contentservice.client.common.request.ISelector;
import com.bitsmi.springshowcase.contentservice.client.common.request.RelationalOperator;
import com.bitsmi.springshowcase.contentservice.client.common.request.SimpleSelector;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public class SchemaSetSelector
{
    @NotNull
    @Valid
    private final ISelector selector;

    private SchemaSetSelector(ISelector selector)
    {
        this.selector = selector;
    }

    public ISelector selector()
    {
        return selector;
    }

    public static SchemaSetSelector id(Long id)
    {
        return new SchemaSetSelector(new SimpleSelector("id",
                RelationalOperator.EQUALS,
                Optional.ofNullable(id).map(v -> Long.toString(v)).orElse(null)
        ));
    }

    public static SchemaSetSelector externalId(String externalId)
    {
        return new SchemaSetSelector(new SimpleSelector("externalId",
                RelationalOperator.EQUALS,
                externalId
        ));
    }

    public SchemaSetSelector and(SchemaSetSelector other)
    {
        return new SchemaSetSelector(this.selector().and(other.selector()));
    }

    public SchemaSetSelector or(SchemaSetSelector other)
    {
        return new SchemaSetSelector(this.selector().and(other.selector()));
    }

    @Override
    public String toString()
    {
        return selector.toString();
    }
}
