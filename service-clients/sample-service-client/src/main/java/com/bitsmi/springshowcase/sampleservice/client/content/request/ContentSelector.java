package com.bitsmi.springshowcase.sampleservice.client.content.request;

import com.bitsmi.springshowcase.sampleservice.client.common.request.ISelector;
import com.bitsmi.springshowcase.sampleservice.client.common.request.RelationalOperator;
import com.bitsmi.springshowcase.sampleservice.client.common.request.SimpleSelector;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Optional;

public class ContentSelector
{
    @NotNull
    @Valid
    private final ISelector selector;

    private ContentSelector(ISelector selector)
    {
        this.selector = selector;
    }

    public ISelector selector()
    {
        return selector;
    }

    public static ContentSelector id(Long id)
    {
        return new ContentSelector(new SimpleSelector("id",
                RelationalOperator.EQUALS,
                Optional.ofNullable(id).map(v -> Long.toString(v)).orElse(null)
        ));
    }

    public ContentSelector and(ContentSelector other)
    {
        return new ContentSelector(this.selector().and(other.selector()));
    }

    public ContentSelector or(ContentSelector other)
    {
        return new ContentSelector(this.selector().and(other.selector()));
    }

    @Override
    public String toString()
    {
        return selector.toString();
    }
}
