package com.bitsmi.springbootshowcase.api.common.request;

import lombok.Builder;

@Builder(toBuilder = true, builderClassName = "Builder")
public record PageRequest(
        int page,
        int size
)
{
    public static PageRequest of(int page, int size)
    {
        return builder().page(page)
                .size(size)
                .build();
    }

    public static class Builder
    {
        public PageRequest build()
        {
            return new PageRequest(page, size>0 ? size : 5);
        }
    }
}
