package com.bitsmi.springbootshowcase.domain.common.dto;

import lombok.Builder;

@Builder(toBuilder = true, builderClassName = "Builder")
public record Pagination(
        int pageNumber,
        int pageSize,
        Sort sort
)
{
    public static final Integer DEFAULT_PAGE_SIZE = 5;

    public static Pagination of(int page, int size, Sort sort)
    {
        return builder().pageNumber(page)
                .pageSize(size)
                .sort(sort)
                .build();
    }

    public static class Builder
    {
        public Pagination build()
        {
            return new Pagination(
                pageNumber,
                pageSize>0 ? pageSize : DEFAULT_PAGE_SIZE,
                sort!=null ? sort : Sort.UNSORTED
            );
        }
    }
}
