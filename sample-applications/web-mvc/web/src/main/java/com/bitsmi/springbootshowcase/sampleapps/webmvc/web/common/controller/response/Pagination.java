package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.common.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder(toBuilder = true, builderClassName = "Builder")
@Schema(description = "Pagination data")
public record Pagination(
        @Schema(description = "Current page. 0 based index")
        int pageNumber,
        @Schema(description = "Number of results per page")
        int pageSize,
        @Schema(description = "Sort criteria applied")
        Sort sort
) {
    public static final Integer DEFAULT_PAGE_SIZE = 5;

    public static Pagination of(int page, int size, Sort sort) {
        return builder().pageNumber(page).pageSize(size).sort(sort).build();
    }

    public static class Builder {
        public Pagination build() {
            return new Pagination(
                    pageNumber, pageSize > 0 ? pageSize : DEFAULT_PAGE_SIZE, sort != null ? sort : Sort.UNSORTED);
        }
    }
}
