package com.bitsmi.springbootshowcase.domain.common.dto;

import lombok.Builder;

@Builder(toBuilder = true, builderClassName = "Builder")
public record Page(
        int number,
        int size
)
{
    public static Page of(int page, int size)
    {
        return builder().number(page)
                .size(size)
                .build();
    }

    public static class Builder
    {
        public Page build()
        {
            return new Page(number, size>0 ? size : 5);
        }
    }
}
