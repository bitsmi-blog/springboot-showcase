package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.common.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;
import java.util.Optional;

@Builder(toBuilder = true, builderClassName = "Builder")
@Schema(description = "Paginated data response")
public record PaginatedResponse<T>(
        @Schema(description = "Data corresponding to this page")
        List<T> data,
        @Schema(description = "Pagination data used in the request")
        Pagination pagination,
        @Schema(description = "Number of results in this page")
        int pageCount,
        @Schema(description = "Number of total pages for the whole data set")
        int totalPages,
        @Schema(description = "Number of total results in the whole data set")
        long totalCount
) {
    @JsonIgnore
    public boolean isFirstPage() {
        return pagination.pageNumber() == 0;
    }

    @JsonIgnore
    public boolean isLastPage() {
        return pagination.pageNumber() == totalPages - 1;
    }

    @JsonIgnore
    public Optional<Pagination> nextPage() {
        if (isLastPage()) {
            return Optional.empty();
        }

        return Optional.of(Pagination.of(pagination.pageNumber() + 1, pagination.pageSize(), pagination.sort()));
    }

    @JsonIgnore
    public Optional<Pagination> previousPage() {
        if (isFirstPage()) {
            return Optional.empty();
        }

        return Optional.of(Pagination.of(pagination.pageNumber() - 1, pagination.pageSize(), pagination.sort()));
    }
}
