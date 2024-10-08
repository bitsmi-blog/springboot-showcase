package com.bitsmi.springbootshowcase.sampleapps.domain.common.dto;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true, builderClassName = "Builder")
public record PaginatedData<T>(
        List<T> data,
        Pagination pagination,
        int pageCount,
        int totalPages,
        long totalCount
)
{
    public boolean isFirstPage()
    {
        return pagination.pageNumber() == 0;
    }

    public boolean isLastPage()
    {
        return pagination.pageNumber() == totalPages - 1;
    }
}
