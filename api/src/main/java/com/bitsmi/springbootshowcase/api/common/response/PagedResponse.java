package com.bitsmi.springbootshowcase.api.common.response;

import com.bitsmi.springbootshowcase.domain.common.dto.Pagination;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true, builderClassName = "Builder")
public record PagedResponse<T>(
        List<T> content,
        Pagination pagination,
        int pageCount,
        int totalPages,
        long totalCount
)
{
    @JsonIgnore
    public boolean isFirstPage()
    {
        return pagination.pageNumber() == 0;
    }

    @JsonIgnore
    public boolean isLastPage()
    {
        return pagination.pageNumber() == totalPages - 1;
    }
}
