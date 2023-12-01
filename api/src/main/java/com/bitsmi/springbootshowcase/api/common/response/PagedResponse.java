package com.bitsmi.springbootshowcase.api.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true, builderClassName = "Builder")
public record PagedResponse<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        int totalPages,
        long totalElements
)
{
    @JsonIgnore
    public boolean isFirstPage()
    {
        return pageNumber == 0;
    }

    @JsonIgnore
    public boolean isLastPage()
    {
        return pageNumber == totalPages - 1;
    }
}
