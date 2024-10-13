package com.bitsmi.springbootshowcase.sampleapps.webmvc.web.common.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Builder(toBuilder = true, builderClassName = "Builder")
@Schema(description = "Request pagination data")
public class PaginatedRequest {

    @PositiveOrZero
    @Schema(description = "Page 0 based index")
    protected Integer page;

    @PositiveOrZero
    @Schema(description = "Size of the results set")
    protected Integer pageSize;

    protected List<String> sort;

    public PaginatedRequest(Integer page, Integer pageSize, List<String> sort) {
        this.page = page;
        this.pageSize = pageSize;
        this.sort = sort;
    }

    public static PaginatedRequest of(Integer page, Integer pageSize) {
        return builder()
                .page(page)
                .pageSize(pageSize)
                .sort(Collections.emptyList())
                .build();
    }

    public static PaginatedRequest of(Integer page, Integer pageSize, List<String> sort) {
        return builder()
                .page(page)
                .pageSize(pageSize)
                .sort(new ArrayList<>(sort))
                .build();
    }

    public PaginatedRequest withDefaults(Integer page, Integer pageSize) {
        return builder()
                .page(ObjectUtils.defaultIfNull(this.page, page))
                .pageSize(ObjectUtils.defaultIfNull(this.pageSize, pageSize))
                .sort(this.sort)
                .build();
    }

    public static class Builder {

        public PaginatedRequest build() {
            return new PaginatedRequest(page, pageSize, ObjectUtils.defaultIfNull(sort, Collections.emptyList()));
        }
    }
}
