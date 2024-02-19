package com.bitsmi.springbootshowcase.domain.common.dto;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true, builderClassName = "Builder")
public record PagedData<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        int totalPages,
        long totalElements
)
{
    public boolean isFirstPage()
    {
        return pageNumber == 0;
    }

    public boolean isLastPage()
    {
        return pageNumber == totalPages - 1;
    }
}
