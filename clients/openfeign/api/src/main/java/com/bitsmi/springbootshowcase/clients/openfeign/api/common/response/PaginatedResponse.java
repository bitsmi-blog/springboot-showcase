package com.bitsmi.springbootshowcase.clients.openfeign.api.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;

import java.util.List;
import java.util.Optional;

@Builder(toBuilder = true, builderClassName = "Builder")
public record PaginatedResponse<T>(
        List<T> data,
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

    @JsonIgnore
    public Optional<Pagination> nextPage()
    {
        if(isLastPage()) {
            return Optional.empty();
        }

        return Optional.of(Pagination.of(pagination.pageNumber() + 1, pagination.pageSize(), pagination.sort()));
    }

    @JsonIgnore
    public Optional<Pagination> previousPage()
    {
        if(isFirstPage()) {
            return Optional.empty();
        }

        return Optional.of(Pagination.of(pagination.pageNumber() - 1, pagination.pageSize(), pagination.sort()));
    }
}
