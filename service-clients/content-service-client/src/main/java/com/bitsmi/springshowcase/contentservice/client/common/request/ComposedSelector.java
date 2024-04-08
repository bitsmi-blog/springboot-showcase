package com.bitsmi.springshowcase.contentservice.client.common.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record ComposedSelector(
        @NotNull @Valid ISelector left,
        @NotNull LogicalOperator operator,
        @NotNull @Valid ISelector right
) implements ISelector
{
    @Override
    public String toString()
    {
        return "(%s %s %s)".formatted(left, operator, right);
    }
}
