package com.bitsmi.springbootshowcase.clients.fluentclient.common.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record ComposedSelector(
        @NotNull @Valid Selector left,
        @NotNull LogicalOperator operator,
        @NotNull @Valid Selector right
) implements Selector
{
    @Override
    public String toString()
    {
        return "(%s %s %s)".formatted(left, operator, right);
    }
}
