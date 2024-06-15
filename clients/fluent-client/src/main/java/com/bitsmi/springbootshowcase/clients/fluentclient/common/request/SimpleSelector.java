package com.bitsmi.springbootshowcase.clients.fluentclient.common.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;

public record SimpleSelector(@NotEmpty String field, @NotNull RelationalOperator operator, @NotEmpty String value) implements Selector
{
    public static SimpleSelector of(String field, RelationalOperator operator, String value)
    {
        return new SimpleSelector(field, operator, value);
    }

    @Override
    public String toString()
    {
        return "%s %s %s".formatted(
                StringUtils.containsWhitespace(field) ? StringUtils.wrap(field, "'") : field,
                operator,
                StringUtils.containsWhitespace(value) ? StringUtils.wrap(value, "'") : value
        );
    }
}
